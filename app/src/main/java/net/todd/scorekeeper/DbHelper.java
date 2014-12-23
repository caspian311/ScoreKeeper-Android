package net.todd.scorekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "scorekeeper.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Players (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Games (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, winner TEXT, date TEXT)");
        db.execSQL("CREATE TABLE Scoreboard (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score REAL, gameId REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Players");
        db.execSQL("DROP TABLE IF EXISTS Games");
        db.execSQL("DROP TABLE IF EXISTS Scoreboard");
        onCreate(db);
    }
}
