package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

public class PickPlayersModel {
	private final PlayerStore playerStore;
	private final ArrayList<Player> selectedPlayers = new ArrayList<Player>();
	private final PickPlayersActivity context;
	private final IntentFactory intentFactory;
	
	public PickPlayersModel(PickPlayersActivity pickPlayersActivity, PlayerStore playerStore, IntentFactory intentFactory) {
		this.context = pickPlayersActivity;
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
	
	public void goToMainPage() {
		Intent intent = intentFactory.createIntent(context, MainPageActivity.class);
		intent.putExtra("selectedPlayers", selectedPlayers);
		context.startActivity(intent);
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
