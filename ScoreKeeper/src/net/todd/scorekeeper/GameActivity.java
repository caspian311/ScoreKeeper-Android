package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	private GameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		view = new GameView(this);
		GameModel model = new GameModel(new GameStore(this), new PageNavigator(this));
		GamePresenter.create(view, model);
		GameWatcher.create(model, new CurrentGameStore(this));
		
		setContentView(view.getView());
	}

	@Override
	public void onBackPressed() {
		view.onBackPressed();
	}
}
