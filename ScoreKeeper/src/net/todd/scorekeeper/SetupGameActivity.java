package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class SetupGameActivity extends Activity {
	private SetupGameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		view = new SetupGameView(this);
		SetupGameModel model = new SetupGameModel(new PlayerStore(this), new PageNavigator(this));
		SetupGamePresenter.create(view, model);
		
		setContentView(view.getView());
	}
	
	@Override
	public void onBackPressed() {
		view.backPressed();
	}
}
