package net.todd.scorekeeper;

import android.app.Activity;
import android.content.Intent;

public class MainPageModel {
	private final Activity mainPageActivity;
	private final IntentFactory intentFactory;

	public MainPageModel(Activity activity, IntentFactory intentFactory) {
		this.mainPageActivity = activity;
		this.intentFactory = intentFactory;
	}
	
	public void quitApplication() {
		System.exit(0);
	}

	public void goToManagePlayerPage() {
		Intent intent = intentFactory.createIntent(mainPageActivity, ManagePlayersActivity.class);
		mainPageActivity.startActivity(intent);
	}
	
	public void goToStartGamePage() {
		Intent intent = intentFactory.createIntent(mainPageActivity, PickPlayersActivity.class);
		mainPageActivity.startActivity(intent);
	}
}
