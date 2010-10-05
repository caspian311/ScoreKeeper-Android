package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class MainPageActivity extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			MainPageView mainView = new MainPageView(this);
			MainPageModel mainModel = new MainPageModel(this);
			MainPagePresenter.create(mainView, mainModel);
			
			setContentView(mainView.getView());
		}
}