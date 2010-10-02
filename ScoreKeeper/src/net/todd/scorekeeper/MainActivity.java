package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			MainView mainView = new MainView(this);
			MainModel mainModel = new MainModel(this);
			MainPresenter.create(mainView, mainModel);
			
			setContentView(mainView.getView());
		}
}