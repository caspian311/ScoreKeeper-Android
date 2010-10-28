package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class MainPageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new ApplicationInitializer(this).initialize();

		MainPageView mainView = new MainPageView(this);
		MainPageModel mainModel = new MainPageModel(this, new IntentFactory());
		MainPagePresenter.create(mainView, mainModel);

		setContentView(mainView.getView());
	}
}