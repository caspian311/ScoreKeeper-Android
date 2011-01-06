package net.todd.scorekeeper;

public class SetupGamePresenter {
	public static void create(final SetupGameView view, final SetupGameModel model) {
		view.setOrderPlayersButtonEnabled(model.arePlayersAddedToGame());
		view.setStartGameButtonEnabled(model.isGameSetupComplete());
		view.setGameName(model.getGameName());
		view.setScoring(model.getScoring());

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				model.cancel();
			}
		});

		view.addAddPlayersButtonPressedListener(new Listener() {
			@Override
			public void handle() {
				model.goToAddPlayersScreen();
			}
		});

		view.addStartGamePressedListener(new Listener() {
			@Override
			public void handle() {
				model.startGame();
			}
		});

		view.addGameNameChangedListener(new Listener() {
			@Override
			public void handle() {
				model.setGameName(view.getGameName());
			}
		});

		view.addOrderPlayersPressedListener(new Listener() {
			@Override
			public void handle() {
				model.goToOrderPlayersScreen();
			}
		});

		view.addScoringChangedListener(new Listener() {
			@Override
			public void handle() {
				model.setScoring(view.getScoring());
			}
		});

		model.addStateChangedListener(new Listener() {
			@Override
			public void handle() {
				view.setStartGameButtonEnabled(model.isGameSetupComplete());
				view.setOrderPlayersButtonEnabled(model.arePlayersAddedToGame());
			}
		});
	}
}
