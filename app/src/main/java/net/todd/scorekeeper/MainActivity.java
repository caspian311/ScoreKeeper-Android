package net.todd.scorekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button managePlayersButton;
    private Button newGameButton;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managePlayersButton = (Button)findViewById(R.id.manage_players_button);
        newGameButton = (Button)findViewById(R.id.new_game_button);
        historyButton = (Button)findViewById(R.id.history_button);
    }

    @Override
    protected void onResume() {
        super.onResume();

        managePlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagePlayersActivity.class);
                startActivity(intent);
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        managePlayersButton.setOnClickListener(null);
        newGameButton.setOnClickListener(null);
        historyButton.setOnClickListener(null);
    }
}
