package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

public class PlayerStore {
	private static final List<Player> players = new ArrayList<Player>();
	private static int playerId;
	
	public List<Player> getAllPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public int nextPlayerId() {
		playerId++;
		return playerId;
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
}
