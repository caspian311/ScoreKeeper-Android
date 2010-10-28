package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class HistoryActivity extends Activity {
	private HistoryView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new HistoryView(this);
		HistoryModel model = new HistoryModel(this, new GameStore(this), new IntentFactory());
		HistoryPresenter.create(view, model);

		setContentView(view.getView());
	}

	@Override
	public void onBackPressed() {
		view.backPressed();
	}
}
