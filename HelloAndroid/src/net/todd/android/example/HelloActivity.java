package net.todd.android.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		String name = getIntent().getExtras().getString("name");
		textView.setText("Hello, " + name + "!");
		setContentView(textView);
	}
}