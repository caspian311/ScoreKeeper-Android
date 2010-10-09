package net.todd.scorekeeper;


public class MainPagePresenter {
	public static void create(final MainPageView mainView, final MainPageModel mainModel) {
		mainView.addQuitButtonListener(new Listener() {
			@Override
			public void handle() {
				mainModel.quitApplication();
			}
		});
		
		mainView.addManagePlayersButtonListener(new Listener() {
			@Override
			public void handle() {
				mainModel.goToManagePlayerPage();
			}
		});
		
		mainView.addStartButtonListener(new Listener() {
			@Override
			public void handle() {
				mainModel.goToStartGamePage();
			}
		});
	}
}
