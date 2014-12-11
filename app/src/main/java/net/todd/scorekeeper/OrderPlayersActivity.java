package net.todd.scorekeeper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Arrays;
import java.util.List;

public class OrderPlayersActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView orderedPlayerList;
    private OrderPlayerCursorAdapter adapter;

    private Uri uri;

    private static final int PLAYERS_LOADER = 1;
    private List<Long> orderedPlayerIds;
    private GameConfiguration gameConfiguration;
    private Button nextButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_players);

        nextButton = (Button)findViewById(R.id.next_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        orderedPlayerList = (ListView)findViewById(R.id.ordered_players_list);
        gameConfiguration = getIntent().getParcelableExtra(GameConfiguration.class.getSimpleName());

        uri = Uri.parse("content://net.todd.scorekeeper.players?ids=" + TextUtils.join(",", gameConfiguration.getSelectedPlayers()));

        adapter = new OrderPlayerCursorAdapter(this);
        orderedPlayerList.setAdapter(adapter);

        getLoaderManager().initLoader(PLAYERS_LOADER, null, this);
    }

    private void movePlayerDown(long playerId) {
    }

    private void movePlayerUp(long playerId) {
    }

    @Override
    protected void onResume() {
        super.onResume();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(GameConfiguration.class.getSimpleName(), gameConfiguration);
                setResult(1, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter.setMovePlayerUpListener(new OrderPlayerCursorAdapter.MovePlayerListener() {
            @Override
            public void playerMoved(long playerId) {
                movePlayerUp(playerId);
            }
        });
        adapter.setMovePlayerDownListener(new OrderPlayerCursorAdapter.MovePlayerListener() {
            @Override
            public void playerMoved(long playerId) {
                movePlayerDown(playerId);
            }
        });


        getLoaderManager().restartLoader(PLAYERS_LOADER, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        nextButton.setOnClickListener(null);
        cancelButton.setOnClickListener(null);

        adapter.setMovePlayerUpListener(null);
        adapter.setMovePlayerDownListener(null);

        getLoaderManager().destroyLoader(PLAYERS_LOADER);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
