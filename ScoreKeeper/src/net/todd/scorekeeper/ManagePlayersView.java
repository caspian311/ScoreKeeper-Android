package net.todd.scorekeeper;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
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

	private int playerToRemove;

	public ManagePlayersView(Context context) {
		this.context = context;

		mainScrollView = new ScrollView(context);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		LinearLayout mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainView.setBackgroundColor(0xFF3399CC);
		mainScrollView.addView(mainView);

		TextView title = new TextView(context);
		title.setText("Manager Players");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		tableView = new TableLayout(context);
		tableView.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		tableView.setColumnStretchable(0, true);
		mainView.addView(tableView);

		TableRow controlsRow = new TableRow(context);
		controlsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT));
		tableView.addView(controlsRow);

		playerNameText = new EditText(context);
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
		doneButton.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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

	private void addPlayer(final int playerId, String playerName) {
		TableRow playerRow = new TableRow(context);
		playerRow.setId(playerId);
		playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT));
		tableView.addView(playerRow);

		TextView player = new TextView(context);
		player.setText(playerName);
		player.setTextSize(30);
		player.setTextColor(0xFF000000);
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

	public int getPlayerToRemove() {
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
}
