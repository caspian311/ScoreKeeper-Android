package net.todd.scorekeeper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.content.Context;

public abstract class Persistor<T> {
	protected Context context;
	private Class<T> clazz;

	protected Persistor(Class<T> clazz, Context context) {
		setClazz(clazz);
		setContext(context);
	}

	protected boolean doesFileExist(String filename) {
		boolean doesFileExist = false;
		try {
			context.openFileInput(filename);
			doesFileExist = true;
		} catch (FileNotFoundException e) {
		}
		return doesFileExist;
	}

	protected boolean doesFileHaveData(String filename) {
		boolean doesFileHaveData = false;
		try {
			FileInputStream input = context.openFileInput(filename);
			doesFileHaveData = input.available() > 0;
		} catch (Exception e) {
		}
		return doesFileHaveData;
	}

	protected void setContext(Context context) {
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	protected Class<T> getClazz() {
		return clazz;
	}

	protected void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public abstract List<T> load();

	public abstract void persist(List<T> items);
}
