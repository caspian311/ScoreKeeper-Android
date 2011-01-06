package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.ScoreBoard;

public class SetupGameModel {
	private final ListenerManager stateChangedListenerManager = new ListenerManager();

	private final PageNavigator pageNavigator;

	private CurrentGame currentGame;

	public SetupGameModel(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;

		currentGame = (CurrentGame) pageNavigator.getExtra("currentGame");
		if (currentGame == null) {
			currentGame = new CurrentGame();
			currentGame.setScoreBoard(new ScoreBoard());
			currentGame.setGameName("");
		}
	}

	public void cancel() {
		pageNavigator.navigateToActivityAndFinish(MainPageActivity.class);
	}

	public void startGame() {
		pageNavigator.navigateToActivityAndFinish(GameActivity.class,
				createExtrasMapWithCurrentGame());
	}

	private Map<String, Serializable> createExtrasMapWithCurrentGame() {
		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentGame);
		return extras;
	}

	public void goToAddPlayersScreen() {
		pageNavigator.navigateToActivityButDontFinish(AddPlayersToGameActivity.class,
				createExtrasMapWithCurrentGame());
	}

	public boolean arePlayersAddedToGame() {
		return currentGame.getScoreBoard().getEntries().size() >= 2;
	}

	public boolean isGameSetupComplete() {
		return arePlayersAddedToGame() && isGameNamePopulated();
	}

	private boolean isGameNamePopulated() {
		return currentGame.getGameName().length() > 0;
	}

	public void addStateChangedListener(Listener listener) {
		stateChangedListenerManager.addListener(listener);
	}

	public void setGameName(String gameName) {
		if (!currentGame.getGameName().equals(gameName)) {
			currentGame.setGameName(gameName);
			stateChangedListenerManager.notifyListeners();
		}
	}

	public String getGameName() {
		return currentGame.getGameName();
	}

	public void goToOrderPlayersScreen() {
		pageNavigator.navigateToActivityButDontFinish(OrderPlayersActivity.class,
				createExtrasMapWithCurrentGame());
	}

	public Scoring getScoring() {
		return currentGame.getScoreBoard().getScoring();
	}

	public void setScoring(Scoring scoring) {
		if (!scoring.equals(currentGame.getScoreBoard().getScoring())) {
			currentGame.getScoreBoard().setScoring(scoring);
			stateChangedListenerManager.notifyListeners();
		}
	}
}
