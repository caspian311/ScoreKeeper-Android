package net.todd.scorekeeper;

public class GamePresenter {
	public static void create(final GameView view, final GameModel model) {
		view.setCurrentPlayer(model.getCurrentPlayer());
		view.setCurrentPlayersScore(model.getCurrentPlayersScore());
		view.setScoreBoard(model.getScoreBoard());

		view.addNextPlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				model.setScoreForCurrentPlayer(view.getScore());
				model.nextPlayer();
			}
		});

		view.addPreviousPlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				model.previousPlayer();
			}
		});

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				view.popupNoBackButtonDialog();
			}
		});

		view.addCancelGameListener(new Listener() {
			@Override
			public void handle() {
				model.cancelGame();
			}
		});

		model.addScoreChangedListener(new Listener() {
			@Override
			public void handle() {
				view.clearScore();
				view.closeSoftKeyboard();
				view.setScoreBoard(model.getScoreBoard());
			}
		});

		model.addPlayerChangedListener(new Listener() {
			@Override
			public void handle() {
				view.setCurrentPlayer(model.getCurrentPlayer());
				view.setCurrentPlayersScore(model.getCurrentPlayersScore());
			}
		});

		view.addGameOverButtonListener(new Listener() {
			@Override
			public void handle() {
				view.popupGameOverConfirmation();
			}
		});

		view.addGameOverConfirmationListener(new Listener() {
			@Override
			public void handle() {
				model.gameOver();
			}
		});
	}
}
