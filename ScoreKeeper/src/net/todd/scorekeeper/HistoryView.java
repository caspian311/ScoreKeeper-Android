package net.todd.scorekeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HistoryView {
	private final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private final ListenerManager backPressedListenerManager = new ListenerManager();
	
	private final Activity activity;
	private final LinearLayout mainView;
	private final LinearLayout historyContainer;
	private final ScrollView mainScrollView;

	public HistoryView(Activity activity) {
		this.activity = activity;

		mainScrollView = new ScrollView(activity);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mainScrollView.setFillViewport(true);

		mainView = new LinearLayout(activity);
		mainView.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams mainViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		mainViewLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainViewLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainViewLayoutParams);
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainView.setBackgroundColor(0xFF3399CC);
		mainScrollView.addView(mainView);

		TextView title = new TextView(activity);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("History");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		historyContainer = new LinearLayout(activity);
		historyContainer.setGravity(Gravity.LEFT);
		LinearLayout.LayoutParams historyContainerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		historyContainerLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		historyContainerLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		historyContainer.setLayoutParams(historyContainerLayoutParams);
		historyContainer.setOrientation(LinearLayout.VERTICAL);
		mainView.addView(historyContainer);
	}

	public void setHistory(List<Game> allGames) {
		historyContainer.removeAllViews();
		for (Game game : allGames) {
			LinearLayout topRow = new LinearLayout(activity);
			topRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			topRow.setOrientation(LinearLayout.HORIZONTAL);
			historyContainer.addView(topRow);

			TextView gameType = new TextView(activity);
			gameType.setText(game.getGameType());
			gameType.setTextSize(25);
			gameType.setTextColor(0xFF000000);
			topRow.addView(gameType);

			TextView gameOverTimestamp = new TextView(activity);
			gameOverTimestamp.setText(dateFormatter.format(game
					.getGameOverTimestamp()));
			gameOverTimestamp.setTextSize(20);
			gameOverTimestamp.setTextColor(0xFF000000);
			topRow.addView(gameOverTimestamp);

			LinearLayout bottomRow = new LinearLayout(activity);
			bottomRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
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
			scoreBoardTextView.setTextColor(0xFF000000);
			scoreBoardTextView.setText(scoreBoardText.toString());
			scoreBoardTextView.setTextSize(15);
			bottomRow.addView(scoreBoardTextView);
		}
	}

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}

	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager.addListener(listener);
	}

	public View getView() {
		return mainScrollView;
	}
}
