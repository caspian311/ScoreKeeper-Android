package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class PlayerStore {
	private List<Player> players;
	private final Persistor<Player> persistor;
	
	public PlayerStore(Context context) {
		this(Persistor.create(Player.class, context));
	}
	
	PlayerStore(Persistor<Player> persistor) {
		this.persistor = persistor;
	}

	public List<Player> getAllPlayers() {
		load();
		
		return players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
		
		persist();
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		
		persist();
	}

	private void load() {
		players = persistor.load();
	}
	
	private void persist() {
		persistor.persist(players);
	}
	
	public int nextPlayerId() {
		return persistor.nextId();
	}

	public Player getPlayerById(int playerId) {
		Player targetPlayer = null;
		for (Player player : getAllPlayers()) {
			if (playerId == player.getId()) {
				targetPlayer = player;
				break;
			}
		}
		return targetPlayer;
	}

	public List<Player> getPlayersById(ArrayList<Integer> playerIds) {
		List<Player> selectedPlayers = new ArrayList<Player>();
		for (Integer playerId : playerIds) {
			for (Player player : getAllPlayers()) {
				if (playerId.equals(player.getId())) {
					selectedPlayers.add(player);
				}
			}
		}
		return selectedPlayers;
	}
}
