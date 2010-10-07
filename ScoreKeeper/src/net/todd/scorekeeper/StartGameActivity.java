package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class StartGameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GameView view = new GameView(this);
		GameModel model = new GameModel();
		GamePresenter.create(view, model);
		
		setContentView(view.getView());
	}
}
