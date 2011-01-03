package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class AddPlayersToGameActivity extends Activity {
	private AddPlayersToGameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new AddPlayersToGameView(this);
		AddPlayersToGameModel model = new AddPlayersToGameModel(new PlayerStore(this),
				new PageNavigator(this));
		AddPlayersToGamePresenter.create(view, model);

		setContentView(view.getView());
	}

	@Override
	public void onBackPressed() {
		view.backPressed();
	}
}
