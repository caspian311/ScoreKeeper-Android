package net.todd.scorekeeper;

public class PickPlayersPresenter {
	public static void create(final PickPlayersView view, final PickPlayersModel model) {
		view.setAllPlayers(model.getAllPlayers());

		view.addSelectedPlayersChangedListener(new Listener() {
			@Override
			public void handle() {
				model.selectionChanged(view.getCurrentPlayer(), view.isCurrentPlayerSelected());
			}
		});

		view.addCancelButtonListener(new Listener() {
			@Override
			public void handle() {
				model.cancel();
			}
		});

		view.addNextButtonListener(new Listener() {
			@Override
			public void handle() {
				if (model.atLeastTwoPlayersSelected()) {
					model.goToOrderPlayerPage();
				} else {
					view.popupErrorMessage();
				}
			}
		});
	}
}
