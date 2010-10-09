package net.todd.scorekeeper;

import android.app.Activity;
import android.content.Intent;

public class MainPageModel {
	private final Activity mainPageActivity;

	public MainPageModel(Activity activity) {
		this.mainPageActivity = activity;
	}
	
	public void quitApplication() {
		System.exit(0);
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
