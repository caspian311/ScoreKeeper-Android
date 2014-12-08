package net.todd.scorekeeper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayersContentProvider extends ContentProvider {
    public static final String NEW_NAME = "new_name";

    private List<Player> data;

    @Override
    public boolean onCreate() {
        return false;
    }

    public PlayersContentProvider() {
        Player player1 = new Player();
        player1.setId(123);
        player1.setName("Matt");

        Player player2 = new Player();
        player2.setId(456);
        player2.setName("Abbi");

        Player player3 = new Player();
        player3.setId(789);
        player3.setName("Caleb");

        Player player4 = new Player();
        player4.setId(987);
        player4.setName("Aurelia");

        Player player5 = new Player();
        player5.setId(654);
        player5.setName("Peter");

        data = new ArrayList<Player>(Arrays.asList(player1, player2, player3, player4, player5));
    }

    @Override
    public PlayersCursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return new PlayersCursor(data);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Player player = new Player();
        player.setName(values.getAsString(NEW_NAME));
        data.add(player);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Player target = getPlayerById(Long.parseLong(selection));

        if (target != null) {
            data.remove(target);
        }

        return 0;
    }

    private Player getPlayerById(long playerId) {
        Player target = null;
        for (Player player : data) {
            if (player.getId() == playerId) {
                target = player;
                break;
            }
        }
        return target;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
