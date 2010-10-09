package net.todd.scorekeeper;

import java.util.List;

import android.content.Context;
import android.content.Intent;

public class ManagePlayersModel {
	private final PlayerStore playerStore;

	private Listener playerChangedListener;

	private final Context context;

	public ManagePlayersModel(Context context, PlayerStore playerStore) {
		this.context = context;
		this.playerStore = playerStore;
	}
	
	public void addPlayer(String playerName) {
		playerStore.addPlayer(new Player(playerStore.nextPlayerId(), playerName));
		
		playerChangedListener.handle();
	}

	public void addPlayerChangedListener(Listener listener) {
		this.playerChangedListener = listener;
	}

	public void removePlayer(int playerToRemove) {
		playerStore.removePlayer(playerStore.getPlayerById(playerToRemove));
		playerChangedListener.handle();
	}

	public List<Player> getPlayers() {
		return playerStore.getAllPlayers();
	}

	public void goToMainPage() {
		Intent intent = new Intent(context, MainPageActivity.class);
		context.startActivity(intent);
	}
}
