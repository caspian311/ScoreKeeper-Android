package net.todd.scorekeeper.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.todd.scorekeeper.Persistor;
import android.content.Context;

import com.thoughtworks.xstream.XStream;

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

			XStream xstream = new XStream();
			xstream.toXML(items, output);
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

				XStream xstream = new XStream();
				@SuppressWarnings("unchecked")
				List<T> fromXML = (List<T>) xstream.fromXML(input);
				items.addAll(fromXML);
			} catch (Exception e) {
				super.context.deleteFile(getDataFilename());
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
