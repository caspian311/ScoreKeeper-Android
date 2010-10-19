package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;

public class GameModel {
	private final ListenerManager scoreChangedListenerManager = new ListenerManager();
	private final ListenerManager playerChangeListenerManager = new ListenerManager();

	private final Activity activity;
	private final GameStore gameStore;
	private final IntentFactory intentFactory;

	private final ArrayList<Player> selectedPlayers;
	private final ScoreBoard scoreBoard;
	private final String gameType;

	private int currentPlayersTurn;

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity, GameStore gameStore, IntentFactory intentFactory) {
		this.activity = activity;
		this.gameStore = gameStore;
		this.intentFactory = intentFactory;

		selectedPlayers = (ArrayList<Player>) activity.getIntent().getSerializableExtra(
				"selectedPlayers");
		gameType = activity.getIntent().getStringExtra("gameType");
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
		Intent intent = intentFactory.createIntent(activity, MainPageActivity.class);
		activity.startActivity(intent);
	}

	public void addScoreChangedListener(Listener listener) {
		scoreChangedListenerManager.addListener(listener);
	}

	public void addPlayerChangedListener(Listener listener) {
		playerChangeListenerManager.addListener(listener);
	}

	public void gameOver() {
		Game game = new Game();
		game.setGameOverTimestamp(new Date());
		game.setGameType(gameType);
		game.setScoreBoard(scoreBoard);
		gameStore.addGame(game);
		
		Intent intent = intentFactory.createIntent(activity, MainPageActivity.class);
		activity.startActivity(intent);
	}
}
