package net.todd.scorekeeper;

public class SetupGamePresenter {
	public static void create(final SetupGameView view, final SetupGameModel model) {
		view.setAllPlayers(model.getAllPlayers());

		view.addSelectedPlayersChangedListener(new Listener() {
			@Override
			public void handle() {
				model.selectionChanged(view.getCurrentPlayerId(), view.isCurrentPlayerSelected());
			}
		});

		view.addCancelButtonListener(new Listener() {
			@Override
			public void handle() {
				model.cancel();
			}
		});

		view.addStartGameButtonListener(new Listener() {
			@Override
			public void handle() {
				if (model.atLeastTwoPlayersSelected()) {
					model.startGame();
				} else {
					view.popupErrorMessage();
				}
			}
		});
		
		view.addUpButtonListener(new Listener() {
			@Override
			public void handle() {
				model.movePlayerUp(view.getCurrentPlayerId());
			}
		});
		
		view.addDownButtonListener(new Listener() {
			@Override
			public void handle() {
				model.movePlayerDown(view.getCurrentPlayerId());
			}
		});
		
		model.addPlayersOrderChangedListener(new Listener() {
			@Override
			public void handle() {
				view.setAllPlayers(model.getAllPlayers());
			}
		});
	}
}
