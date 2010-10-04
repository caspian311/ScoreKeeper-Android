package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;

public class AddUsersActivity extends Activity {
//	private Button addButton;
//	private EditText playerNameText;
//	private ArrayAdapter<String> adapter;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.add_user);
//
//		playerNameText = (EditText) findViewById(R.id.EditText01);
//		final ListView listOfPlayers = (ListView) findViewById(R.id.ListView01);
//		adapter = new ArrayAdapter<String>(listOfPlayers.getContext(),
//				R.layout.row, R.id.TextView01);
//		listOfPlayers.setAdapter(adapter);
//
//		addButton = (Button) findViewById(R.id.Button01);
//		addButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String playerName = playerNameText.getText().toString();
//				adapter.add(playerName);
//				playerNameText.getText().clear();
//			}
//		});
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AddPlayerView view = new AddPlayerView(this);
		AddPlayerModel model = new AddPlayerModel(this);
		AddPlayerPresenter.create(view, model);
		
		setContentView(view.getView());
	}
}