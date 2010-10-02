package net.todd.scorekeeper;

import android.content.Intent;

public class MainModel {
	private final MainActivity mainActivity;

	public MainModel(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	public void quitApplication() {
		mainActivity.finish();
	}

	public void goToAddPlayerPage() {
		Intent intent = new Intent(mainActivity, AddUsersActivity.class);
		mainActivity.startActivity(intent);
	}
}
