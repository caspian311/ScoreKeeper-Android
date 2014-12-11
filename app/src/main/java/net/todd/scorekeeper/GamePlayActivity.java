package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GamePlayActivity extends Activity {
    private TextView titleView;
    private GameConfiguration gameConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play_activity);

        gameConfiguration = getIntent().getParcelableExtra(GameConfiguration.class.getSimpleName());
        titleView = (TextView)findViewById(R.id.game_title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        titleView.setText(gameConfiguration.getGameName());
    }
}
