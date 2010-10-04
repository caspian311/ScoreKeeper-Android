package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class AddUsersActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AddPlayerView view = new AddPlayerView(this);
		AddPlayerModel model = new AddPlayerModel();
		AddPlayerPresenter.create(view, model);
		
		setContentView(view.getView());
	}
}