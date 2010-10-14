package net.todd.scorekeeper;

import java.util.List;

import android.app.Activity;

public class ManagePlayersModel {
	private final PlayerStore playerStore;

	private final ListenerManager playerChangedListenerManager = new ListenerManager();

	private final Activity context;

	public ManagePlayersModel(Activity context, PlayerStore playerStore) {
		this.context = context;
		this.playerStore = playerStore;
	}
	
	public void addPlayer(String playerName) {
		if (playerName != null && playerName.length() > 0) {
			playerStore.addPlayer(new Player(playerStore.nextPlayerId(), playerName));
			playerChangedListenerManager.notifyListeners();
		}
	}

	public void addPlayerChangedListener(Listener listener) {
		playerChangedListenerManager.addListener(listener);
	}

	public void removePlayer(int playerToRemove) {
		playerStore.removePlayer(playerToRemove);
		playerChangedListenerManager.notifyListeners();
	}

	public List<Player> getPlayers() {
		return playerStore.getAllPlayers();
	}

	public void finish() {
		context.finish();
	}
}
