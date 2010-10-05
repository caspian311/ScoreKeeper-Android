package net.todd.scorekeeper;

public class PickPlayersPresenter {
	public static void create(final PickPlayersView view, final PickPlayersModel model) {
		view.setAllPlayers(model.getAllPlayers());
		
		view.setSelectedPlayersChangedListener(new IListener(){
			@Override
			public void handle() {
				model.selectionChanged(view.getCurrentPlayer(), view.isCurrentPlayerSelected());
			}
		});
	}
}
