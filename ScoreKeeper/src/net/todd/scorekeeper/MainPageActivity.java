package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class MainPageActivity extends Activity {
	private MainPageView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CurrentGameStore currentStateStore = new CurrentGameStore(this);
		PageNavigator pageNavigator = new PageNavigator(this);
		
		new GameRestorer(currentStateStore, pageNavigator).restoreGameInProgress();

		mainView = new MainPageView(this);
		MainPageModel mainModel = new MainPageModel(this, currentStateStore, pageNavigator);
		MainPagePresenter.create(mainView, mainModel);

		setContentView(mainView.getView());
	}

	@Override
	public void onBackPressed() {
		mainView.backPressed();
	}
}