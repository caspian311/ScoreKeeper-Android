package net.todd.scorekeeper;

public class MainPagePresenter {
	public static void create(final MainPageView view, final MainPageModel model) {
		view.addQuitButtonListener(new Listener() {
			@Override
			public void handle() {
				model.quitApplication();
			}
		});

		view.addManagePlayersButtonListener(new Listener() {
			@Override
			public void handle() {
				model.goToManagePlayerPage();
			}
		});

		view.addStartButtonListener(new Listener() {
			@Override
			public void handle() {
				model.goToStartGamePage();
			}
		});

		view.addHistoryButtonListener(new Listener() {
			@Override
			public void handle() {
				model.goToHistoryPage();
			}
		});

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				model.quitApplication();
			}
		});
		
		view.setVersion(model.getVersion());
	}
}
