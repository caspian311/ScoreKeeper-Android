package net.todd.scorekeeper;

public class ManagePlayersPresenter {
	public static void create(final ManagePlayersView view, final ManagePlayersModel model) {
		view.addAddPlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				model.addPlayer(view.getPlayerNameText());
				view.clearPlayerNameText();
			}
		});

		view.addRemovePlayerButtonListener(new Listener() {
			@Override
			public void handle() {
				model.removePlayer(view.getPlayerToRemove());
			}
		});

		model.addPlayerChangedListener(new Listener() {
			@Override
			public void handle() {
				view.setPlayers(model.getPlayers());
			}
		});

		view.addDoneButtonListener(new Listener() {
			@Override
			public void handle() {
				model.finish();
			}
		});

		view.setPlayers(model.getPlayers());
	}
}
