package net.todd.scorekeeper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainPageView {
	private final RelativeLayout mainLayout;

	private final ListenerManager startGameButtonListenerManager = new ListenerManager();
	private final ListenerManager addPlayersButtonListenerManager = new ListenerManager();
	private final ListenerManager quitButtonListenerManager = new ListenerManager();
	private final ListenerManager historyButtonListenerManager = new ListenerManager();
	private final ListenerManager backPressedListenerManager = new ListenerManager();

	private final TextView version;

	public MainPageView(Context context) {
		mainLayout = new RelativeLayout(context);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		BackgroundUtil.setBackground(mainLayout);

		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		title.setText("Score Keeper");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(title);

		TableLayout tableView = new TableLayout(context);
		tableView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		tableView.setStretchAllColumns(true);
		tableView.setGravity(Gravity.CENTER);
		mainLayout.addView(tableView);

		TableRow firstRow = new TableRow(context);
		tableView.addView(firstRow);

		Button addPlayersButton = new Button(context);
		addPlayersButton.setText("Manage Players");
		addPlayersButton.setTextSize(20);
		addPlayersButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addPlayersButtonListenerManager.notifyListeners();
			}
		});
		firstRow.addView(addPlayersButton);

		Button historyButton = new Button(context);
		historyButton.setText("See History");
		historyButton.setTextSize(20);
		historyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				historyButtonListenerManager.notifyListeners();
			}
		});
		firstRow.addView(historyButton);

		TableRow secondRow = new TableRow(context);
		tableView.addView(secondRow);

		Button startGameButton = new Button(context);
		startGameButton.setText("New Game");
		startGameButton.setTextSize(20);
		startGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGameButtonListenerManager.notifyListeners();
			}
		});
		secondRow.addView(startGameButton);

		Button quitButton = new Button(context);
		quitButton.setText("Quit");
		quitButton.setTextSize(20);
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quitButtonListenerManager.notifyListeners();
			}
		});
		secondRow.addView(quitButton);

		version = new TextView(context);
		version.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		version.setTextSize(UIConstants.TEXT_SMALL_SIZE);
		version.setTextColor(UIConstants.TEXT_COLOR);
		version.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(version);
	}

	public View getView() {
		return mainLayout;
	}

	public void addQuitButtonListener(Listener listener) {
		quitButtonListenerManager.addListener(listener);
	}

	public void addManagePlayersButtonListener(Listener listener) {
		addPlayersButtonListenerManager.addListener(listener);
	}

	public void addStartButtonListener(Listener listener) {
		startGameButtonListenerManager.addListener(listener);
	}

	public void addHistoryButtonListener(Listener listener) {
		historyButtonListenerManager.addListener(listener);
	}

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}

	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager.addListener(listener);
	}

	public void setVersion(String versionText) {
		version.setText(versionText);
	}
}
