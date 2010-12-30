package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.XmlPersistor;
import android.content.Context;

public class PlayerStore {
	private List<Player> players;
	private final Persistor<Player> persistor;

	public PlayerStore(Context context) {
		this(XmlPersistor.create(Player.class, context));
	}

	PlayerStore(Persistor<Player> persistor) {
		this.persistor = persistor;
	}

	public List<Player> getAllPlayers() {
		load();

		return players;
	}

	public void addPlayer(Player player) {
		load();
		players.add(player);
		persist();
	}

	public void removePlayer(String playerId) {
		load();
		Player playerToRemove = getPlayerById(playerId);
		players.remove(playerToRemove);
		persist();
	}

	private void load() {
		players = persistor.load();
	}

	private void persist() {
		persistor.persist(players);
	}

	public String nextPlayerId() {
		return UUID.randomUUID().toString();
	}

	public Player getPlayerById(String playerId) {
		List<Player> allPlayers = getAllPlayers();
		Player targetPlayer = null;
		for (Player player : allPlayers) {
			if (playerId.equals(player.getId())) {
				targetPlayer = player;
				break;
			}
		}
		return targetPlayer;
	}

	public List<Player> getPlayersById(List<String> playerIds) {
		List<Player> selectedPlayers = new ArrayList<Player>();
		List<Player> allPlayers = getAllPlayers();
		for (String playerId : playerIds) {
			for (Player player : allPlayers) {
				if (playerId.equals(player.getId())) {
					selectedPlayers.add(player);
					break;
				}
			}
		}
		return selectedPlayers;
	}
}
