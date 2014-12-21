package net.todd.scorekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private GameConfiguration gameConfiguration;
    private List<ScoreboardEntry> scoreboard = new ArrayList<ScoreboardEntry>();
    private int currentPlayerPosition;

    public GameModel(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;

        initializeScoreboard();
    }

    private void initializeScoreboard() {
        for (Player player : this.gameConfiguration.getSelectedPlayers()) {
            scoreboard.add(new ScoreboardEntry(player.getId(), player.getName(), 0));
        }
        getCurrentPlayer().setCurrentPlayer(true);
    }

    public Cursor getScoreBoardCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "isCurrentPlayer", "playerName", "playerScore"});
        for (ScoreboardEntry scoreboardEntry : scoreboard) {
            cursor.addRow(new Object[]{scoreboardEntry.getId(), scoreboardEntry.isCurrentPlayer() ? 1 : 0, scoreboardEntry.getName(), scoreboardEntry.getScore()});
        }
        return cursor;
    }

    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    private ScoreboardEntry getCurrentPlayer() {
        return scoreboard.get(currentPlayerPosition);
    }

    public void goToNextPlayer() {
        currentPlayerPosition++;
        currentPlayerPosition %= scoreboard.size();

        for (ScoreboardEntry scoreboardEntry : scoreboard) {
            scoreboardEntry.setCurrentPlayer(false);
        }
        getCurrentPlayer().setCurrentPlayer(true);
    }

    public void updateCurrentPlayerScore(int score) {
        getCurrentPlayer().addToScore(score);
    }

    public ContentValues getGameStats() {
        return null;
    }
}
