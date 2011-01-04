package net.todd.scorekeeper;

public class OrderPlayersPresenter {
	public static void create(final OrderPlayersView view, final OrderPlayersModel model) {
		view.setAllPlayers(model.getAllPlayers());

		view.addDoneButtonListener(new Listener() {
			@Override
			public void handle() {
				model.done();
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

		view.addBackPressedListener(new Listener() {
			@Override
			public void handle() {
				model.done();
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
