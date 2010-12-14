package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class ManagePlayersActivity extends Activity {
	private ManagePlayersView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		view = new ManagePlayersView(this);
		ManagePlayersModel model = new ManagePlayersModel(new PlayerStore(this), new PageNavigator(this));
		ManagePlayersPresenter.create(view, model);
		
		setContentView(view.getView());
	}
	
	@Override
	public void onBackPressed() {
		view.backPressed();
	}
}