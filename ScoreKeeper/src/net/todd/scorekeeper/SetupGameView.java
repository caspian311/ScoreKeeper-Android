package net.todd.scorekeeper;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SetupGameView {
	private final ListenerManager backPressedListenerManager = new ListenerManager();
	private final ListenerManager addPlayersButtonPressedListenerManager = new ListenerManager();
	private final ListenerManager orderPlayersButtonPressedListenerManager = new ListenerManager();
	private final ListenerManager scoringChangedListenerManager = new ListenerManager();
	private final ListenerManager startGameButtonPressedListenerManager = new ListenerManager();
	private final ListenerManager gameNameChangedListenerManager = new ListenerManager();

	private final ScrollView mainScrollView;
	private final Button orderPlayersButton;
	private final Button startGameButton;
	private EditText gameNameText;
	private Spinner scoringSpinner;

	public SetupGameView(Context context) {
		mainScrollView = new ScrollView(context);
		mainScrollView.setFillViewport(true);
		mainScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		LinearLayout mainView = new LinearLayout(context);
		mainView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		mainLayoutParams.leftMargin = UIConstants.MARGIN_SIZE;
		mainLayoutParams.rightMargin = UIConstants.MARGIN_SIZE;
		mainView.setLayoutParams(mainLayoutParams);
		BackgroundUtil.setBackground(mainView);
		mainView.setOrientation(LinearLayout.VERTICAL);
		mainScrollView.addView(mainView);

		TextView title = new TextView(context);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setText("Setup the Game");
		title.setTextSize(UIConstants.TEXT_TITLE_SIZE);
		title.setTextColor(UIConstants.TEXT_COLOR);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(title);

		TextView gameNameLabel = new TextView(context);
		gameNameLabel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		gameNameLabel.setTextSize(UIConstants.TEXT_SMALL_SIZE);
		gameNameLabel.setText("Game name:");
		gameNameLabel.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(gameNameLabel);

		gameNameText = new EditText(context);
		LinearLayout.LayoutParams gameTextLayout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		gameTextLayout.leftMargin = UIConstants.MARGIN_SIZE;
		gameTextLayout.rightMargin = UIConstants.MARGIN_SIZE;
		gameNameText.setGravity(Gravity.CENTER_HORIZONTAL);
		gameNameText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				gameNameChangedListenerManager.notifyListeners();
			}
		});
		gameNameText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				gameNameChangedListenerManager.notifyListeners();
				return false;
			}
		});
		gameNameText.setLayoutParams(gameTextLayout);
		mainView.addView(gameNameText);

		TextView gameNameExplanationLabel = new TextView(context);
		LinearLayout.LayoutParams gameNameExplanationLayout = new LinearLayout.LayoutParams(
				new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		gameNameExplanationLayout.bottomMargin = UIConstants.MARGIN_SIZE;
		gameNameExplanationLayout.leftMargin = UIConstants.MARGIN_SIZE;
		gameNameExplanationLayout.rightMargin = UIConstants.MARGIN_SIZE;
		gameNameExplanationLabel.setLayoutParams(gameNameExplanationLayout);
		gameNameExplanationLabel.setTextSize(UIConstants.TEXT_TINY_SIZE);
		gameNameExplanationLabel.setText("(ex. Scrabble, Rook, Farkle, etc.)");
		gameNameExplanationLabel.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(gameNameExplanationLabel);

		Button addPlayersButton = new Button(context);
		LinearLayout.LayoutParams addPlayersButtonLayout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, UIConstants.BUTTON_HEIGHT);
		addPlayersButtonLayout.bottomMargin = UIConstants.MARGIN_SIZE;
		addPlayersButtonLayout.leftMargin = UIConstants.MARGIN_SIZE;
		addPlayersButtonLayout.rightMargin = UIConstants.MARGIN_SIZE;
		addPlayersButton.setLayoutParams(addPlayersButtonLayout);
		addPlayersButton.setText("Add Players >");
		addPlayersButton.setGravity(Gravity.CENTER_HORIZONTAL);
		addPlayersButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addPlayersButtonPressedListenerManager.notifyListeners();
			}
		});
		mainView.addView(addPlayersButton);

		orderPlayersButton = new Button(context);
		LinearLayout.LayoutParams orderPlayersButtonLayout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, UIConstants.BUTTON_HEIGHT);
		orderPlayersButtonLayout.bottomMargin = UIConstants.MARGIN_SIZE;
		orderPlayersButtonLayout.leftMargin = UIConstants.MARGIN_SIZE;
		orderPlayersButtonLayout.rightMargin = UIConstants.MARGIN_SIZE;
		orderPlayersButton.setLayoutParams(orderPlayersButtonLayout);
		orderPlayersButton.setText("Order Players >");
		orderPlayersButton.setGravity(Gravity.CENTER_HORIZONTAL);
		orderPlayersButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderPlayersButtonPressedListenerManager.notifyListeners();
			}
		});
		mainView.addView(orderPlayersButton);

		TextView scoringLabel = new TextView(context);
		scoringLabel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		scoringLabel.setTextSize(UIConstants.TEXT_SMALL_SIZE);
		scoringLabel.setText("Scoring:");
		scoringLabel.setGravity(Gravity.CENTER_HORIZONTAL);
		mainView.addView(scoringLabel);

		scoringSpinner = new Spinner(context);
		scoringSpinner.setAdapter(new ArrayAdapter<Scoring>(context,
				android.R.layout.simple_spinner_item, Scoring.values()));
		LinearLayout.LayoutParams spinnerLayout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		spinnerLayout.bottomMargin = UIConstants.MARGIN_SIZE;
		spinnerLayout.leftMargin = UIConstants.MARGIN_SIZE;
		spinnerLayout.rightMargin = UIConstants.MARGIN_SIZE;
		scoringSpinner.setLayoutParams(spinnerLayout);
		scoringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				scoringChangedListenerManager.notifyListeners();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		mainView.addView(scoringSpinner);

		startGameButton = new Button(context);
		LinearLayout.LayoutParams startGameLayout = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, UIConstants.BUTTON_HEIGHT);
		startGameLayout.leftMargin = UIConstants.MARGIN_SIZE;
		startGameLayout.rightMargin = UIConstants.MARGIN_SIZE;
		startGameButton.setLayoutParams(startGameLayout);
		startGameButton.setText("Start Game");
		startGameButton.setGravity(Gravity.CENTER_HORIZONTAL);
		startGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGameButtonPressedListenerManager.notifyListeners();
			}
		});
		mainView.addView(startGameButton);
	}

	public View getView() {
		return mainScrollView;
	}

	public void backPressed() {
		backPressedListenerManager.notifyListeners();
	}

	public void addBackPressedListener(Listener listener) {
		backPressedListenerManager.addListener(listener);
	}

	public void addAddPlayersButtonPressedListener(Listener listener) {
		addPlayersButtonPressedListenerManager.addListener(listener);
	}

	public void addOrderPlayersPressedListener(Listener listener) {
		orderPlayersButtonPressedListenerManager.addListener(listener);
	}

	public void addScoringChangedListener(Listener listener) {
		scoringChangedListenerManager.addListener(listener);
	}

	public void addStartGamePressedListener(Listener listener) {
		startGameButtonPressedListenerManager.addListener(listener);
	}

	public void setStartGameButtonEnabled(boolean isEnabled) {
		startGameButton.setEnabled(isEnabled);
	}

	public void setOrderPlayersButtonEnabled(boolean isEnabled) {
		orderPlayersButton.setEnabled(isEnabled);
	}

	public void addGameNameChangedListener(Listener listener) {
		gameNameChangedListenerManager.addListener(listener);
	}

	public String getGameName() {
		return gameNameText.getText().toString();
	}

	public void setGameName(String gameName) {
		gameNameText.setText(gameName);
	}

	public void setScoring(Scoring scoring) {
		for (int i = 0; i < scoringSpinner.getCount(); i++) {
			if (scoringSpinner.getItemAtPosition(i) == scoring) {
				scoringSpinner.setSelection(i);
				break;
			}
		}
	}

	public Scoring getScoring() {
		return Scoring.class.cast(scoringSpinner.getSelectedItem());
	}
}
