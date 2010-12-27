package net.todd.scorekeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HistoryView {
	private final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private final ListenerManager backPressedListenerManager = new ListenerManager();
	private final ListenerManager doneButtonListenerManager = new ListenerManager();
	private final ListenerManager clearButtonListenerManager = new ListenerManager();
	
	private final Activity activity;
	private final LinearLayout mainView;
	private final LinearLayout historyContainer;
	private final ScrollView mainScrollView;

	public HistoryView(Activity activity) {
		this.activity = activity;

		mainScrollView = new ScrollView(activity);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		mainScrollView.setFillViewport(true);

		mainView = new LinearLayout(activity);
		mainView.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams mainViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		mainViewLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainViewLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainViewLayoutParams);
		mainView.setOrientation(LinearLayout.VERTICAL);
		BackgroundUtil.setBackground(mainView);
		mainScrollView.addView(mainView);

		TextView title = new TextView(activity);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("History");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		historyContainer = new LinearLayout(activity);
		historyContainer.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams historyContainerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		historyContainerLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		historyContainerLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		historyContainer.setLayoutParams(historyContainerLayoutParams);
		historyContainer.setOrientation(LinearLayout.VERTICAL);
		mainView.addView(historyContainer);
		
		Button doneButton = new Button(activity);
		ButtonUtilities.setLayout(doneButton);
		doneButton.setText("Done");
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doneButtonListenerManager.notifyListeners();
			}
		});
		mainView.addView(doneButton);
		
		Button clearButton = new Button(activity);
		ButtonUtilities.setLayout(clearButton);
		clearButton.setText("Clear");
		clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearButtonListenerManager.notifyListeners();
			}
		});
		mainView.addView(clearButton);
	}

	public void setHistory(List<Game> allGames) {
		historyContainer.removeAllViews();
		for (Game game : allGames) {
			LinearLayout topRow = new LinearLayout(activity);
			topRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			topRow.setOrientation(LinearLayout.HORIZONTAL);
			historyContainer.addView(topRow);

			TextView gameType = new TextView(activity);
			gameType.setText(game.getGameType());
			gameType.setTextSize(UIConstants.TEXT_NORMAL_SIZE);
			gameType.setTextColor(UIConstants.TEXT_COLOR);
			topRow.addView(gameType);

			TextView gameOverTimestamp = new TextView(activity);
			gameOverTimestamp.setText(dateFormatter.format(game
					.getGameOverTimestamp()));
			gameOverTimestamp.setTextSize(UIConstants.TEXT_SMALL_SIZE);
			gameOverTimestamp.setTextColor(UIConstants.TEXT_COLOR);
			topRow.addView(gameOverTimestamp);

			LinearLayout bottomRow = new LinearLayout(activity);
			bottomRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			bottomRow.setOrientation(LinearLayout.HORIZONTAL);
			historyContainer.addView(bottomRow);

			StringBuilder scoreBoardText = new StringBuilder();

			List<ScoreBoardEntry> entries = game.getScoreBoard().getEntries();
			for (int i = 0; i < entries.size(); i++) {
				ScoreBoardEntry entry = entries.get(i);
				scoreBoardText.append(entry.getPlayer().getName()).append(":").append(entry.getScore());
				if (i < entries.size() - 1) {
					scoreBoardText.append(", ");
				}
			}

			TextView scoreBoardTextView = new TextView(activity);
			scoreBoardTextView.setTextColor(UIConstants.TEXT_COLOR);
			scoreBoardTextView.setText(scoreBoardText.toString());
			scoreBoardTextView.setTextSize(UIConstants.TEXT_TINY_SIZE);
			bottomRow.addView(scoreBoardTextView);
		}
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
		return mainScrollView;
	}
}
