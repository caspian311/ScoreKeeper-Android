package net.todd.scorekeeper;

public class MainModel {
	private final MainActivity mainActivity;

	public MainModel(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	public void quitApplication() {
		mainActivity.finish();
	}
}
