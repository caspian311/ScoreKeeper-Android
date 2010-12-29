package net.todd.scorekeeper.data;

import java.io.Serializable;

public class CurrentGame implements Serializable {
	private static final long serialVersionUID = 5432426044479529331L;

	private final ScoreBoard scoreBoard;
	private final Player currentPlayer;

	public CurrentGame(ScoreBoard scoreBoard, Player currentPlayer) {
		this.scoreBoard = scoreBoard;
		this.currentPlayer = currentPlayer;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
