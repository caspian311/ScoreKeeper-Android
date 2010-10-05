package net.todd.scorekeeper;

import java.util.List;

public class ManagePlayersModel {
	private final PlayerStore playerStore;

	private IListener playerChangedListener;

	public ManagePlayersModel(PlayerStore playerStore) {
		this.playerStore = playerStore;
	}
	
	public void addPlayer(String playerName) {
		playerStore.addPlayer(new Player(playerStore.nextPlayerId(), playerName));
		
		playerChangedListener.handle();
	}

	public void addPlayerChangedListener(IListener listener) {
		this.playerChangedListener = listener;
	}

	public void removePlayer(int playerToRemove) {
		playerStore.removePlayer(playerStore.getPlayerById(playerToRemove));
		playerChangedListener.handle();
	}

	public List<Player> getPlayers() {
		return playerStore.getAllPlayers();
	}
}
