package net.todd.scorekeeper.data;

import java.io.Serializable;

import org.simpleframework.xml.Element;

public class ScoreBoardEntry implements Serializable {
	private static final long serialVersionUID = 490726045699984371L;

	@Element
	private Player player;
	@Element(required = false)
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
