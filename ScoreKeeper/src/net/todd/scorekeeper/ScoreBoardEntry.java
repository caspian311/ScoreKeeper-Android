package net.todd.scorekeeper;

import java.io.Serializable;


public class ScoreBoardEntry implements Serializable, Comparable<ScoreBoardEntry> {
	private static final long serialVersionUID = 490726045699984371L;
	
	private final Player player;
	private int score;

	public ScoreBoardEntry(Player player) {
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

	@Override
	public int compareTo(ScoreBoardEntry that) {
		Integer score1 = this.getScore();
		Integer score2 = that.getScore();
		return score1.compareTo(score2) * -1;
	}
}
