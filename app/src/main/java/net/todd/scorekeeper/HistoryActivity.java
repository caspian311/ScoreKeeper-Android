package net.todd.scorekeeper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int GAME_HISTORY_LOADER = 1;
    private Button doneButton;
    private ListView gameHistoryList;
    private GameHistoryAdapter adapter;

    private final Uri gamesUri = Uri.parse("content://net.todd.scorekeeper/games");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        doneButton = (Button)findViewById(R.id.done_button);
        gameHistoryList = (ListView)findViewById(R.id.game_history);

        adapter = new GameHistoryAdapter(this);
        gameHistoryList.setAdapter(adapter);

        getLoaderManager().initLoader(GAME_HISTORY_LOADER, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLoaderManager().restartLoader(GAME_HISTORY_LOADER, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        doneButton.setOnClickListener(null);

        getLoaderManager().destroyLoader(GAME_HISTORY_LOADER);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, gamesUri, null, null, null, null);
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
