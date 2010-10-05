package net.todd.scorekeeper;

import android.content.Intent;

public class MainPageModel {
	private final MainPageActivity mainPageActivity;

	public MainPageModel(MainPageActivity mainActivity) {
		this.mainPageActivity = mainActivity;
	}
	
	public void quitApplication() {
		mainPageActivity.finish();
	}

	public void goToManagePlayerPage() {
		Intent intent = new Intent(mainPageActivity, ManagePlayersActivity.class);
		mainPageActivity.startActivity(intent);
	}
	
	public void goToStartGamePage() {
		Intent intent = new Intent(mainPageActivity, PickPlayersActivity.class);
		mainPageActivity.startActivity(intent);
	}
}
