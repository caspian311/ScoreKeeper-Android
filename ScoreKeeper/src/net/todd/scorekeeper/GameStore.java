package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class GameStore {
	private final Persistor<Game> persistor;
	private List<Game> games;

	public GameStore(Context context) {
		persistor = Persistor.create(Game.class, context);
	}

	public List<Game> getAllGames() {
		load();
		return games;
	}

	public void addGame(Game game) {
		load();
		games.add(game);
		save();
	}

	private void save() {
		persistor.persist(games);
	}

	private void load() {
		games = persistor.load();
	}

	public void clearAllGames() {
		persistor.persist(new ArrayList<Game>());
	}

	public void deleteGame(Game selectedGame) {
		load();
		Game actualGame = findMatchingGameByDate(selectedGame);
		games.remove(actualGame);
		persistor.persist(games);
	}

	private Game findMatchingGameByDate(Game selectedGame) {
		Game target = null;
		for (Game game : games) {
			if (game.getGameOverTimestamp().equals(selectedGame.getGameOverTimestamp())) {
				target = game;
				break;
			}
		}
		return target;
	}
}
