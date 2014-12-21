package net.todd.scorekeeper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GamePlayActivity extends Activity {
    private TextView titleView;
    private GameConfiguration gameConfiguration;
    private Button gameOverButton;
    private Button nextPlayerButton;
    private EditText playerScore;
    private ListView scoreBoard;
    private ScoreBoardAdapter scoreBoardAdapter;
    private GameModel gameModel;
    private TextView playersTurnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play_activity);

        gameConfiguration = getIntent().getParcelableExtra(GameConfiguration.class.getSimpleName());
        gameModel = new GameModel(gameConfiguration);

        titleView = (TextView)findViewById(R.id.game_title);

        gameOverButton = (Button)findViewById(R.id.game_over_button);
        nextPlayerButton = (Button)findViewById(R.id.next_player_button);

        playersTurnText = (TextView)findViewById(R.id.players_turn_text);
        playerScore = (EditText)findViewById(R.id.players_score_field);

        scoreBoard = (ListView)findViewById(R.id.score_board);
        scoreBoardAdapter = new ScoreBoardAdapter(this, gameModel.getScoreBoardCursor());
        scoreBoard.setAdapter(scoreBoardAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        titleView.setText(gameConfiguration.getGameName());
        playersTurnText.setText(gameModel.getCurrentPlayerName());
        playerScore.setText("");

        nextPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameModel.updateCurrentPlayerScore(getCurrentScore());
                playerScore.setText("");
                gameModel.goToNextPlayer();
                playersTurnText.setText(gameModel.getCurrentPlayerName());
                scoreBoardAdapter.swapCursor(gameModel.getScoreBoardCursor());
            }
        });

        gameOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().insert(Uri.parse("content://net.todd.scorekeeper.games"), gameModel.getGameStats());

                Intent intent = new Intent(GamePlayActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private int getCurrentScore() {
        int score = 0;
        try {
            score = Integer.parseInt(playerScore.getText().toString());
        } catch (NumberFormatException e) {
            Log.e("scorekeeper", "Score is not a valid number: " + playerScore.getText().toString());
        }
        return score;
    }

    @Override
    protected void onPause() {
        super.onPause();

        gameOverButton.setOnClickListener(null);
        nextPlayerButton.setOnClickListener(null);
    }
}
