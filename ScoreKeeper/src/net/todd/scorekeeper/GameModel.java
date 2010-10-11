package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

public class GameModel {
	private final ArrayList<Player> selectedPlayers;
	private int currentPlayersTurn = -1;
	private final Map<Player, Integer> scoreBoard = new HashMap<Player, Integer>();

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity) {
		selectedPlayers = (ArrayList<Player>)activity.getIntent().getExtras().get("selectedPlayers");
		for (Player player : selectedPlayers) {
			scoreBoard.put(player, 0);
		}
	}

	public Player getNextPlayer() {
		return selectedPlayers.get(getNextTurn());
	}

	private int getNextTurn() {
		currentPlayersTurn++;
		currentPlayersTurn %= selectedPlayers.size();
		return currentPlayersTurn;
	}
	
	private int getPreviousTurn() {
		currentPlayersTurn--;
		currentPlayersTurn %= selectedPlayers.size();
		return currentPlayersTurn;
	}

	public void setScoreForCurrentPlayer(String score) {
		Integer currentScore = scoreBoard.get(getCurrentPlayer());
		currentScore += Integer.parseInt(score);
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

	public int getPrevoiusPlayersScore() {
		return scoreBoard.get(getPreviousPlayer());
	}

	public Map<Player, Integer> getScoreBoard() {
		return scoreBoard;
	}
}
