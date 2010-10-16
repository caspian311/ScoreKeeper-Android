package net.todd.scorekeeper;

import java.util.ArrayList;

import android.app.Activity;

public class GameModel {
	private final ListenerManager scoreChangedListenerManager = new ListenerManager();
	private final ListenerManager playerChangeListenerManager = new ListenerManager();

	private final Activity activity;
	private final ArrayList<Player> selectedPlayers;
	private final ScoreBoard scoreBoard;

	private int currentPlayersTurn;

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity) {
		this.activity = activity;

		selectedPlayers = (ArrayList<Player>) activity.getIntent().getSerializableExtra(
				"selectedPlayers");
		scoreBoard = new ScoreBoard(selectedPlayers);
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
		Integer currentScore = scoreBoard.getScore(getCurrentPlayer());
		currentScore += score;
		 scoreBoard.setScore(getCurrentPlayer(), currentScore);
		scoreChangedListenerManager.notifyListeners();
	}

	public Player getCurrentPlayer() {
		return selectedPlayers.get(currentPlayersTurn);
	}

	public int getCurrentPlayersScore() {
		return scoreBoard.getScore(getCurrentPlayer());
	}

	public void previousPlayer() {
		getPreviousTurn();
		playerChangeListenerManager.notifyListeners();
	}

	public ScoreBoard getScoreBoard() {
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

	public void gameOver() {
		activity.finish();
	}
}
