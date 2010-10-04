package net.todd.scorekeeper;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddPlayerView {
	private final Context context;
	
	private final TableLayout mainView;
	private final EditText playerNameText;
	private final Button addPlayerButton;

	private int playerToRemove;
	private IListener playerRemovedListener;
	
	public AddPlayerView(Context context) {
		this.context = context;

		mainView = new TableLayout(context);
		mainView.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
		mainView.setBackgroundColor(0xFF3399CC);
		mainView.setColumnStretchable(0, true);

		TableRow controlsRow = new TableRow(context);
		controlsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		mainView.addView(controlsRow);
		
		playerNameText = new EditText(context);
		controlsRow.addView(playerNameText);
		
		addPlayerButton = new Button(context);
		addPlayerButton.setText("Add Player");
		controlsRow.addView(addPlayerButton);
	}

	public View getView() {
		return mainView;
	}
	
	public String getPlayerNameText() {
		return playerNameText.getText().toString();
	}
	
	public void clearPlayerNameText() {
		playerNameText.setText("");
	}
	
	public void addAddPlayerButtonListener(final IListener listener) {
		addPlayerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.handle();
			}
		});
	}
	
	private void addPlayer(final int playerId, String playerName) {
		TableRow playerRow = new TableRow(context);
		playerRow.setId(playerId);
		playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		mainView.addView(playerRow);

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
				playerRemovedListener.handle();
			}
		});
		playerRow.addView(removePlayerButton);
	}
	
	public void addPlayerRemoveListener(IListener listener) {
		playerRemovedListener = listener;
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
		while (mainView.getChildCount() > 1) {
			mainView.removeViewAt(mainView.getChildCount() - 1);		
		}
	}
}
