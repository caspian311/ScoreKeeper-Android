package net.todd.scorekeeper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameView {
	private final LinearLayout mainView;
	private final TextView playerName;

	public GameView(Context context) {
		mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mainView.setBackgroundColor(0xFF3399CC);
		
		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Start Game");
		title.setTextSize(30);
		title.setTextColor(0xFF000000);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);
		
		playerName = new TextView(context);
		playerName.setTextColor(0xFF000000);
		playerName.setTextSize(45);
		mainView.addView(playerName);
	}

	public void setCurrentPlayer(Player player) {
		playerName.setText(player.getName());
	}
	
	public View getView() {
		return mainView;
	}
}
