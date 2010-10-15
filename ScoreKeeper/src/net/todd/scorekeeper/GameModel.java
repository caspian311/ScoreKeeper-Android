package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

public class GameModel {
	private final Map<Player, Integer> scoreBoard = new HashMap<Player, Integer>();
	private final ListenerManager scoreChangedListenerManager = new ListenerManager();
	private final ListenerManager playerChangeListenerManager = new ListenerManager();

	private final Activity activity;
	private final ArrayList<Player> selectedPlayers;

	private int currentPlayersTurn;

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity) {
		this.activity = activity;

		selectedPlayers = (ArrayList<Player>) activity.getIntent().getSerializableExtra(
				"selectedPlayers");
		for (Player player : selectedPlayers) {
			scoreBoard.put(player, 0);
		}
	}

	public void nextPlayer() {
		getNextTurn();
		playerChangeListenerManager.notifyListeners();
	}

	private int getNextTurn() {
		currentPlayersTurn++;
		currentPlayersTurn %= selectedPlayers.size();
		return currentPlayersTurn;
	}

	private int getPreviousTurn() {
		currentPlayersTurn--;
		if (currentPlayersTurn == -1) {
			currentPlayersTurn = selectedPlayers.size() - 1;
		}
		return currentPlayersTurn;
	}

	public void setScoreForCurrentPlayer(int score) {
		Integer currentScore = scoreBoard.get(getCurrentPlayer());
		currentScore += score;
		 scoreBoard.put(getCurrentPlayer(), currentScore);
		scoreChangedListenerManager.notifyListeners();
	}

	public Player getCurrentPlayer() {
		return selectedPlayers.get(currentPlayersTurn);
	}

	public int getCurrentPlayersScore() {
		return scoreBoard.get(getCurrentPlayer());
	}

	public void previousPlayer() {
		getPreviousTurn();
		playerChangeListenerManager.notifyListeners();
	}

	public Map<Player, Integer> getScoreBoard() {
		return scoreBoard;
	}

	public void cancelGame() {
		activity.finish();
	}

	public void addScoreChangedListener(Listener listener) {
		scoreChangedListenerManager.addListener(listener);
	}

	public void addPlayerChangedListener(Listener listener) {
		playerChangeListenerManager.addListener(listener);
	}
}
