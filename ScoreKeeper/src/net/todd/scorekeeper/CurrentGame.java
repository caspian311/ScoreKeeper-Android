package net.todd.scorekeeper;

import java.io.Serializable;

public class CurrentGame implements Serializable {
	private static final long serialVersionUID = -9165656166149475667L;
	
	private final ScoreBoard scoreBoard;

	public CurrentGame(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
}
