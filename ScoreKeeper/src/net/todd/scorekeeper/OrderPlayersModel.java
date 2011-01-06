package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoardEntry;

public class OrderPlayersModel {
	private final PageNavigator pageNavigator;

	private final ListenerManager playersOrderChangedListenerManager = new ListenerManager();
	private final ArrayList<Player> allPlayers = new ArrayList<Player>();

	private final CurrentGame currentGame;

	public OrderPlayersModel(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;

		currentGame = (CurrentGame) pageNavigator.getExtra("currentGame");
		for (ScoreBoardEntry scoreBoardEntry : currentGame.getScoreBoard().getEntries()) {
			allPlayers.add(scoreBoardEntry.getPlayer());
		}
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public void done() {
		currentGame.getScoreBoard().setPlayers(allPlayers);

		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentGame);
		pageNavigator.navigateToActivityAndFinish(SetupGameActivity.class, extras);
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
