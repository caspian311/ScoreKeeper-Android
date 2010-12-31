package net.todd.scorekeeper;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

@Deprecated
public class ObjectSerializerPersistor<T> extends Persistor<T> {
	public static <T> Persistor<T> create(Class<T> clazz, Context context) {
		return new ObjectSerializerPersistor<T>(clazz, context);
	}

	private ObjectSerializerPersistor(Class<T> clazz, Context context) {
		super(clazz, context);
	}

	@Override
	public void persist(List<T> items) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(getContext().openFileOutput(getDataFilename(),
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

	@Override
	public List<T> load() {
		List<T> items = new ArrayList<T>();
		if (doesFileExist(getDataFilename()) && doesFileHaveData(getDataFilename())) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(getContext().openFileInput(getDataFilename()));
				List<?> itemsFromFile = (List<?>) ois.readObject();
				for (Object object : itemsFromFile) {
					items.add(getClazz().cast(object));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
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
		return getClazz().getName() + ".data";
	}
}
