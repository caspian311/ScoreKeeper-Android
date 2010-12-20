package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class GameRestorer {
	private final CurrentGameStore currentGameStore;
	private final PageNavigator pageNavigator;

	public GameRestorer(CurrentGameStore currentGameStore, PageNavigator pageNavigator) {
		this.currentGameStore = currentGameStore;
		this.pageNavigator = pageNavigator;
	}

	public void restoreGameInProgress() {
		CurrentGame currentGame = currentGameStore.getCurrentGame();
		if (currentGame != null) {
			Map<String, Serializable> extras = new HashMap<String, Serializable>();
			extras.put("currentGame", currentGame);
			pageNavigator.navigateToActivity(GameActivity.class, extras);
		}
	}
}
