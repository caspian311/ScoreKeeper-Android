package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class PickPlayersActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PickPlayersView view = new PickPlayersView(this);
		PickPlayersModel model = new PickPlayersModel(this, new PlayerStore());
		PickPlayersPresenter.create(view, model);
		
		setContentView(view.getView());
	}
}
