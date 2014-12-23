package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {
    private TextView winnerName;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);

        winnerName = (TextView)findViewById(R.id.winner_name);
        doneButton = (Button)findViewById(R.id.done_button);
    }

    @Override
    protected void onResume() {
        super.onResume();

        winnerName.setText(getIntent().getStringExtra(Constants.WINNER_NAME));

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        doneButton.setOnClickListener(null);
    }
}
