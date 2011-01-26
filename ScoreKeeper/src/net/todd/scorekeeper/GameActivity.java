package net.todd.scorekeeper;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;

public class GameActivity extends Activity {
	private GameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		disableKeyGuard();

		view = new GameView(this);
		GameModel model = new GameModel(new GameStore(this), new PageNavigator(this));
		GamePresenter.create(view, model);
		GameWatcher.create(model, new CurrentGameStore(this));

		setContentView(view.getView());
	}

	private void disableKeyGuard() {
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(getClass().getName());
		if (keyguardManager.inKeyguardRestrictedInputMode()) {
			lock.disableKeyguard();
		}
	}

	@Override
	public void onBackPressed() {
		view.onBackPressed();
	}
}
