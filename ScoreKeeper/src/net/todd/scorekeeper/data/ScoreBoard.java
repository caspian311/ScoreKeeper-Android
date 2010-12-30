package net.todd.scorekeeper.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class ScoreBoard implements Serializable {
	private static final long serialVersionUID = -9216810547295023938L;

	@ElementList
	private List<ScoreBoardEntry> scoreBoardEntries;

	public ScoreBoard() {
		scoreBoardEntries = new ArrayList<ScoreBoardEntry>();
	}

	public void setPlayers(List<Player> players) {
		scoreBoardEntries.clear();
		for (Player player : players) {
			ScoreBoardEntry scoreBoardEntry = new ScoreBoardEntry();
			scoreBoardEntry.setPlayer(player);
			scoreBoardEntries.add(scoreBoardEntry);
		}
	}

	public void setScore(Player player, int score) {
		for (ScoreBoardEntry scoreBoardEntry : scoreBoardEntries) {
			if (scoreBoardEntry.getPlayer().equals(player)) {
				scoreBoardEntry.setScore(score);
				break;
			}
		}
	}

	public int getScore(Player player) {
		int score = 0;
		for (ScoreBoardEntry scoreBoardEntry : scoreBoardEntries) {
			if (scoreBoardEntry.getPlayer().equals(player)) {
				score = scoreBoardEntry.getScore();
				break;
			}
		}
		return score;
	}

	public List<ScoreBoardEntry> getEntries() {
		Collections.sort(scoreBoardEntries);
		return scoreBoardEntries;
	}
}
