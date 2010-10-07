package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

public class PickPlayersModel {
	private final PlayerStore playerStore;
	private final List<Player> selectedPlayers = new ArrayList<Player>();
	private final PickPlayersActivity pickPlayersActivity;
	
	public PickPlayersModel(PickPlayersActivity pickPlayersActivity, PlayerStore playerStore) {
		this.pickPlayersActivity = pickPlayersActivity;
		this.playerStore = playerStore;
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
	
	public void goToNextPage() {
		Intent intent = new Intent(pickPlayersActivity, OrderSelectedPlayersActivity.class);
		pickPlayersActivity.startActivity(intent);
	}

	public void goToMainPage() {
		Intent intent = new Intent(pickPlayersActivity, MainPageActivity.class);
		pickPlayersActivity.startActivity(intent);
	}

	public void goToOrderPlayerPage() {
		Intent intent = new Intent(pickPlayersActivity, OrderPlayersActivity.class);
		intent.putExtra("playerIds", (ArrayList<Player>)selectedPlayers);
		pickPlayersActivity.startActivity(intent);
	}
}
