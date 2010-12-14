package net.todd.scorekeeper;


public class MainPageModel {
	private final PageNavigator pageNavigator;

	public MainPageModel(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
	}
	
	public void quitApplication() {
		System.exit(0);
	}

	public void goToManagePlayerPage() {
		pageNavigator.navigateToActivity(ManagePlayersActivity.class);
	}
	
	public void goToStartGamePage() {
		pageNavigator.navigateToActivity(SetupGameActivity.class);
	}

	public void goToHistoryPage() {
		pageNavigator.navigateToActivity(HistoryActivity.class);
	}
}
