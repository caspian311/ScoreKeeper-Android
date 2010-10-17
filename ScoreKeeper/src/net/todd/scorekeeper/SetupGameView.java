package net.todd.scorekeeper;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SetupGameView {
	private final ScrollView mainScrollView;
	private final TableLayout allPlayersTable;
	private final Context context;

	private boolean isCurrentPlayerSelected;
	private int currentPlayerId;

	private final ListenerManager cancelButtonListenerManager = new ListenerManager();
	private final ListenerManager startGameButtonListenerManager = new ListenerManager();
	private final ListenerManager selectedPlayersChangedListenerManager = new ListenerManager();
	private final ListenerManager upButtonListenerManager = new ListenerManager();
	private final ListenerManager downButtonListenerManager = new ListenerManager();

	public SetupGameView(Context context) {
		this.context = context;

		mainScrollView = new ScrollView(context);
		mainScrollView.setFillViewport(true);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		LinearLayout mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mainView.setBackgroundColor(0xFF3399CC);
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainScrollView.addView(mainView);

		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Setup the Game");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		LinearLayout controlView = new LinearLayout(context);
		controlView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		controlView.setOrientation(LinearLayout.HORIZONTAL);
		controlView.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(controlView);

		Button cancelButton = new Button(context);
		LayoutParams cancelButtonLayout = new LayoutParams(150, 50);
		cancelButton.setLayoutParams(cancelButtonLayout);
		cancelButton.setText("Cancel");
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelButtonListenerManager.notifyListeners();
			}
		});
		controlView.addView(cancelButton);

		Button startGameButton = new Button(context);
		LayoutParams startGameButtonLayout = new LayoutParams(150, 50);
		startGameButton.setLayoutParams(startGameButtonLayout);
		startGameButton.setText("Start Game");
		startGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGameButtonListenerManager.notifyListeners();
			}
		});
		controlView.addView(startGameButton);

		allPlayersTable = new TableLayout(context);
		allPlayersTable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		allPlayersTable.setColumnStretchable(1, true);
		mainView.addView(allPlayersTable);
	}

	public View getView() {
		return mainScrollView;
	}

	public void setAllPlayers(List<Player> allPlayers) {
		allPlayersTable.removeAllViews();
		for (final Player player : allPlayers) {
			TableRow playerRow = new TableRow(context);
			playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));
			allPlayersTable.addView(playerRow);

			CheckBox playerSelection = new CheckBox(context);
			playerSelection.setChecked(player.istSelected());
			playerSelection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					isCurrentPlayerSelected = isChecked;
					currentPlayerId = player.getId();
					selectedPlayersChangedListenerManager.notifyListeners();
				}
			});
			playerRow.addView(playerSelection);

			TextView playerName = new TextView(context);
			playerName.setText(player.getName());
			playerName.setTextSize(30);
			playerName.setTextColor(0xFF000000);
			playerRow.addView(playerName);

			Button upButton = new Button(context);
//			upButton.setLayoutParams(new LayoutParams(50, 50));
			upButton.setText("+");
			upButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					upButtonListenerManager.notifyListeners();
				}
			});
			playerRow.addView(upButton);

			Button downButton = new Button(context);
//			downButton.setLayoutParams(new LayoutParams(50, 50));
			downButton.setText("-");
			downButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					downButtonListenerManager.notifyListeners();
				}
			});
			playerRow.addView(downButton);
		}
	}

	public void addSelectedPlayersChangedListener(Listener listener) {
		selectedPlayersChangedListenerManager.addListener(listener);
	}

	public boolean isCurrentPlayerSelected() {
		return isCurrentPlayerSelected;
	}

	public void addStartGameButtonListener(final Listener listener) {
		startGameButtonListenerManager.addListener(listener);
	}

	public void addCancelButtonListener(final Listener listener) {
		cancelButtonListenerManager.addListener(listener);
	}

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void addUpButtonListener(Listener listener) {
		upButtonListenerManager.addListener(listener);
	}

	public void addDownButtonListener(Listener listener) {
		downButtonListenerManager.addListener(listener);
	}

	public void popupErrorMessage() {
		new AlertDialog.Builder(context).setMessage("You must select at least 2 players.")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}
}
