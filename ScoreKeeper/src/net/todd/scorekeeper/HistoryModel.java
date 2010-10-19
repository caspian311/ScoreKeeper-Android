package net.todd.scorekeeper;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

public class HistoryModel {
	private final Activity activity;
	private final GameStore gameStore;
	private final IntentFactory intentFactory;

	public HistoryModel(Activity activity, GameStore gameStore, IntentFactory intentFactory) {
		this.activity = activity;
		this.gameStore = gameStore;
		this.intentFactory = intentFactory;
	}

	public void finish() {
		Intent intent = intentFactory.createIntent(activity, MainPageActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		activity.startActivity(intent);
	}

	public List<Game> getAllGames() {
		List<Game> allGames = gameStore.getAllGames();
		Collections.sort(allGames);
		return allGames;
	}
}
