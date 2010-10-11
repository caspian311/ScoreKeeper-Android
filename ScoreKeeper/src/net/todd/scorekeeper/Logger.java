package net.todd.scorekeeper;

import java.util.Date;

import android.util.Log;

public class Logger {
	private static boolean isTestMode;

	public static void setTestMode(boolean isTestMode) {
		Logger.isTestMode = isTestMode;
	}
	
	public static void error(String tag, String message, Throwable throwable) {
		if (isTestMode) {
			System.err.println(getCurrentDate() + tag + " : " + message);
			throwable.printStackTrace(System.err);
		} else{
			Log.e(tag, message, throwable);
		}
	}

	private static String getCurrentDate() {
		// TODO make this prettier
		return new Date().toString();
	}
}
