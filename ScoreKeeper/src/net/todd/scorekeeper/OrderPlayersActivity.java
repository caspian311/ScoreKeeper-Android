package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class OrderPlayersActivity extends Activity {
	private OrderPlayersView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new OrderPlayersView(this);
		OrderPlayersModel model = new OrderPlayersModel(new PlayerStore(this), new PageNavigator(
				this));
		OrderPlayersPresenter.create(view, model);

		setContentView(view.getView());
	}

	@Override
	public void onBackPressed() {
		view.backPressed();
	}
}
