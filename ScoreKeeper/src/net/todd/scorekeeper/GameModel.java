package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;

public class GameModel {
	private final ListenerManager scoreChangedListenerManager = new ListenerManager();
	private final ListenerManager playerChangeListenerManager = new ListenerManager();

	private final GameStore gameStore;

	private final ArrayList<Player> selectedPlayers;
	private final ScoreBoard scoreBoard;
	private final String gameType;

	private int currentPlayersTurn;
	private final PageNavigator pageNavigator;

	@SuppressWarnings("unchecked")
	public GameModel(Activity activity, GameStore gameStore, PageNavigator pageNavigator) {
		this.gameStore = gameStore;
		this.pageNavigator = pageNavigator;

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
		pageNavigator.navigateToActivity(MainPageActivity.class);
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
		
		pageNavigator.navigateToActivity(MainPageActivity.class);
	}
}
