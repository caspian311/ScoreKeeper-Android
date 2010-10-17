package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class SetupGameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SetupGameView view = new SetupGameView(this);
		SetupGameModel model = new SetupGameModel(this, new PlayerStore(this), new IntentFactory());
		SetupGamePresenter.create(view, model);
		
		setContentView(view.getView());
	}
}
