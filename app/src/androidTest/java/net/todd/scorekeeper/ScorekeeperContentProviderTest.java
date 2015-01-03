package net.todd.scorekeeper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ScorekeeperContentProviderTest {
    private ContentResolver contentResolver;
    private DbHelper dbHelper;

    @Before
    public void setup() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        contentResolver = activity.getContentResolver();

        dbHelper = new DbHelper(activity);
        cleanUpPlayers();
    }

    @After
    public void cleanUpPlayers() {
        dbHelper.getWritableDatabase().execSQL("DELETE FROM Players");
    }

    @Test
    public void playersAreManaged() {
        String playerName = UUID.randomUUID().toString();

        Uri allPlayersUri = Uri.parse("content://net.todd.scorekeeper").buildUpon().appendPath("players").build();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", playerName);
        contentResolver.insert(allPlayersUri, contentValues);

        Cursor allPlayersCursor = contentResolver.query(allPlayersUri, null, null, null, null);
        assertThat(allPlayersCursor.moveToFirst(), is(true));
        assertThat(allPlayersCursor.getString(allPlayersCursor.getColumnIndex("name")), equalTo(playerName));

        int playerId = allPlayersCursor.getInt(allPlayersCursor.getColumnIndex("_id"));

        Uri deleteUri = Uri.parse("content://net.todd.scorekeeper").buildUpon().appendPath("players").appendPath(new Integer(playerId).toString()).build();
        int numberOfDeletes = contentResolver.delete(deleteUri, null, null);

        assertThat(numberOfDeletes, is(1));

        Cursor allPlayersAfterDelete = contentResolver.query(allPlayersUri, null, null, null, null);
        assertThat(allPlayersAfterDelete.moveToFirst(), is(false));
    }
}
