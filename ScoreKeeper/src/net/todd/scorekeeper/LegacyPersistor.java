package net.todd.scorekeeper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

@Deprecated
public class LegacyPersistor<T> {
	private final Class<T> clazz;
	private final Context context;

	public static <T> LegacyPersistor<T> create(Class<T> clazz, Context context) {
		return new LegacyPersistor<T>(clazz, context);
	}

	public LegacyPersistor(Class<T> clazz, Context context) {
		this.clazz = clazz;
		this.context = context;
	}

	public void persist(List<T> items) {
		save(getDataFilename(), items);
	}

	public int nextId() {
		int currentId = loadCurrentId();
		int nextId = currentId + 1;
		saveCurrentId(nextId);
		return nextId;
	}

	private void saveCurrentId(int currentId) {
		save(getIdFilename(), currentId);
	}

	private void save(String filename, Object object) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
			oos.writeObject(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
		}
	}

	private int loadCurrentId() {
		Integer id = 0;
		if (doesFileExist(getIdFilename())) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						context.openFileInput(getIdFilename()));
				id = ((Integer) ois.readObject()).intValue();
			} catch (Exception e) {
				Logger.error(getClass().getName(), "Could not load the id file for: "
						+ getIdFilename(), e);
			}
		}
		return id;
	}

	private boolean doesFileExist(String filename) {
		boolean doesFileExist = false;
		try {
			context.openFileInput(filename);
			doesFileExist = true;
		} catch (FileNotFoundException e) {
		}
		return doesFileExist;
	}

	private boolean doesFileHaveData(String filename) {
		boolean doesFileHaveData = false;
		try {
			FileInputStream input = context.openFileInput(filename);
			doesFileHaveData = input.available() > 0;
		} catch (Exception e) {
		}
		return doesFileHaveData;
	}

	public List<T> load() {
		List<T> items = new ArrayList<T>();
		if (doesFileExist(getDataFilename()) && doesFileHaveData(getDataFilename())) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(context.openFileInput(getDataFilename()));
				List<?> itemsFromFile = (List<?>) ois.readObject();
				for (Object object : itemsFromFile) {
					items.add(clazz.cast(object));
				}
			} catch (Exception e) {
				Logger.error(getClass().getName(), "Could not restore data from file: "
						+ getDataFilename(), e);
			} finally {
				try {
					ois.close();
				} catch (Exception e) {
				}
			}
		}
		return items;
	}

	private String getIdFilename() {
		return clazz.getName() + ".id";
	}

	private String getDataFilename() {
		return clazz.getName() + ".data";
	}
}
