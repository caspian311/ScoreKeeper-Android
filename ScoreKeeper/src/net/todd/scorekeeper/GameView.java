package net.todd.scorekeeper;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class GameView {
	private final LinearLayout mainView;

	public GameView(Context context) {
		mainView = new LinearLayout(context);
	}

	public View getView() {
		return mainView;
	}
}
