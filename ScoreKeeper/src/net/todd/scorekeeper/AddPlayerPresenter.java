package net.todd.scorekeeper;

public class AddPlayerPresenter {
	private static int playerId;

	public static void create(final AddPlayerView view, final AddPlayerModel model) {
		view.addAddPlayerButtonListener(new IListener() {
			@Override
			public void handle() {
				playerId++;

				String playerName = view.getPlayerNameText();
				model.addPlayer(playerName);
				view.clearPlayerNameText();

				view.addPlayer(playerId, playerName);
			}
		});

		view.addPlayerRemoveListener(new IListener() {
			@Override
			public void handle() {
				view.removePlayer(view.getPlayerToRemove());
			}
		});
	}
}
