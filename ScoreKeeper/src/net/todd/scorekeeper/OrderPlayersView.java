package net.todd.scorekeeper;

import java.util.*;

import net.todd.scorekeeper.data.Player;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class OrderPlayersView {
	private final ScrollView mainScrollView;
	private final TableLayout allPlayersTable;
	private final Context context;

	private boolean isCurrentPlayerSelected;
	private String currentPlayerId;

	private final ListenerManager doneButtonListenerManager = new ListenerManager();
	private final ListenerManager upButtonListenerManager = new ListenerManager();
	private final ListenerManager downButtonListenerManager = new ListenerManager();
	private final ListenerManager backPressedListenerManager = new ListenerManager();

	public OrderPlayersView(Context context) {
		this.context = context;

		mainScrollView = new ScrollView(context);
		mainScrollView.setFillViewport(true);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		LinearLayout mainView = new LinearLayout(context);
		mainView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		mainLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainLayoutParams);
		BackgroundUtil.setBackground(mainView);
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainScrollView.addView(mainView);

		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Order Players");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		LinearLayout controlView = new LinearLayout(context);
		controlView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams controlViewLayouParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		controlViewLayouParams.leftMargin = UIConstants.MARGIN_SIZE;
		controlViewLayouParams.rightMargin = UIConstants.MARGIN_SIZE;
		controlView.setLayoutParams(controlViewLayouParams);
		controlView.setOrientation(LinearLayout.HORIZONTAL);
		mainView.addView(controlView);

		Button doneButton = new Button(context);
		doneButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, UIConstants.BUTTON_HEIGHT));
		doneButton.setText("Done");
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doneButtonListenerManager.notifyListeners();
			}
		});
		controlView.addView(doneButton);

		allPlayersTable = new TableLayout(context);
		allPlayersTable.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		allPlayersTable.setGravity(Gravity.CENTER_HORIZONTAL);
		TableLayout.LayoutParams allPlayersTableLayoutParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		allPlayersTableLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		allPlayersTableLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		allPlayersTable.setLayoutParams(allPlayersTableLayoutParams);

		allPlayersTable.setColumnStretchable(2, true);
		mainView.addView(allPlayersTable);
	}

	public View getView() {
		return mainScrollView;
	}

	public void setAllPlayers(List<Player> allPlayers) {
		allPlayersTable.removeAllViews();
		for (final Player player : allPlayers) {
			TableRow playerRow = new TableRow(context);
			playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));
			playerRow.setGravity(Gravity.CENTER_VERTICAL);
			allPlayersTable.addView(playerRow);

			ImageButton upButton = new ImageButton(context);
			upButton.setImageDrawable(context.getResources().getDrawable(R.drawable.add));
			upButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					upButtonListenerManager.notifyListeners();
				}
			});
			playerRow.addView(upButton);

			ImageButton downButton = new ImageButton(context);
			downButton.setImageDrawable(context.getResources().getDrawable(R.drawable.minus));
			downButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					downButtonListenerManager.notifyListeners();
				}
			});
			playerRow.addView(downButton);

			TextView playerName = new TextView(context);
			playerName.setText(player.getName());
			playerName.setTextSize(UIConstants.TEXT_NORMAL_SIZE);
			playerName.setTextColor(UIConstants.TEXT_COLOR);
			playerRow.addView(playerName);
		}
	}

	public boolean isCurrentPlayerSelected() {
		return isCurrentPlayerSelected;
	}

	public void addDoneButtonListener(final Listener listener) {
		doneButtonListenerManager.addListener(listener);
	}

	public String getCurrentPlayerId() {
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

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}

	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager.addListener(listener);
	}
}
