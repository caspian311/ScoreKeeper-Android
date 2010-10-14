package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

public class PickPlayersModel {
	private final PlayerStore playerStore;
	private final ArrayList<Player> selectedPlayers = new ArrayList<Player>();
	private final Activity context;
	private final IntentFactory intentFactory;
	
	public PickPlayersModel(Activity context, PlayerStore playerStore, IntentFactory intentFactory) {
		this.context = context;
		this.playerStore = playerStore;
		this.intentFactory = intentFactory;
	}

	public List<Player> getAllPlayers() {
		return playerStore.getAllPlayers();
	}

	public void selectionChanged(int currentPlayer, boolean isCurrentPlayerSelected) {
		if (isCurrentPlayerSelected) {
			selectedPlayers.add(playerStore.getPlayerById(currentPlayer));
		} else {
			selectedPlayers.remove(playerStore.getPlayerById(currentPlayer));
		}
	}
	
	public void cancel() {
		context.finish();
	}

	public void goToOrderPlayerPage() {
		Intent intent = intentFactory.createIntent(context, OrderPlayersActivity.class);
		intent.putExtra("selectedPlayers", selectedPlayers);
		context.startActivity(intent);
	}

	public boolean atLeastTwoPlayersSelected() {
		return selectedPlayers.size() >= 2;
	}
}
