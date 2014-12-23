package net.todd.scorekeeper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ScorekeeperContentProvider extends ContentProvider {
    private DbHelper dbHelper;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PLAYERS = 1;
    private static final int GAMES = 2;
    private static final int SCORE_BOARD = 4;

    static {
        uriMatcher.addURI("net.todd.scorekeeper", "players", PLAYERS);
        uriMatcher.addURI("net.todd.scorekeeper", "games", GAMES);
        uriMatcher.addURI("net.todd.scorekeeper", "games/#/scoreboard", SCORE_BOARD);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case PLAYERS:
                return getPlayers(db);
            case GAMES:
                return getGames(db);
            case SCORE_BOARD:
                return getScoreboardForGame(db, uri);
            default:
                return null;
        }
    }

    private Cursor getScoreboardForGame(SQLiteDatabase db, Uri uri) {
        String gameId = uri.getPathSegments().get(1);
        return db.rawQuery("SELECT _id, name, score FROM Scoreboard WHERE gameId = ? ", new String[]{gameId});
    }

    private Cursor getGames(SQLiteDatabase db) {
        return db.rawQuery("SELECT _id, name, winner, date FROM Games", null);
    }

    private Cursor getPlayers(SQLiteDatabase db) {
        return db.rawQuery("SELECT _id, name FROM Players", null);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(getTableFromUri(uri), null, values);
        db.close();

        return uri.buildUpon().appendPath(new Long(id).toString()).build();
    }

    private String getTableFromUri(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case PLAYERS:
                tableName = "Players";
                break;
            case GAMES:
                tableName = "Games";
                break;
            case SCORE_BOARD:
                tableName = "Scoreboard";
                break;
        }
        return tableName;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(getTableFromUri(uri), "_id = ?", new String[]{selection});
        db.close();

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
