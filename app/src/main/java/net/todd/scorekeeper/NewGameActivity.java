package net.todd.scorekeeper;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends Activity {
    private static final int PLAYERS_LOADER = 1;

    private Spinner gameTypeSpinner;
    private ListView availablePlayersList;
    private AvailablePlayerCurorAdapter availablePlayerListAdapter;
    private Button cancelButton;
    private Button nextButton;

    private Uri uri = Uri.parse("content://net.todd.scorekeeper.players");

    private List<Long> selectedPlayerIds = new ArrayList<Long>();
    private EditText gameNameText;
    private TextWatcher gameNameTextWatcher;
    private TextView noPlayersAvailableMessage;
    private LoaderManager.LoaderCallbacks<Cursor> loaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);

        noPlayersAvailableMessage = (TextView)findViewById(R.id.no_players_available);

        gameNameText = (EditText)findViewById(R.id.game_name_text);
        gameNameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateStartGameButton();
            }
        };

        gameTypeSpinner = (Spinner) findViewById(R.id.game_type_spinner);
        ArrayAdapter<CharSequence> gameTypeSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.game_types, android.R.layout.simple_spinner_item);
        gameTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(gameTypeSpinnerAdapter);

        availablePlayersList = (ListView)findViewById(R.id.available_players);
        availablePlayerListAdapter = new AvailablePlayerCurorAdapter(this);
        availablePlayersList.setAdapter(availablePlayerListAdapter);
        availablePlayerListAdapter.setPlayerSelectionChangeListener(new AvailablePlayerCurorAdapter.PlayerSelectionChangeListener() {
            public void playerSelectionChanged(long playerId, boolean isSelected) {
                if (isSelected) {
                    selectedPlayerIds.add(playerId);
                } else {
                    selectedPlayerIds.remove(playerId);
                }

                updateStartGameButton();
            }
        });

        cancelButton = (Button)findViewById(R.id.cancel_game_button);
        nextButton = (Button)findViewById(R.id.next_button);

        loaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(NewGameActivity.this, uri, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                availablePlayerListAdapter.swapCursor(data);

                if (data.getCount() == 0) {
                    gameNameText.setVisibility(View.GONE);
                    gameTypeSpinner.setVisibility(View.GONE);
                    availablePlayersList.setVisibility(View.GONE);

                    noPlayersAvailableMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                availablePlayerListAdapter.swapCursor(null);
            }
        };

        getLoaderManager().initLoader(PLAYERS_LOADER, null, loaderCallback);
    }

    private void updateStartGameButton() {
        nextButton.setEnabled(selectedPlayerIds.size() > 0 && gameNameText.getText().length() > 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        gameNameText.addTextChangedListener(gameNameTextWatcher);

        gameTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("scorekeeper", "game type selection made: " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName = gameNameText.getText().toString();
                GameConfiguration gameConfiguration = new GameConfiguration(gameName, selectedPlayerIds);
                Intent intent = new Intent(NewGameActivity.this, OrderPlayersActivity.class);
                intent.putExtra(GameConfiguration.class.getSimpleName(), gameConfiguration);
                startActivityForResult(intent, 0);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLoaderManager().restartLoader(PLAYERS_LOADER, null, loaderCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();

        gameNameText.removeTextChangedListener(gameNameTextWatcher);
        gameTypeSpinner.setOnItemSelectedListener(null);
        nextButton.setOnClickListener(null);
        cancelButton.setOnClickListener(null);
    }
}
