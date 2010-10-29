package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

public class SetupGameModel {
	private final Activity activity;
	private final IntentFactory intentFactory;

	private final ListenerManager playersOrderChangedListenerManager = new ListenerManager();
	private final ArrayList<Player> allPlayers = new ArrayList<Player>();

	public SetupGameModel(Activity activity, PlayerStore playerStore, IntentFactory intentFactory) {
		this.activity = activity;
		this.intentFactory = intentFactory;

		allPlayers.addAll(playerStore.getAllPlayers());
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public void selectionChanged(String currentPlayer, boolean isCurrentPlayerSelected) {
		getPlayerById(currentPlayer).setSelected(isCurrentPlayerSelected);
	}

	public void cancel() {
		activity.finish();
	}

	public boolean atLeastTwoPlayersSelected() {
		int count = 0;
		for (Player player : allPlayers) {
			if (player.istSelected()) {
				count++;
			}
		}
		return count >= 2;
	}

	public void startGame() {
		ArrayList<Player> selectedPlayers = new ArrayList<Player>();
		for (Player player : allPlayers) {
			if (player.istSelected()) {
				selectedPlayers.add(player);
			}
		}
		Intent intent = intentFactory.createIntent(activity, GameActivity.class);
		intent.putExtra("selectedPlayers", selectedPlayers);
		activity.startActivity(intent);
	}

	public void movePlayerUp(String currentPlayerId) {
		Player player = getPlayerById(currentPlayerId);
		int currentIndex = allPlayers.indexOf(player);
		if (currentIndex != 0) {
			allPlayers.remove(player);
			allPlayers.add(currentIndex - 1, player);
		}
		playersOrderChangedListenerManager.notifyListeners();
	}

	private Player getPlayerById(String currentPlayerId) {
		Player targetPlayer = null;
		for (Player player : allPlayers) {
			if (player.getId().equals(currentPlayerId)) {
				targetPlayer = player;
			}
		}
		return targetPlayer;
	}

	public void movePlayerDown(String currentPlayerId) {
		Player player = getPlayerById(currentPlayerId);
		int currentIndex = allPlayers.indexOf(player);
		if (currentIndex != allPlayers.size() - 1) {
			allPlayers.remove(player);
			allPlayers.add(currentIndex + 1, player);
		}
		playersOrderChangedListenerManager.notifyListeners();
	}

	public void addPlayersOrderChangedListener(Listener listener) {
		playersOrderChangedListenerManager.addListener(listener);
	}
}
