package net.todd.scorekeeper.data;

import java.io.Serializable;
import java.util.Date;

import org.simpleframework.xml.Element;

public class Game implements Serializable, Comparable<Game> {
	private static final long serialVersionUID = -4703874076017365877L;

	@Element(required = false)
	private Date gameOverTimestamp;
	@Element(required = false)
	private String gameType;
	@Element
	private ScoreBoard scoreBoard;

	public Date getGameOverTimestamp() {
		return gameOverTimestamp;
	}

	public void setGameOverTimestamp(Date gameOverTimestamp) {
		this.gameOverTimestamp = gameOverTimestamp;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameOverTimestamp == null) ? 0 : gameOverTimestamp.hashCode());
		result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
		result = prime * result + ((scoreBoard == null) ? 0 : scoreBoard.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (gameOverTimestamp == null) {
			if (other.gameOverTimestamp != null)
				return false;
		} else if (!gameOverTimestamp.equals(other.gameOverTimestamp))
			return false;
		if (gameType == null) {
			if (other.gameType != null)
				return false;
		} else if (!gameType.equals(other.gameType))
			return false;
		if (scoreBoard == null) {
			if (other.scoreBoard != null)
				return false;
		} else if (!scoreBoard.equals(other.scoreBoard))
			return false;
		return true;
	}

	@Override
	public int compareTo(Game that) {
		return that.getGameOverTimestamp().compareTo(this.getGameOverTimestamp());
	}
}
