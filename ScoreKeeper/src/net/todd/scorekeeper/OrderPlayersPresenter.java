package net.todd.scorekeeper;

public class OrderPlayersPresenter {
	public static void create(final OrderPlayersView view, final OrderPlayersModel model) {
		view.setPlayers(model.getSelectedPlayers());

		view.addBackButtonListener(new Listener() {
			@Override
			public void handle() {
				model.cancel();
			}
		});

		view.addStartGameButtonListener(new Listener() {
			@Override
			public void handle() {
				model.startGame();
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
				view.setPlayers(model.getSelectedPlayers());
			}
		});
	}
}
