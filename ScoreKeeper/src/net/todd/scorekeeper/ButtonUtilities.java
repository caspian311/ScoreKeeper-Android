package net.todd.scorekeeper;

import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class ButtonUtilities {
	public static void setLayout(Button button) {
		button.setLayoutParams(new LayoutParams(UIConstants.BUTTON_WIDTH,
				UIConstants.BUTTON_HEIGHT));
	}
}
