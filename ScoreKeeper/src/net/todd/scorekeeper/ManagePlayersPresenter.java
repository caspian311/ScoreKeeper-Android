package net.todd.scorekeeper;

public class ManagePlayersPresenter {
	public static void create(final ManagePlayersView view, final ManagePlayersModel model) {
		view.addAddPlayerButtonListener(new IListener() {
			@Override
			public void handle() {
				model.addPlayer(view.getPlayerNameText());
				view.clearPlayerNameText();
			}
		});

		view.addPlayerRemoveListener(new IListener() {
			@Override
			public void handle() {
				model.removePlayer(view.getPlayerToRemove());
			}
		});
		
		model.addPlayerChangedListener(new IListener() {
			@Override
			public void handle() {
				view.setPlayers(model.getPlayers());
			}
		});
		
		view.setPlayers(model.getPlayers());
	}
}
