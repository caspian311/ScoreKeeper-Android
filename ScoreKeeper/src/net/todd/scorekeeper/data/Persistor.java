package net.todd.scorekeeper.data;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.todd.scorekeeper.Logger;
import android.content.Context;

public class Persistor<T> {
	private final Class<T> clazz;
	private final Context context;

	public static <T> Persistor<T> create(Class<T> clazz, Context context) {
		return new Persistor<T>(clazz, context);
	}

	private Persistor(Class<T> clazz, Context context) {
		this.clazz = clazz;
		this.context = context;
	}

	public void persist(List<T> items) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(getDataFilename(),
					Context.MODE_PRIVATE));
			oos.writeObject(items);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
		}
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

	private String getDataFilename() {
		return clazz.getName() + ".data";
	}
}
