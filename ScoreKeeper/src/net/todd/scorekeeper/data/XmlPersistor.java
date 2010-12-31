package net.todd.scorekeeper.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.todd.scorekeeper.Persistor;

import org.simpleframework.xml.core.Persister;

import android.content.Context;

public class XmlPersistor<T> extends Persistor<T> {
	public static <T> Persistor<T> create(Class<T> clazz, Context context) {
		return new XmlPersistor<T>(clazz, context);
	}

	private XmlPersistor(Class<T> clazz, Context context) {
		super(clazz, context);
	}

	@Override
	public void persist(List<T> items) {
		FileOutputStream output = null;
		try {
			output = getContext().openFileOutput(getDataFilename(), Context.MODE_PRIVATE);
			CollectionOfItems<T> collectionOfItems = new CollectionOfItems<T>();
			collectionOfItems.setItems(items);
			new Persister().write(collectionOfItems, output);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				output.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public List<T> load() {
		List<T> items = new ArrayList<T>();
		if (doesFileExist(getDataFilename()) && doesFileHaveData(getDataFilename())) {
			FileInputStream input = null;
			try {
				input = getContext().openFileInput(getDataFilename());
				@SuppressWarnings("unchecked")
				CollectionOfItems<T> collectionOfItems = new Persister().read(
						CollectionOfItems.class, input);
				for (Object object : collectionOfItems.getItems()) {
					items.add(getClazz().cast(object));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
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
		return getClazz().getName() + ".xml";
	}
}
