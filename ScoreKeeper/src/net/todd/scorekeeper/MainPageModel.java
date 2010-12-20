package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MainPageModel {
	private final PageNavigator pageNavigator;
	private final CurrentGameStore currentStateStore;

	public MainPageModel(CurrentGameStore currentGameStore, PageNavigator pageNavigator) {
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
}
