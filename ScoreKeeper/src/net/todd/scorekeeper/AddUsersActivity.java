package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddUsersActivity extends Activity {
	private Button addButton;
	private EditText playerNameText;
	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		playerNameText = (EditText) findViewById(R.id.EditText01);
		final ListView listOfPlayers = (ListView) findViewById(R.id.ListView01);
		adapter = new ArrayAdapter<String>(listOfPlayers.getContext(),
				R.layout.row, R.id.TextView01);
		listOfPlayers.setAdapter(adapter);

		addButton = (Button) findViewById(R.id.Button01);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String playerName = playerNameText.getText().toString();
				adapter.add(playerName);
				playerNameText.getText().clear();
			}
		});
	}
}