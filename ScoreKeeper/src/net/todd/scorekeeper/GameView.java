package net.todd.scorekeeper;

import java.util.Map;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameView {
	private final LinearLayout mainView;
	private final TextView playerName;
	private final Button nextPlayerButton;
	private final EditText score;
	private final Button previousPlayerButton;
	private final TableLayout scoreBoardTable;
	private final Context context;

	public GameView(Context context) {
		this.context = context;
		
		mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mainView.setBackgroundColor(0xFF3399CC);
		mainView.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.setOrientation(LinearLayout.VERTICAL);
		
		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Start Game");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);
		
		playerName = new TextView(context);
		playerName.setTextColor(0xFF000000);
		playerName.setTextSize(45);
		playerName.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(playerName);
		
		score = new EditText(context);
		score.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		score.setWidth(100);
		score.setLines(1);
		score.setGravity(Gravity.CENTER_HORIZONTAL);
		score.setInputType(InputType.TYPE_CLASS_NUMBER);
		mainView.addView(score);
		
		LinearLayout buttonLayout = new LinearLayout(context);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(buttonLayout);
		
		previousPlayerButton = new Button(context);
		previousPlayerButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		previousPlayerButton.setText("Previous Player");
		previousPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(previousPlayerButton);

		nextPlayerButton = new Button(context);
		nextPlayerButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		nextPlayerButton.setText("Next Player");
		nextPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(nextPlayerButton);
		
		TextView scoreBoardTitle = new TextView(context);
		scoreBoardTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		scoreBoardTitle.setText("Score Board");
		scoreBoardTitle.setTextSize(24);
		scoreBoardTitle.setTextColor(0xFF000000);
		mainView.addView(scoreBoardTitle);
		
		scoreBoardTable = new TableLayout(context);
		scoreBoardTable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		scoreBoardTable.setColumnStretchable(0, true);
		mainView.addView(scoreBoardTable);
	}

	public void setCurrentPlayer(Player player, int score) {
		playerName.setText(player.getName() + " : " + score);
	}
	
	public View getView() {
		return mainView;
	}

	public int getScore() {
		return Integer.parseInt(score.getText().toString());
	}
	
	public void addPreviousPlayerButtonListener(final Listener listener) {
		previousPlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}
	
	public void addNextPlayerButtonListener(final Listener listener) {
		nextPlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}

	public void clearScore() {
		score.setText("");
	}
	
	public void setScoreBoard(Map<Player, Integer> scoreBoard) {
		scoreBoardTable.removeAllViews();
		
		for (Player player : scoreBoard.keySet()) {
			TableRow playerRow = new TableRow(context);
			scoreBoardTable.addView(playerRow);
			
			TextView playerView = new TextView(context);
			playerView.setText(player.getName());
			playerView.setTextSize(24);
			playerView.setTextColor(0xFF000000);
			playerRow.addView(playerView);
			
			TextView playerScoreView = new TextView(context);
			Integer score = scoreBoard.get(player);
			playerScoreView.setText("" + score);
			playerScoreView.setTextSize(24);
			playerScoreView.setTextColor(0xFF000000);
			playerRow.addView(playerScoreView);
		}
	}
}
