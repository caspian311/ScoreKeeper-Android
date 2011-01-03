package net.todd.scorekeeper;

import java.util.List;

import net.todd.scorekeeper.data.Player;

public class ManagePlayersModel {
	private final PlayerStore playerStore;
	private final PageNavigator pageNavigator;

	private final ListenerManager playerChangedListenerManager = new ListenerManager();

	public ManagePlayersModel(PlayerStore playerStore, PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
		this.playerStore = playerStore;
	}

	public void addPlayer(String playerName) {
		if (playerName != null && playerName.length() > 0) {
			Player player = new Player();
			player.setId(playerStore.nextPlayerId());
			player.setName(playerName);
			playerStore.addPlayer(player);
			playerChangedListenerManager.notifyListeners();
		}
	}

	public void addPlayerChangedListener(Listener listener) {
		playerChangedListenerManager.addListener(listener);
	}

	public void removePlayer(String playerToRemove) {
		playerStore.removePlayer(playerToRemove);
		playerChangedListenerManager.notifyListeners();
	}

	public List<Player> getPlayers() {
		return playerStore.getAllPlayers();
	}

	public void finish() {
		pageNavigator.navigateToActivityAndFinish(MainPageActivity.class);
	}
}
