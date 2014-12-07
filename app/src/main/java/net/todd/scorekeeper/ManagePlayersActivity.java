package net.todd.scorekeeper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ManagePlayersActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PLAYERS_LOADER = 1;
    private Button addPlayerButton;
    private EditText newNameTextField;

    private TextWatcher newNameTextWatcher;
    private SimpleCursorAdapter adapter;
    private Button doneButton;

    private Uri uri = Uri.parse("content://net.todd.scorekeeper.players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_players);

        addPlayerButton = (Button) findViewById(R.id.add_player_button);
        newNameTextField = (EditText) findViewById(R.id.new_player_name);
        doneButton = (Button) findViewById(R.id.done_managing_players);

        adapter = new SimpleCursorAdapter(this, R.layout.player, null, new String[]{"name"}, new int[]{R.id.player_name}, 0);

        ListView listView = (ListView) findViewById(R.id.players_list);
        listView.setAdapter(adapter);

        newNameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                addPlayerButton.setEnabled(newNameTextField.getText().length() != 0);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        newNameTextField.addTextChangedListener(newNameTextWatcher);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PlayersContentProvider.NEW_NAME, newNameTextField.getText().toString());

                newNameTextField.setText("");

                new AsyncQueryHandler(getContentResolver()) {
                    @Override
                    protected void onInsertComplete(int token, Object cookie, Uri uri) {
                        super.onInsertComplete(token, cookie, uri);

                        getLoaderManager().restartLoader(PLAYERS_LOADER, null, ManagePlayersActivity.this);
                    }
                }.startInsert(-1, null, uri, contentValues);
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLoaderManager().initLoader(PLAYERS_LOADER, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        addPlayerButton.setOnClickListener(null);
        doneButton.setOnClickListener(null);
        newNameTextField.removeTextChangedListener(newNameTextWatcher);

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
