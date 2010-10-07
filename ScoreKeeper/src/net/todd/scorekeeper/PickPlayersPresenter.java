package net.todd.scorekeeper;

public class PickPlayersPresenter {
	public static void create(final PickPlayersView view, final PickPlayersModel model) {
		view.setAllPlayers(model.getAllPlayers());

		view.setSelectedPlayersChangedListener(new IListener() {
			@Override
			public void handle() {
				model.selectionChanged(view.getCurrentPlayer(), view.isCurrentPlayerSelected());
			}
		});

		view.addCancelButtonListener(new IListener() {
			@Override
			public void handle() {
				model.goToMainPage();
			}
		});

		view.addNextButtonListener(new IListener() {
			@Override
			public void handle() {
				model.goToOrderPlayerPage();
			}
		});
	}
}
