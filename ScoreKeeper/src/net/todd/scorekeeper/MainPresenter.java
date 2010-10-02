package net.todd.scorekeeper;

import android.view.View;
import android.view.View.OnClickListener;

public class MainPresenter {
	public static void create(final MainView mainView, final MainModel mainModel) {
		mainView.addQuitButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainModel.quitApplication();
			}
		});
	}
}
