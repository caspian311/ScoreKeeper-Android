package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupGameModel {
	private final PageNavigator pageNavigator;

	private final ListenerManager playersOrderChangedListenerManager = new ListenerManager();
	private final ArrayList<Player> allPlayers = new ArrayList<Player>();

	public SetupGameModel(PlayerStore playerStore, PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;

		allPlayers.addAll(playerStore.getAllPlayers());
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public void selectionChanged(String currentPlayer, boolean isCurrentPlayerSelected) {
		getPlayerById(currentPlayer).setSelected(isCurrentPlayerSelected);
	}

	public void cancel() {
		pageNavigator.navigateToActivity(MainPageActivity.class);
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
		CurrentGame currentGame = new CurrentGame(new ScoreBoard(selectedPlayers), selectedPlayers.get(0));

		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentGame);
		pageNavigator.navigateToActivity(GameActivity.class, extras);
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
