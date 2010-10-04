package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

public class AddPlayerModel {
	private IListener playerChangedListener;
	private final List<Player> players = new ArrayList<Player>();
	private static int playerId;

	public void addPlayer(String playerName) {
		players.add(new Player(nextPlayerId(), playerName));
		
		playerChangedListener.handle();
	}

	private int nextPlayerId() {
		playerId++;
		return playerId;
	}

	public void addPlayerChangedListener(IListener listener) {
		this.playerChangedListener = listener;
	}

	public void removePlayer(int playerToRemove) {
		Player targetPlayer = null;
		for (Player player : players) {
			if (playerToRemove == player.getId()) {
				targetPlayer = player;
				break;
			}
		}
		players.remove(targetPlayer);
		playerChangedListener.handle();
	}

	public List<Player> getPlayers() {
		return players;
	}
}
