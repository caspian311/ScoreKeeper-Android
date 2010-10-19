package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class GameStore {
	private final Persistor<Game> persistor;

	public GameStore(Context context) {
		persistor = Persistor.create(Game.class, context);
	}

	public List<Game> getAllGames() {
		return persistor.load();
	}

	public void addGame(Game game) {
		List<Game> games = persistor.load();
		games.add(game);
		persistor.persist(games);
	}

	public void clearAllGames() {
		persistor.persist(new ArrayList<Game>());
	}
}
