package net.todd.scorekeeper;

import java.util.Collections;
import java.util.List;

public class HistoryModel {
	private final GameStore gameStore;
	private final PageNavigator pageNavigator;
	private final ListenerManager historyChangedListener = new ListenerManager();

	public HistoryModel(GameStore gameStore, PageNavigator pageNavigator) {
		this.gameStore = gameStore;
		this.pageNavigator = pageNavigator;
	}

	public void finish() {
		pageNavigator.navigateToActivity(MainPageActivity.class);
	}

	public List<Game> getAllGames() {
		List<Game> allGames = gameStore.getAllGames();
		Collections.sort(allGames);
		return allGames;
	}

	public void clearHistory() {
		gameStore.clearAllGames();
		historyChangedListener.notifyListeners();
	}

	public void addHistoryChangedListener(Listener listener) {
		historyChangedListener.addListener(listener);
	}

	public void removeGame(Game selectedGame) {
		gameStore.deleteGame(selectedGame);
		historyChangedListener.notifyListeners();
	}

	public boolean areThereGamesInHistory() {
		return !gameStore.getAllGames().isEmpty();
	}
}
