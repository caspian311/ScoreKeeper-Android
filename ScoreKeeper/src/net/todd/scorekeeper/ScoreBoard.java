package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard implements Serializable {
	private static final long serialVersionUID = -9216810547295023938L;
	
	private final List<ScoreBoardEntry> scoreBoardEntries;

	public ScoreBoard(List<Player> players) {
		scoreBoardEntries = new ArrayList<ScoreBoardEntry>();
		for (Player player : players) {
			scoreBoardEntries.add(new ScoreBoardEntry(player));
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
