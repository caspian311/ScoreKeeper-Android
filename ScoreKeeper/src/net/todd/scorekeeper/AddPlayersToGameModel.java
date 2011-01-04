package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;
import net.todd.scorekeeper.data.ScoreBoardEntry;

public class AddPlayersToGameModel {
	private final PageNavigator pageNavigator;

	private final ArrayList<Player> allPlayers = new ArrayList<Player>();

	private final CurrentGame currentGame;

	public AddPlayersToGameModel(PlayerStore playerStore, PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;

		allPlayers.addAll(playerStore.getAllPlayers());

		currentGame = (CurrentGame) pageNavigator.getExtra("currentGame");
		for (ScoreBoardEntry scoreBoardEntry : currentGame.getScoreBoard().getEntries()) {
			String playerId = scoreBoardEntry.getPlayer().getId();
			for (Player player : allPlayers) {
				if (player.getId().equals(playerId)) {
					player.setSelected(true);
					break;
				}
			}
		}
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public void selectionChanged(String currentPlayer, boolean isCurrentPlayerSelected) {
		getPlayerById(currentPlayer).setSelected(isCurrentPlayerSelected);
	}

	public boolean atLeastTwoPlayersSelected() {
		int count = 0;
		for (Player player : allPlayers) {
			if (player.isSelected()) {
				count++;
			}
		}
		return count >= 2;
	}

	public void done() {
		ArrayList<Player> selectedPlayers = new ArrayList<Player>();
		for (Player player : allPlayers) {
			if (player.isSelected()) {
				selectedPlayers.add(player);
			}
		}
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(selectedPlayers);
		currentGame.setScoreBoard(scoreBoard);

		Map<String, Serializable> extras = new HashMap<String, Serializable>();
		extras.put("currentGame", currentGame);
		pageNavigator.navigateToActivityAndFinish(SetupGameActivity.class, extras);
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
}
