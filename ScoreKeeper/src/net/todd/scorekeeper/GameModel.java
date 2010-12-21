package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameModel {
	private final ListenerManager scoreChangedListenerManager = new ListenerManager();
	private final ListenerManager playerChangeListenerManager = new ListenerManager();
	private final ListenerManager gameOverListenerManager = new ListenerManager();
	private final ListenerManager cancellationListenerManager = new ListenerManager();

	private final GameStore gameStore;

	private final List<Player> selectedPlayers = new ArrayList<Player>();
	private final ScoreBoard scoreBoard;
	private final String gameType;

	private int currentPlayersTurn;
	private final PageNavigator pageNavigator;

	public GameModel(GameStore gameStore, PageNavigator pageNavigator) {
		this.gameStore = gameStore;
		this.pageNavigator = pageNavigator;

		CurrentGame currentGame = (CurrentGame) pageNavigator.getExtra("currentGame");
		scoreBoard = currentGame.getScoreBoard();
		for (ScoreBoardEntry entry : scoreBoard.getEntries()) {
			selectedPlayers.add(entry.getPlayer());
		}
		if (currentGame.getCurrentPlayer() != null) {
			currentPlayersTurn = selectedPlayers.indexOf(currentGame.getCurrentPlayer());
		}
		gameType = (String)pageNavigator.getExtra("gameType");
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
		cancellationListenerManager.notifyListeners();
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

		gameOverListenerManager.notifyListeners();
		pageNavigator.navigateToActivity(MainPageActivity.class);
	}

	public void addGameOverListener(Listener listener) {
		gameOverListenerManager.addListener(listener);
	}

	public void addCancelGameListener(Listener listener) {
		cancellationListenerManager.addListener(listener);
	}
}
