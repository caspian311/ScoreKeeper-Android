package net.todd.scorekeeper;

public class ScoreboardEntry {
    private final Long id;
    private final String name;
    private int score;
    private boolean currentPlayer;

    public ScoreboardEntry(Long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int score) {
        this.score += score;
    }

    public Long getId() {
        return id;
    }
}