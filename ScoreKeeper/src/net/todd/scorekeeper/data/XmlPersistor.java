package net.todd.scorekeeper.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.todd.scorekeeper.Logger;

import org.simpleframework.xml.core.Persister;

import android.content.Context;

public class XmlPersistor<T> {
	private final Class<T> clazz;
	private final Context context;

	public static <T> XmlPersistor<T> create(Class<T> clazz, Context context) {
		return new XmlPersistor<T>(clazz, context);
	}

	private XmlPersistor(Class<T> clazz, Context context) {
		this.clazz = clazz;
		this.context = context;
	}

	public void persist(List<T> items) {
		FileOutputStream output = null;
		try {
			output = context.openFileOutput(getDataFilename(), Context.MODE_PRIVATE);
			CollectionOfItems<T> collectionOfItems = new CollectionOfItems<T>();
			collectionOfItems.setItems(items);
			new Persister().write(collectionOfItems, output);
		} catch (Exception e) {
			Logger.error("error", e.getMessage(), e);
		} finally {
			try {
				output.close();
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
			FileInputStream input = null;
			try {
				input = context.openFileInput(getDataFilename());
				@SuppressWarnings("unchecked")
				CollectionOfItems<T> collectionOfItems = new Persister().read(
						CollectionOfItems.class, input);
				for (Object object : collectionOfItems.getItems()) {
					items.add(clazz.cast(object));
				}
			} catch (Exception e) {
				Logger.error(getClass().getName(), "Could not restore data from file: "
						+ getDataFilename(), e);
			} finally {
				try {
					input.close();
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
