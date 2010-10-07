package net.todd.scorekeeper;

public class OrderPlayersPresenter {
	public static void create(final OrderPlayersView view, final OrderPlayersModel model) {
		view.setPlayers(model.getSelectedPlayers());

		view.addBackButtonListener(new IListener() {
			@Override
			public void handle() {
				model.goToPickPlayersPage();
			}
		});

		view.addStartGameButtonListener(new IListener() {
			@Override
			public void handle() {
				model.startGame();
			}
		});

		view.addUpButtonListener(new IListener() {
			@Override
			public void handle() {
				model.movePlayerUp(view.getCurrentPlayerId());
				view.clearPlayersTable();
				view.setPlayers(model.getSelectedPlayers());
			}
		});
		
		view.addDownButtonListener(new IListener() {
			@Override
			public void handle() {
				model.movePlayerDown(view.getCurrentPlayerId());
				view.clearPlayersTable();
				view.setPlayers(model.getSelectedPlayers());
			}
		});
	}
}
