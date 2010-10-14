package net.todd.scorekeeper;

import android.content.Context;
import android.content.Intent;

public class IntentFactory {
	public Intent createIntent(Context context, Class<?> clazz) {
		return new Intent(context, clazz);
	}
}
