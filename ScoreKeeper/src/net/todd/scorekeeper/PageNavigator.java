package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;

public class PageNavigator {
	private final IntentFactory intentFactory;
	private final Activity homeActivity;

	public PageNavigator(Activity homeActivity) {
		this(new IntentFactory(), homeActivity);
	}

	public PageNavigator(IntentFactory intentFactory, Activity homeActivity) {
		this.intentFactory = intentFactory;
		this.homeActivity = homeActivity;
	}

	public void navigateToActivityAndFinish(Class<? extends Activity> targetActivity) {
		navigateToActivityAndFinish(targetActivity, Collections.<String, Serializable> emptyMap());
	}

	public Serializable getExtra(String key) {
		return Serializable.class.cast(homeActivity.getIntent().getExtras().get(key));
	}

	public void navigateToActivityAndFinish(Class<? extends Activity> targetActivity,
			Map<String, Serializable> extras) {
		Intent intent = intentFactory.createIntent(homeActivity, targetActivity);
		for (String key : extras.keySet()) {
			intent.putExtra(key, extras.get(key));
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		homeActivity.startActivity(intent);
		homeActivity.finish();
	}

	public void navigateToActivityButDontFinish(Class<? extends Activity> targetActivity,
			Map<String, Serializable> extras) {
		Intent intent = intentFactory.createIntent(homeActivity, targetActivity);
		for (String key : extras.keySet()) {
			intent.putExtra(key, extras.get(key));
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		homeActivity.startActivity(intent);
	}
}
