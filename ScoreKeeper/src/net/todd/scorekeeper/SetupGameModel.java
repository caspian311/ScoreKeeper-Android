package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;

public class SetupGameModel {
	private final ListenerManager stateChangedListenerManager = new ListenerManager();

	private final PageNavigator pageNavigator;
	private final List<Player> selectedPlayers = new ArrayList<Player>();

	private String gameName;

	public SetupGameModel(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;

		CurrentGame currentGame = (CurrentGame) pageNavigator.getExtra("currentGame");
		if (currentGame != null) {
			gameName = currentGame.getGameName();
		} else {
			gameName = "";
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
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(selectedPlayers);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(gameName);
		currentGame.setCurrentPlayer(selectedPlayers.get(0));
		currentGame.setScoreBoard(scoreBoard);

		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentGame);
		return extras;
	}

	public void goToAddPlayersScreen() {
		pageNavigator.navigateToActivityButDontFinish(AddPlayersToGameActivity.class,
				createExtrasMapWithCurrentGame());
	}

	public boolean arePlayersAddedToGame() {
		return selectedPlayers.size() >= 2;
	}

	public boolean isGameSetupComplete() {
		return arePlayersAddedToGame() && isGameNamePopulated();
	}

	private boolean isGameNamePopulated() {
		return gameName != null && gameName.length() > 0;
	}

	public void setPlayers(List<Player> players) {
		if (!players.equals(selectedPlayers)) {
			selectedPlayers.addAll(players);
			stateChangedListenerManager.notifyListeners();
		}
	}

	public void addStateChangedListener(Listener listener) {
		stateChangedListenerManager.addListener(listener);
	}

	public List<Player> getSelectedPlayers() {
		return selectedPlayers;
	}

	public void setGameName(String gameName) {
		if (this.gameName == null || !this.gameName.equals(gameName)) {
			this.gameName = gameName;
			stateChangedListenerManager.notifyListeners();
		}
	}

	public String getGameName() {
		return gameName;
	}

	public void goToOrderPlayersScreen() {
		pageNavigator.navigateToActivityButDontFinish(OrderPlayersActivity.class,
				createExtrasMapWithCurrentGame());
	}

	public Scoring getScoring() {
		// TODO
		return null;
	}
}
