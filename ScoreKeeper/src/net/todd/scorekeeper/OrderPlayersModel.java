package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

public class OrderPlayersModel {
	private final ListenerManager playersOrderChangedListenerManager = new ListenerManager();

	private final Activity activity;
	private final IntentFactory intentFactory;
	private final ArrayList<Player> selectedPlayers;

	@SuppressWarnings("unchecked")
	public OrderPlayersModel(Activity activity, IntentFactory intentFactory) {
		this.activity = activity;
		this.intentFactory = intentFactory;

		selectedPlayers = (ArrayList<Player>) activity.getIntent().getSerializableExtra(
				"selectedPlayers");
	}

	public List<Player> getSelectedPlayers() {
		return selectedPlayers;
	}

	public void cancel() {
		activity.finish();
	}

	public void startGame() {
		Intent intent = intentFactory.createIntent(activity, GameActivity.class);
		intent.putExtra("selectedPlayers", selectedPlayers);
		activity.startActivity(intent);
	}

	public void movePlayerUp(int currentPlayerId) {
		Player player = getPlayerById(currentPlayerId);
		int currentIndex = selectedPlayers.indexOf(player);
		if (currentIndex != 0) {
			selectedPlayers.remove(player);
			selectedPlayers.add(currentIndex - 1, player);
		}
		playersOrderChangedListenerManager.notifyListeners();
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
		playersOrderChangedListenerManager.notifyListeners();
	}

	public void addPlayersOrderChangedListener(Listener listener) {
		playersOrderChangedListenerManager.addListener(listener);
	}
}
