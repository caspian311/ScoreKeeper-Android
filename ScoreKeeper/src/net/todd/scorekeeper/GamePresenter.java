package net.todd.scorekeeper;

public class GamePresenter {
	public static void create(final GameView view, final GameModel model) {
		view.setCurrentPlayer(model.getNextPlayer(), model.getCurrentPlayersScore());
		view.setScoreBoard(model.getScoreBoard());

		view.addNextPlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				model.setScoreForCurrentPlayer(view.getScore());
				view.clearScore();
				view.closeSoftKeyboard();
				view.setCurrentPlayer(model.getNextPlayer(), model.getCurrentPlayersScore());
				view.setScoreBoard(model.getScoreBoard());
			}
		});

		view.addPreviousPlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				view.clearScore();
				view.closeSoftKeyboard();
				view.setCurrentPlayer(model.getPreviousPlayer(), model.getCurrentPlayersScore());
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
	}
}
