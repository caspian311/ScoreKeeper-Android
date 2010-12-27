package net.todd.scorekeeper;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManagePlayersView {
	private final Context context;
	private final ScrollView mainScrollView;
	private final TableLayout tableView;
	private final EditText playerNameText;

	private final ListenerManager addPlayerButtonListenerManager = new ListenerManager();
	private final ListenerManager playerRemovedListenerManager = new ListenerManager();
	private final ListenerManager doneButtonListenerManager = new ListenerManager();
	private final ListenerManager backPressedListenerManager = new ListenerManager();

	private String playerToRemove;

	public ManagePlayersView(Context context) {
		this.context = context;

		mainScrollView = new ScrollView(context);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		mainScrollView.setFillViewport(true);

		LinearLayout mainView = new LinearLayout(context);
		mainView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams mainViewLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		mainView.setLayoutParams(mainViewLayoutParams);
		mainView.setOrientation(LinearLayout.VERTICAL);
		BackgroundUtil.setBackground(mainView);
		mainScrollView.addView(mainView);

		TextView title = new TextView(context);
		title.setText("Manager Players");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		tableView = new TableLayout(context);
		tableView.setGravity(Gravity.CENTER_HORIZONTAL);
		TableLayout.LayoutParams tableViewLayoutParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		tableViewLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		tableViewLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		tableView.setLayoutParams(tableViewLayoutParams);
		tableView.setColumnStretchable(0, true);
		mainView.addView(tableView);

		TableRow controlsRow = new TableRow(context);
		controlsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT));
		tableView.addView(controlsRow);

		playerNameText = new EditText(context) {
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					addPlayerButtonListenerManager.notifyListeners();
					return true;
				}
				
				return super.onKeyDown(keyCode, event);
			};
		};
		playerNameText.setLines(1);
		controlsRow.addView(playerNameText);

		Button addPlayerButton = new Button(context);
		addPlayerButton.setText("Add Player");
		addPlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addPlayerButtonListenerManager.notifyListeners();
			}
		});
		controlsRow.addView(addPlayerButton);

		Button doneButton = new Button(context);
		doneButton.setGravity(Gravity.CENTER_HORIZONTAL);
		TableLayout.LayoutParams doneButtonLayoutParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		doneButtonLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		doneButtonLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		doneButton.setLayoutParams(doneButtonLayoutParams);
		doneButton.setText("Done");
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doneButtonListenerManager.notifyListeners();
			}
		});
		mainView.addView(doneButton);
	}

	public View getView() {
		return mainScrollView;
	}

	public String getPlayerNameText() {
		return playerNameText.getText().toString();
	}

	public void clearPlayerNameText() {
		playerNameText.setText("");
	}

	public void addAddPlayerButtonListener(final Listener listener) {
		addPlayerButtonListenerManager.addListener(listener);
	}

	private void addPlayer(final String playerId, String playerName) {
		TableRow playerRow = new TableRow(context);
		playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT));
		tableView.addView(playerRow);

		TextView player = new TextView(context);
		player.setText(playerName);
		player.setTextSize(UIConstants.TEXT_NORMAL_SIZE);
		player.setTextColor(UIConstants.TEXT_COLOR);
		playerRow.addView(player);

		Button removePlayerButton = new Button(context);
		removePlayerButton.setText("Remove");
		removePlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playerToRemove = playerId;
				playerRemovedListenerManager.notifyListeners();
			}
		});
		playerRow.addView(removePlayerButton);
	}

	public void addRemovePlayerButtonListener(Listener listener) {
		playerRemovedListenerManager.addListener(listener);
	}

	public String getPlayerToRemove() {
		return playerToRemove;
	}

	public void setPlayers(List<Player> players) {
		clearCurrentPlayers();

		for (Player player : players) {
			addPlayer(player.getId(), player.getName());
		}
	}

	private void clearCurrentPlayers() {
		while (tableView.getChildCount() > 1) {
			tableView.removeViewAt(tableView.getChildCount() - 1);
		}
	}

	public void addDoneButtonListener(Listener listener) {
		doneButtonListenerManager.addListener(listener);
	}

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}
	
	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager .addListener(listener);
	}
}
