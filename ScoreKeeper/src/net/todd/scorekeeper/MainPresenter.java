package net.todd.scorekeeper;


public class MainPresenter {
	public static void create(final MainView mainView, final MainModel mainModel) {
		mainView.addQuitButtonListener(new IListener() {
			@Override
			public void handle() {
				mainModel.quitApplication();
			}
		});
		
		mainView.addPlayersButtonListener(new IListener() {
			@Override
			public void handle() {
				mainModel.goToAddPlayerPage();
			}
		});
	}
}
