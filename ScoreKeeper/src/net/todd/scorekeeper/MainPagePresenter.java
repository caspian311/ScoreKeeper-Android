package net.todd.scorekeeper;


public class MainPagePresenter {
	public static void create(final MainPageView mainView, final MainPageModel mainModel) {
		mainView.addQuitButtonListener(new IListener() {
			@Override
			public void handle() {
				mainModel.quitApplication();
			}
		});
		
		mainView.addManagePlayersButtonListener(new IListener() {
			@Override
			public void handle() {
				mainModel.goToManagePlayerPage();
			}
		});
		
		mainView.addStartButtonListener(new IListener() {
			@Override
			public void handle() {
				mainModel.goToStartGamePage();
			}
		});
	}
}
