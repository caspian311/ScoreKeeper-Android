package net.todd.scorekeeper;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class OrderPlayersView {
	private final LinearLayout mainView;
	private final TableLayout playerTable;
	private final Context context;
	private final Button startGameButton;
	private final Button backButton;
	
	private int currentPlayerId;
	private IListener upButtonListener;
	private IListener downButtonListener;

	public OrderPlayersView(Context context) {
		this.context = context;
		
		mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainView.setBackgroundColor(0xFF3399CC);
		
		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Order Players");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);
		
		LinearLayout controls = new LinearLayout(context);
		controls.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		controls.setOrientation(LinearLayout.HORIZONTAL);
		controls.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(controls);

		backButton = new Button(context);
		backButton.setText("Back");
		controls.addView(backButton);
		
		startGameButton = new Button(context);
		startGameButton.setText("Start Game");
		controls.addView(startGameButton);
		
		playerTable = new TableLayout(context);
		playerTable.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		playerTable.setColumnStretchable(0, true);
		mainView.addView(playerTable);
	}

	public View getView() {
		return mainView;
	}
	
	public void setPlayers(List<Player> players) {
		for (final Player player : players) {
			TableRow playerRow = new TableRow(context);
			playerRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			playerTable.addView(playerRow);
			
			TextView playerName = new TextView(context);
			playerName.setText(player.getName());
			playerName.setTextSize(30);
			playerName.setTextColor(0xFF000000);
			playerRow.addView(playerName);
			
			Button upButton = new Button(context);
			upButton.setText("Up");
			upButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					upButtonListener.handle();
				}
			});
			playerRow.addView(upButton);
			
			Button downButton = new Button(context);
			downButton.setText("Down");
			downButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPlayerId = player.getId();
					downButtonListener.handle();
				}
			});
			playerRow.addView(downButton);
		}
	}
	
	public void addStartGameButtonListener(final IListener listener) {
		startGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}
	
	public void addBackButtonListener(final IListener listener) {
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void addUpButtonListener(IListener listener) {
		this.upButtonListener = listener;
	}
	
	public void addDownButtonListener(IListener listener) {
		this.downButtonListener = listener;
	}

	public void clearPlayersTable() {
		playerTable.removeAllViews();
	}
}
