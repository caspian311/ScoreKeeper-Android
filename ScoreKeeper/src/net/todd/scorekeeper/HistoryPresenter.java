package net.todd.scorekeeper;

public class HistoryPresenter {
	public static void create(final HistoryView view, final HistoryModel model) {
		view.setHistory(model.getAllGames());
		
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
	}
}
