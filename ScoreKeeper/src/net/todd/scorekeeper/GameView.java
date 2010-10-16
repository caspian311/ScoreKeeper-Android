package net.todd.scorekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
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
	private final Activity context;
	private Listener backPressedListener;
	private Listener cancelGameListener;
	private final TextView playerScore;

	private final ListenerManager gameOverButtonListenerManager = new ListenerManager();
	private final ListenerManager gameOverConfirmationListenerManager = new ListenerManager();
	
	public GameView(Activity context) {
		this.context = context;

		mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
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

		LinearLayout playerData = new LinearLayout(context);
		playerData.setOrientation(LinearLayout.HORIZONTAL);
		playerData.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		playerData.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(playerData);
		
		playerName = new TextView(context);
		playerName.setTextColor(0xFF000000);
		playerName.setTextSize(45);
		playerName.setGravity(Gravity.CENTER_HORIZONTAL);
		playerData.addView(playerName);

		TextView colonText = new TextView(context);
		colonText.setGravity(Gravity.CENTER_HORIZONTAL);
		colonText.setTextColor(0xFF000000);
		colonText.setTextSize(45);
		colonText.setText(" : ");
		playerData.addView(colonText);
		
		playerScore = new TextView(context);
		playerScore.setTextColor(0xFF000000);
		playerScore.setTextSize(45);
		playerScore.setGravity(Gravity.CENTER_HORIZONTAL);
		playerData.addView(playerScore);
		
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
		previousPlayerButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		previousPlayerButton.setText("Previous Player");
		previousPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(previousPlayerButton);

		nextPlayerButton = new Button(context);
		nextPlayerButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		nextPlayerButton.setText("Next Player");
		nextPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(nextPlayerButton);

		TextView scoreBoardTitle = new TextView(context);
		scoreBoardTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		scoreBoardTitle.setText("Score Board");
		scoreBoardTitle.setTextSize(24);
		scoreBoardTitle.setTextColor(0xFF000000);
		mainView.addView(scoreBoardTitle);

		scoreBoardTable = new TableLayout(context);
		scoreBoardTable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		scoreBoardTable.setColumnStretchable(0, true);
		mainView.addView(scoreBoardTable);
		
		Button gameOverButton = new Button(context);
		gameOverButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		gameOverButton.setText("Game Over");
		gameOverButton.setGravity(Gravity.CENTER_HORIZONTAL);
		gameOverButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameOverButtonListenerManager.notifyListeners();
			}
		});
		mainView.addView(gameOverButton);
	}

	public void setCurrentPlayer(Player player) {
		playerName.setText(player.getName());
	}

	public void setCurrentPlayersScore(int score) {
		playerScore.setText("" + score);
	}
	
	public View getView() {
		return mainView;
	}

	public int getScore() {
		String scoreString = score.getText().toString();
		int scoreInt = 0;
		try {
			scoreInt = Integer.parseInt(scoreString);
		} catch (NumberFormatException e) {
		}
		return scoreInt;
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

	public void setScoreBoard(ScoreBoard scoreBoard) {
		scoreBoardTable.removeAllViews();

		for (ScoreBoardEntry scoreBoardEntry : scoreBoard.getEntries()) {
			TableRow playerRow = new TableRow(context);
			scoreBoardTable.addView(playerRow);

			TextView playerView = new TextView(context);
			playerView.setText(scoreBoardEntry.getPlayer().getName());
			playerView.setTextSize(24);
			playerView.setTextColor(0xFF000000);
			playerRow.addView(playerView);

			TextView playerScoreView = new TextView(context);
			playerScoreView.setText("" + scoreBoardEntry.getScore());
			playerScoreView.setTextSize(24);
			playerScoreView.setTextColor(0xFF000000);
			playerRow.addView(playerScoreView);
		}
	}

	public void closeSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
	}

	public void onBackPressed() {
		backPressedListener.handle();
	}

	public void addBackPressedListener(Listener listener) {
		this.backPressedListener = listener;
	}

	public void addCancelGameListener(Listener listener) {
		this.cancelGameListener = listener;
	}
	
	public void addGameOverButtonListener(Listener listener) {
		gameOverButtonListenerManager.addListener(listener);
	}

	public void popupNoBackButtonDialog() {
		new AlertDialog.Builder(context)
				.setMessage("If you leave this page you will lose any game data.  Is that ok?")
				.setPositiveButton("Leave page", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						cancelGameListener.handle();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	public void popupGameOverConfirmation() {
		new AlertDialog.Builder(context)
		.setMessage("Are you sure the game is over?")
		.setPositiveButton("Game Over", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				gameOverConfirmationListenerManager.notifyListeners();
			}
		}).setNegativeButton("Stll Playing", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).show();
	}
	
	public void addGameOverConfirmationListener(Listener listener) {
		gameOverConfirmationListenerManager.addListener(listener);
	}
}
