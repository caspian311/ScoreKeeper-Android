package net.todd.scorekeeper;

import java.util.Arrays;
import java.util.List;

import android.content.Context;

public class CurrentGameStore {
	private final Persistor<CurrentGame> persistor;

	public CurrentGameStore(Context context) {
		persistor = Persistor.create(CurrentGame.class, context);
	}

	public void clearState() {
		persistor.persist(Arrays.<CurrentGame>asList());
	}

	public void saveState(ScoreBoard scoreBoard, Player currentPlayer) {
		persistor.persist(Arrays.<CurrentGame>asList(new CurrentGame(scoreBoard)));
	}

	public CurrentGame getCurrentGame() {
		List<CurrentGame> currentGames = persistor.load();
		return currentGames.isEmpty() ? null : currentGames.get(0);
	}
}
