package net.todd.scorekeeper;

import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;
import net.todd.scorekeeper.data.ScoreBoardEntry;
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameView {
	private final ScrollView mainScrollView;
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

		mainScrollView = new ScrollView(context);
		mainScrollView.setFillViewport(true);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		LinearLayout mainView = new LinearLayout(context);
		mainView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams mainViewLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		mainViewLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainViewLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainViewLayoutParams);
		BackgroundUtil.setBackground(mainView);
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainScrollView.addView(mainView);

		LinearLayout playerData = new LinearLayout(context);
		playerData.setOrientation(LinearLayout.HORIZONTAL);
		playerData.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		playerData.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(playerData);

		playerName = new TextView(context);
		playerName.setTextColor(UIConstants.TEXT_COLOR);
		playerName.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		playerName.setGravity(Gravity.CENTER_HORIZONTAL);
		playerData.addView(playerName);

		TextView colonText = new TextView(context);
		colonText.setGravity(Gravity.CENTER_HORIZONTAL);
		colonText.setTextColor(UIConstants.TEXT_COLOR);
		colonText.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		colonText.setText(" : ");
		playerData.addView(colonText);

		playerScore = new TextView(context);
		playerScore.setTextColor(UIConstants.TEXT_COLOR);
		playerScore.setTextSize(UIConstants.TEXT_NORMAL_SIZE);
		playerScore.setGravity(Gravity.CENTER_HORIZONTAL);
		playerData.addView(playerScore);

		score = new EditText(context);
		score.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		score.setWidth(100);
		score.setLines(1);
		score.setGravity(Gravity.CENTER_HORIZONTAL);
		score.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
		mainView.addView(score);

		LinearLayout buttonLayout = new LinearLayout(context);
		buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		buttonLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		buttonLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		buttonLayout.setLayoutParams(buttonLayoutParams);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		mainView.addView(buttonLayout);

		previousPlayerButton = new Button(context);
		ButtonUtilities.setLayout(previousPlayerButton);
		previousPlayerButton.setText("Previous");
		previousPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(previousPlayerButton);

		nextPlayerButton = new Button(context);
		ButtonUtilities.setLayout(nextPlayerButton);
		nextPlayerButton.setText("Next");
		nextPlayerButton.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(nextPlayerButton);

		TextView scoreBoardTitle = new TextView(context);
		scoreBoardTitle.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams scoreBoardTitleLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		scoreBoardTitleLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		scoreBoardTitleLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		scoreBoardTitle.setLayoutParams(scoreBoardTitleLayoutParams);
		scoreBoardTitle.setText("Score Board");
		scoreBoardTitle.setTextSize(24);
		scoreBoardTitle.setTextColor(UIConstants.TEXT_COLOR);
		mainView.addView(scoreBoardTitle);

		scoreBoardTable = new TableLayout(context);
		scoreBoardTable.setGravity(Gravity.CENTER_HORIZONTAL);
		TableLayout.LayoutParams scoreBoardLayoutParams = new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		scoreBoardLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		scoreBoardLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		scoreBoardTable.setColumnStretchable(0, true);
		mainView.addView(scoreBoardTable, scoreBoardLayoutParams);

		Button gameOverButton = new Button(context);
		gameOverButton.setGravity(Gravity.CENTER_HORIZONTAL);
		ButtonUtilities.setLayout(gameOverButton);
		gameOverButton.setText("Game Over");
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
		return mainScrollView;
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
			playerView.setTextSize(UIConstants.TEXT_SMALL_SIZE);
			playerView.setTextColor(UIConstants.TEXT_COLOR);
			playerRow.addView(playerView);

			TextView playerScoreView = new TextView(context);
			playerScoreView.setText("" + scoreBoardEntry.getScore());
			playerScoreView.setTextSize(UIConstants.TEXT_SMALL_SIZE);
			playerScoreView.setTextColor(UIConstants.TEXT_COLOR);
			playerRow.addView(playerScoreView);
		}
	}

	public void closeSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
		new AlertDialog.Builder(context).setMessage("Are you sure the game is over?")
				.setPositiveButton("Game Over", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						gameOverConfirmationListenerManager.notifyListeners();
					}
				}).setNegativeButton("Still Playing", new DialogInterface.OnClickListener() {
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
