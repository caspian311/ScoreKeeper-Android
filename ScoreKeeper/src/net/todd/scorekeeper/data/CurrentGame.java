package net.todd.scorekeeper.data;

import java.io.Serializable;

public class CurrentGame implements Serializable {
	private static final long serialVersionUID = 5432426044479529331L;

	private ScoreBoard scoreBoard;
	private Player currentPlayer;
	private String gameName;

	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameType) {
		this.gameName = gameType;
	}
}
