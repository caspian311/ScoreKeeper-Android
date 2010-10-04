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

public class MainView {
	private final RelativeLayout mainLayout;
	private final Button quitButton;
	private final Button addPlayersButton;

	public MainView(Context context) {
		mainLayout = new RelativeLayout(context);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mainLayout.setBackgroundColor(0xFF3399CC);

		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		title.setText("Score Keeper");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(title);

		TableLayout tableView = new TableLayout(context);
		tableView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		tableView.setStretchAllColumns(true);
		tableView.setGravity(Gravity.CENTER);
		mainLayout.addView(tableView);

		TableRow firstRow = new TableRow(context);
		tableView.addView(firstRow);

		addPlayersButton = new Button(context);
		addPlayersButton.setText("Manage Players");
		addPlayersButton.setTextSize(20);
		firstRow.addView(addPlayersButton);
		Button historyButton = new Button(context);
		historyButton.setText("See History");
		historyButton.setTextSize(20);
		firstRow.addView(historyButton);

		TableRow secondRow = new TableRow(context);
		tableView.addView(secondRow);

		Button startGameButton = new Button(context);
		startGameButton.setText("Start Game");
		startGameButton.setTextSize(20);
		secondRow.addView(startGameButton);
		quitButton = new Button(context);
		quitButton.setText("Quit");
		quitButton.setTextSize(20);
		secondRow.addView(quitButton);
	}

	public View getView() {
		return mainLayout;
	}

	public void addQuitButtonListener(final IListener listener) {
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}

	public void addPlayersButtonListener(final IListener listener) {
		addPlayersButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}
}
