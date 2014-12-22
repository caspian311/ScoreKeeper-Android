package net.todd.scorekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public String getWinner() {
        ScoreboardEntry winner = null;
        if (gameConfiguration.isHighestScoreWins()) {
            int highestScore = Integer.MIN_VALUE;
            for (ScoreboardEntry entry : scoreboard) {
                if (entry.getScore() > highestScore) {
                    highestScore = entry.getScore();
                    winner = entry;
                }
            }
        } else {
            int lowestScore = Integer.MAX_VALUE;
            for (ScoreboardEntry entry : scoreboard) {
                if (entry.getScore() < lowestScore) {
                    lowestScore = entry.getScore();
                    winner = entry;
                }
            }
        }
        return winner.getName();
    }

    public ContentValues getGameData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", gameConfiguration.getGameName());
        contentValues.put("winner", getWinner());
        contentValues.put("date", Constants.DATE_STORAGE_FORMAT.format(new Date()));
        return contentValues;
    }

    public List<ContentValues> getScoreboardData() {
        List<ContentValues> data = new ArrayList<ContentValues>();
        for (ScoreboardEntry entry : scoreboard) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", entry.getName());
            contentValues.put("score", entry.getScore());
            data.add(contentValues);
        }
        return data;
    }
}
