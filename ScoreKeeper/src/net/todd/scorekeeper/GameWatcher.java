package net.todd.scorekeeper;

public class GameWatcher {
	public static void create(final GameModel model, final CurrentGameStore currentGameStore) {
		// might need to make this a player changed listener instead
		model.addScoreChangedListener(new Listener() {
			@Override
			public void handle() {
				currentGameStore.saveState(model.getScoreBoard(), model.getCurrentPlayer());
			}
		});
		
		Listener gameEndedListener = new Listener() {
			@Override
			public void handle() {
				currentGameStore.clearState();
			}
		};
		
		model.addGameOverListener(gameEndedListener);
		model.addCancelGameListener(gameEndedListener);
		
		currentGameStore.saveState(model.getScoreBoard(), model.getCurrentPlayer());
	}
}
