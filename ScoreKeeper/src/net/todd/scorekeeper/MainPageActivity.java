package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class MainPageActivity extends Activity {
	private MainPageView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new ApplicationInitializer(this).initialize();

		mainView = new MainPageView(this);
		MainPageModel mainModel = new MainPageModel(new PageNavigator(this));
		MainPagePresenter.create(mainView, mainModel);

		setContentView(mainView.getView());
	}
	
	@Override
	public void onBackPressed() {
		mainView.backPressed();
	}
}