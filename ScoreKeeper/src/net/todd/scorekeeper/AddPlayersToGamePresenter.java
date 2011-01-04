package net.todd.scorekeeper;

public class AddPlayersToGamePresenter {
	public static void create(final AddPlayersToGameView view, final AddPlayersToGameModel model) {
		view.setAllPlayers(model.getAllPlayers());

		view.addSelectedPlayersChangedListener(new Listener() {
			@Override
			public void handle() {
				model.selectionChanged(view.getCurrentPlayerId(), view.isCurrentPlayerSelected());
			}
		});

		view.addDoneButtonListener(new Listener() {
			@Override
			public void handle() {
				if (model.atLeastTwoPlayersSelected()) {
					model.done();
				} else {
					view.popupErrorMessage();
				}
			}
		});

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				model.done();
			}
		});
	}
}
