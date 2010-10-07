package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

public class OrderPlayersModel {
	private final OrderPlayersActivity activity;

	private final ArrayList<Player> selectedPlayers;

	@SuppressWarnings("unchecked")
	public OrderPlayersModel(OrderPlayersActivity activity) {
		this.activity = activity;

		selectedPlayers = (ArrayList<Player>) activity.getIntent().getExtras().get("playerIds");
	}

	public List<Player> getSelectedPlayers() {
		return selectedPlayers;
	}

	public void goToPickPlayersPage() {
		Intent intent = new Intent(activity, PickPlayersActivity.class);
		activity.startActivity(intent);
	}

	public void startGame() {
		Intent intent = new Intent(activity, StartGameActivity.class);
		activity.startActivity(intent);
	}

	public void movePlayerUp(int currentPlayerId) {
		Player player = getPlayerById(currentPlayerId);
		int currentIndex = selectedPlayers.indexOf(player);
		if (currentIndex != 0) {
			selectedPlayers.remove(player);
			selectedPlayers.add(currentIndex - 1, player);
		}
	}

	private Player getPlayerById(int currentPlayerId) {
		Player targetPlayer = null;
		for (Player player : selectedPlayers) {
			if (player.getId() == currentPlayerId) {
				targetPlayer = player;
			}
		}
		return targetPlayer;
	}

	public void movePlayerDown(int currentPlayerId) {
		Player player = getPlayerById(currentPlayerId);
		int currentIndex = selectedPlayers.indexOf(player);
		if (currentIndex != selectedPlayers.size() - 1) {
			selectedPlayers.remove(player);
			selectedPlayers.add(currentIndex + 1, player);
		}
	}
}
