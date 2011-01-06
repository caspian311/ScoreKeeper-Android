package net.todd.scorekeeper;

import java.util.Comparator;

import net.todd.scorekeeper.data.ScoreBoardEntry;

public enum Scoring {
	HIGH("Highest score wins", new Comparator<ScoreBoardEntry>() {
		@Override
		public int compare(ScoreBoardEntry object1, ScoreBoardEntry object2) {
			Integer score1 = object1.getScore();
			Integer score2 = object2.getScore();
			return score1.compareTo(score2) * -1;
		}
	}), LOW("Lowest score wins", new Comparator<ScoreBoardEntry>() {
		@Override
		public int compare(ScoreBoardEntry object1, ScoreBoardEntry object2) {
			Integer score1 = object1.getScore();
			Integer score2 = object2.getScore();
			return score1.compareTo(score2);
		}
	});

	private final String text;
	private Comparator<ScoreBoardEntry> comparator;

	Scoring(String text, Comparator<ScoreBoardEntry> comparator) {
		this.text = text;
		this.comparator = comparator;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}

	public static Scoring byName(String scoringText) {
		Scoring target = null;
		for (Scoring scoring : values()) {
			if (scoring.text.equals(scoringText)) {
				target = scoring;
				break;
			}
		}
		return target;
	}

	public Comparator<ScoreBoardEntry> getComparator() {
		return comparator;
	}
}
