package net.todd.scorekeeper.data;

import java.io.Serializable;

public class ScoreBoardEntry implements Serializable {
	private static final long serialVersionUID = 490726045699984371L;

	private Player player;
	private int score;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public Player getPlayer() {
		return player;
	}
}
