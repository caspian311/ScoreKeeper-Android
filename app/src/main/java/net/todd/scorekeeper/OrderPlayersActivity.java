package net.todd.scorekeeper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderPlayersActivity extends Activity {
    private ListView orderedPlayerList;
    private OrderPlayerCursorAdapter adapter;

    private Uri uri;

    private static final int PLAYERS_LOADER = 1;
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

        adapter = new OrderPlayerCursorAdapter(this);
        orderedPlayerList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(GameConfiguration.class.getSimpleName(), gameConfiguration);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        adapter.setMovePlayerUpListener(new OrderPlayerCursorAdapter.MovePlayerListener() {
            @Override
            public void playerMoved(long playerId) {
                gameConfiguration.movePlayerUp(playerId);
                adapter.swapCursor(gameConfiguration.getPlayersAsCursor());
            }
        });
        adapter.setMovePlayerDownListener(new OrderPlayerCursorAdapter.MovePlayerListener() {
            @Override
            public void playerMoved(long playerId) {
                gameConfiguration.movePlayerDown(playerId);
                adapter.swapCursor(gameConfiguration.getPlayersAsCursor());
            }
        });
        adapter.swapCursor(gameConfiguration.getPlayersAsCursor());
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
}
