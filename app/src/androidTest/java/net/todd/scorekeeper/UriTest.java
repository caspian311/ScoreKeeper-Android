package net.todd.scorekeeper;

import android.content.UriMatcher;
import android.net.Uri;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UriTest {
    private static final int GAMES = 100;
    private static final int GAME = 101;
    private static final int SCOREBOARDS = 102;

    private UriMatcher uriMatcher;

    @Before
    public void setUp() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("net.todd.scorekeeper", "games", GAMES);
        uriMatcher.addURI("net.todd.scorekeeper", "games/#", GAME);
        uriMatcher.addURI("net.todd.scorekeeper", "games/#/scoreboard", SCOREBOARDS);
    }

    @Test
    public void testMatchers() {
        assertEquals(GAMES, uriMatcher.match(Uri.parse("content://net.todd.scorekeeper/games")));
        assertEquals(GAME, uriMatcher.match(Uri.parse("content://net.todd.scorekeeper/games/123")));
        assertEquals(SCOREBOARDS, uriMatcher.match(Uri.parse("content://net.todd.scorekeeper/games/123/scoreboard")));
    }

    @Test
    public void testSegments() {
        Uri gameUri = Uri.parse("content://net.todd.scorekeeper/games/123");
        Uri scoreboardUri = Uri.parse("content://net.todd.scorekeeper/games/123/scoreboard");

        assertEquals("123", gameUri.getLastPathSegment());
        assertEquals("123", scoreboardUri.getPathSegments().get(1));
    }

    @Test
    public void testAddingToAUri() {
        Uri gamesUri = Uri.parse("content://net.todd.scorekeeper/games");
        Uri gameUri = Uri.parse("content://net.todd.scorekeeper/games/123");

        Uri modifiedUri = gamesUri.buildUpon().appendPath("123").build();
        assertEquals(modifiedUri, gameUri);
    }
}
