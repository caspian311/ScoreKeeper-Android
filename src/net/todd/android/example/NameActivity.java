package net.todd.android.example;

import net.todd.andoid.example.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name_getter);
		final EditText nameField = (EditText) findViewById(R.id.EditText01);
		View button = findViewById(R.id.Button01);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NameActivity.this,
						HelloActivity.class);
				intent.putExtra("name", nameField.getText().toString());
				startActivity(intent);
			}
		});
	}
}
