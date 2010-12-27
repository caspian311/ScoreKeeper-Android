package net.todd.scorekeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HistoryView {
	private final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private final ListenerManager backPressedListenerManager = new ListenerManager();
	private final ListenerManager doneButtonListenerManager = new ListenerManager();
	private final ListenerManager clearButtonListenerManager = new ListenerManager();
	private final ListenerManager clearHistoryConfirmationListenerManager = new ListenerManager();
	private final ListenerManager clearGameConfirmationListenerManager = new ListenerManager();
	private final ListenerManager clearGameButtonPressedListenerManager = new ListenerManager();

	private final Activity activity;
	private final LinearLayout mainView;
	private final ScrollView historyContainer;

	private Game selectedGameToRemove;
	private final Button clearButton;

	public HistoryView(Activity activity) {
		this.activity = activity;

		mainView = new LinearLayout(activity);
		mainView.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams mainViewLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		mainViewLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainViewLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainViewLayoutParams);
		mainView.setOrientation(LinearLayout.VERTICAL);
		BackgroundUtil.setBackground(mainView);

		TextView title = new TextView(activity);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("History");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		historyContainer = new ScrollView(activity);
		LinearLayout.LayoutParams historyContainerLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, 300);
		historyContainerLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		historyContainerLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		historyContainer.setLayoutParams(historyContainerLayoutParams);
		mainView.addView(historyContainer);

		LinearLayout buttonLayout = new LinearLayout(activity);
		buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		buttonLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		buttonLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		buttonLayout.setLayoutParams(buttonLayoutParams);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		mainView.addView(buttonLayout);

		clearButton = new Button(activity);
		ButtonUtilities.setLayout(clearButton);
		clearButton.setText("Clear");
		clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearButtonListenerManager.notifyListeners();
			}
		});
		buttonLayout.addView(clearButton);

		Button doneButton = new Button(activity);
		ButtonUtilities.setLayout(doneButton);
		doneButton.setText("Done");
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doneButtonListenerManager.notifyListeners();
			}
		});
		buttonLayout.addView(doneButton);
	}

	public void setHistory(List<Game> allGames) {
		historyContainer.removeAllViews();

		TableLayout historyTable = new TableLayout(activity);
		historyTable.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
		historyTable.setColumnStretchable(0, true);
		historyContainer.addView(historyTable);

		for (int gameIndex = 0; gameIndex < allGames.size(); gameIndex++) {
			final Game game = allGames.get(gameIndex);
			TableRow firstGameRow = new TableRow(activity);
			historyTable.addView(firstGameRow);

			TextView gameOverTimestamp = new TextView(activity);
			gameOverTimestamp.setText(dateFormatter.format(game.getGameOverTimestamp()));
			gameOverTimestamp.setTextSize(UIConstants.TEXT_SMALL_SIZE);
			gameOverTimestamp.setTextColor(UIConstants.TEXT_COLOR);
			firstGameRow.addView(gameOverTimestamp);

			ImageButton removeGameButton = new ImageButton(activity);
			removeGameButton.setImageDrawable(activity.getResources()
					.getDrawable(R.drawable.delete));
			removeGameButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					selectedGameToRemove = game;
					clearGameButtonPressedListenerManager.notifyListeners();
				}
			});
			firstGameRow.addView(removeGameButton);

			StringBuilder scoreBoardText = new StringBuilder();
			List<ScoreBoardEntry> entries = game.getScoreBoard().getEntries();
			for (int i = 0; i < entries.size(); i++) {
				ScoreBoardEntry entry = entries.get(i);
				scoreBoardText.append(entry.getPlayer().getName()).append(":")
						.append(entry.getScore());
				if (i < entries.size() - 1) {
					scoreBoardText.append(", ");
				}
			}

			TableRow secondGameRow = new TableRow(activity);
			historyTable.addView(secondGameRow);

			TextView scoreBoardTextView = new TextView(activity);
			TableRow.LayoutParams scorBoardTextLayoutParams = new TableRow.LayoutParams(
					TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
			scorBoardTextLayoutParams.span = 2;
			scoreBoardTextView.setLayoutParams(scorBoardTextLayoutParams);
			scoreBoardTextView.setTextColor(UIConstants.TEXT_COLOR);
			scoreBoardTextView.setText(scoreBoardText.toString());
			scoreBoardTextView.setTextSize(UIConstants.TEXT_TINY_SIZE);
			secondGameRow.addView(scoreBoardTextView);

			if (gameIndex < allGames.size() - 1) {
				TableRow borderGameRow = new TableRow(activity);
				historyTable.addView(borderGameRow);

				View line = new View(activity);
				line.setBackgroundColor(UIConstants.TEXT_COLOR);
				TableRow.LayoutParams lineLayoutParam = new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT, 2);
				lineLayoutParam.span = 2;
				line.setLayoutParams(lineLayoutParam);
				borderGameRow.addView(line);
			}
		}
	}

	public void setClearButtonEnabled(boolean isEnabled) {
		clearButton.setEnabled(isEnabled);
	}

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}

	public void addClearButtonPressedListener(Listener listener) {
		clearButtonListenerManager.addListener(listener);
	}

	public void addDonePressedListener(Listener listener) {
		doneButtonListenerManager.addListener(listener);
	}

	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager.addListener(listener);
	}

	public View getView() {
		return mainView;
	}

	public void confirmClearingHistory() {
		new AlertDialog.Builder(activity)
				.setMessage(
						"Are you sure you want to clear all game data? "
								+ "This action cannot be undone.")
				.setPositiveButton("Clear History", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearHistoryConfirmationListenerManager.notifyListeners();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	public void confirmClearingGame() {
		new AlertDialog.Builder(activity)
				.setMessage(
						"Are you sure you want to remove this game from the history? "
								+ "This action cannot be undone.")
				.setPositiveButton("Remove Game", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearGameConfirmationListenerManager.notifyListeners();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	public void addClearHistoryConfirmationListener(Listener listener) {
		clearHistoryConfirmationListenerManager.addListener(listener);
	}

	public void addClearGameConfirmationListener(Listener listener) {
		clearGameConfirmationListenerManager.addListener(listener);
	}

	public Game getSelectedGame() {
		return selectedGameToRemove;
	}

	public void addClearGameButtonPressedListener(Listener listener) {
		clearGameButtonPressedListenerManager.addListener(listener);
	}
}
