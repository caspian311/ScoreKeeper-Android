package net.todd.scorekeeper;

public class HistoryPresenter {
	public static void create(final HistoryView view, final HistoryModel model) {
		updateView(view, model);

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				model.finish();
			}
		});

		view.addDonePressedListener(new Listener() {
			@Override
			public void handle() {
				model.finish();
			}
		});

		view.addClearButtonPressedListener(new Listener() {
			@Override
			public void handle() {
				view.confirmClearingHistory();
			}
		});

		view.addClearHistoryConfirmationListener(new Listener() {
			@Override
			public void handle() {
				model.clearHistory();
			}
		});

		view.addClearGameConfirmationListener(new Listener() {
			@Override
			public void handle() {
				model.removeGame(view.getSelectedGame());
			}
		});

		view.addClearGameButtonPressedListener(new Listener() {
			@Override
			public void handle() {
				view.confirmClearingGame();
			}
		});

		model.addHistoryChangedListener(new Listener() {
			@Override
			public void handle() {
				updateView(view, model);
			}
		});
	}

	private static void updateView(final HistoryView view, final HistoryModel model) {
		view.setHistory(model.getAllGames());
		view.setClearButtonEnabled(model.areThereGamesInHistory());
	}
}
