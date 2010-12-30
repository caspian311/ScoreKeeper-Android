package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;
import net.todd.scorekeeper.data.XmlPersistor;
import android.content.Context;

public class CurrentGameStore {
	private final Persistor<CurrentGame> persistor;

	public CurrentGameStore(Context context) {
		persistor = XmlPersistor.create(CurrentGame.class, context);
	}

	public void clearState() {
		persistor.persist(new ArrayList<CurrentGame>());
	}

	public void saveState(ScoreBoard scoreBoard, Player currentPlayer) {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(scoreBoard);
		currentGame.setCurrentPlayer(currentPlayer);
		persistor.persist(new ArrayList<CurrentGame>(Arrays.<CurrentGame> asList(currentGame)));
	}

	public CurrentGame getCurrentGame() {
		List<CurrentGame> currentGames = persistor.load();
		return currentGames.isEmpty() ? null : currentGames.get(0);
	}
}
