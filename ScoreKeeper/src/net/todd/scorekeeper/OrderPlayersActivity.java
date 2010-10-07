package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class OrderPlayersActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		OrderPlayersView view = new OrderPlayersView(this);
		OrderPlayersModel model = new OrderPlayersModel(this);
		OrderPlayersPresenter.create(view, model);
		
		setContentView(view.getView());
	}
}
