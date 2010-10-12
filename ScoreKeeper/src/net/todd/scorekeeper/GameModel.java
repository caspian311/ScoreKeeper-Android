package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

public class GameModel {
	private static final int INITIAL_POSITION = -99;

	private final ArrayList<Player> selectedPlayers;
	private int currentPlayersTurn = INITIAL_POSITION;
	private final Map<Player, Integer> scoreBoard = new HashMap<Player, Integer>();

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity) {
		selectedPlayers = (ArrayList<Player>) activity.getIntent().getSerializableExtra(
				"selectedPlayers");
		for (Player player : selectedPlayers) {
			scoreBoard.put(player, 0);
		}
	}

	public Player getNextPlayer() {
		return selectedPlayers.get(getNextTurn());
	}

	private int getNextTurn() {
		if (currentPlayersTurn == INITIAL_POSITION) {
			currentPlayersTurn = 0;
		} else {
			currentPlayersTurn++;
			currentPlayersTurn %= selectedPlayers.size();
		}
		return currentPlayersTurn;
	}

	private int getPreviousTurn() {
		if (currentPlayersTurn == INITIAL_POSITION) {
			currentPlayersTurn = selectedPlayers.size() - 1;
		} else {
			currentPlayersTurn--;
			if (currentPlayersTurn == -1) {
				currentPlayersTurn = selectedPlayers.size() - 1;
			}
		}
		return currentPlayersTurn;
	}

	public void setScoreForCurrentPlayer(int score) {
		Integer currentScore = scoreBoard.get(getCurrentPlayer());
		currentScore += score;
		scoreBoard.put(getCurrentPlayer(), currentScore);
	}

	private Player getCurrentPlayer() {
		return selectedPlayers.get(currentPlayersTurn);
	}

	public int getCurrentPlayersScore() {
		return scoreBoard.get(getCurrentPlayer());
	}

	public Player getPreviousPlayer() {
		return selectedPlayers.get(getPreviousTurn());
	}

	public Map<Player, Integer> getScoreBoard() {
		return scoreBoard;
	}
}
