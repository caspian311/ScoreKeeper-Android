package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainPageModel {
	private final PageNavigator pageNavigator;
	private final CurrentGameStore currentStateStore;
	private final Context context;

	public MainPageModel(Context context, CurrentGameStore currentGameStore,
			PageNavigator pageNavigator) {
		this.context = context;
		this.currentStateStore = currentGameStore;
		this.pageNavigator = pageNavigator;
	}

	public void quitApplication() {
		System.exit(0);
	}

	public void goToManagePlayerPage() {
		pageNavigator.navigateToActivity(ManagePlayersActivity.class);
	}

	public void goToStartGamePage() {
		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentStateStore.getCurrentGame());
		pageNavigator.navigateToActivity(SetupGameActivity.class, extras);
	}

	public void goToHistoryPage() {
		pageNavigator.navigateToActivity(HistoryActivity.class);
	}

	public String getVersion() {
		String versionNumber;
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionNumber = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
		return "Version " + versionNumber;
	}
}
