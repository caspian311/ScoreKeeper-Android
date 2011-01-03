package net.todd.scorekeeper;

public enum Scoring {
	HIGH("Highest score wins"), LOW("Lowest score wins");

	private final String text;

	Scoring(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
