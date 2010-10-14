package net.todd.scorekeeper;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class Persistor<T> {
	private final Class<T> clazz;
	private final Context context;

	public static <T> Persistor<T> create(Class<T> clazz, Context context) {
		return new Persistor<T>(clazz, context);
	}

	public Persistor(Class<T> clazz, Context context) {
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
		try {
			ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(filename,
					Context.MODE_PRIVATE));
			oos.writeObject(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
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

	public List<T> load() {
		List<T> items = new ArrayList<T>();
		if (doesFileExist(getDataFilename())) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						context.openFileInput(getDataFilename()));
				List<?> itemsFromFile = (List<?>) ois.readObject();
				for (Object object : itemsFromFile) {
					items.add(clazz.cast(object));
				}
			} catch (Exception e) {
				Logger.error(getClass().getName(), "Could not restore data from file: "
						+ getDataFilename(), e);
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
