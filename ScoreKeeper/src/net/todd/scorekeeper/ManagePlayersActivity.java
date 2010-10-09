package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class ManagePlayersActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ManagePlayersView view = new ManagePlayersView(this);
		ManagePlayersModel model = new ManagePlayersModel(this, new PlayerStore(this));
		ManagePlayersPresenter.create(view, model);
		
		setContentView(view.getView());
	}
}