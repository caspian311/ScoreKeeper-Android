package net.todd.scorekeeper;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class BackgroundUtil {
	public static void setBackground(View view) {
		GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
				new int[] {UIConstants.BACKGROUND_COLOR1 , UIConstants.BACKGROUND_COLOR2 });
		view.setBackgroundDrawable(drawable);
	}
}
