package net.todd.scorekeeper.test;

import java.util.Arrays;

import net.todd.scorekeeper.GameActivity;
import net.todd.scorekeeper.GameView;
import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

public class GameActivityTest extends ActivityInstrumentationTestCase2<GameActivity> {
	private GameActivity activity;

	public GameActivityTest() {
		super("net.todd.scorekeeper", GameActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Player player1 = new Player();
		player1.setId("1");
		player1.setName("Matt");
		Player player2 = new Player();
		player2.setId("2");
		player2.setName("Abbi");
		Player player3 = new Player();
		player3.setId("3");
		player3.setName("Caleb");

		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(Arrays.asList(player1, player2, player3));

		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName("Farkle");
		currentGame.setScoreBoard(scoreBoard);

		Intent intent = new Intent();
		intent.putExtra("currentGame", currentGame);
		setActivityIntent(intent);

		activity = getActivity();
	}

	public void testGameNameIsPopulatedFromIntent() throws Exception {
		assertEquals("Farkle", gameName());
	}

	public void testPlayerNamesDisplaysInCorrectOrderAsUserCyclesThroughNextPlayer()
			throws Exception {
		assertEquals("Matt", currentPlayerName());

		clickNext();

		assertEquals("Abbi", currentPlayerName());

		clickNext();

		assertEquals("Caleb", currentPlayerName());

		clickNext();

		assertEquals("Matt", currentPlayerName());

		clickNext();

		assertEquals("Abbi", currentPlayerName());

		clickNext();

		assertEquals("Caleb", currentPlayerName());
	}

	public void testPlayerNamesDisplaysInCorrectOrderAsUserCyclesThroughPreviousPlayer()
			throws Exception {
		assertEquals("Matt", currentPlayerName());

		clickPrevious();

		assertEquals("Caleb", currentPlayerName());

		clickPrevious();

		assertEquals("Abbi", currentPlayerName());

		clickPrevious();

		assertEquals("Matt", currentPlayerName());

		clickPrevious();

		assertEquals("Caleb", currentPlayerName());

		clickPrevious();

		assertEquals("Abbi", currentPlayerName());
	}

	public void testSettingScoreDoesNotChangeTheOrderOfThePlayers() throws Exception {
		assertEquals("Matt", currentPlayerName());
		setScore("111");

		clickNext();

		assertEquals("Abbi", currentPlayerName());
		setScore("333");

		clickNext();

		assertEquals("Caleb", currentPlayerName());
		setScore("222");

		clickNext();

		assertEquals("Matt", currentPlayerName());
		assertEquals("111", currentScore());

		clickNext();

		assertEquals("Abbi", currentPlayerName());
		assertEquals("333", currentScore());

		clickNext();

		assertEquals("Caleb", currentPlayerName());
		assertEquals("222", currentScore());
	}

	private void clickNext() {
		clickButton(GameView.NEXT_BUTTON_ID);
	}

	private void clickPrevious() {
		clickButton(GameView.PREVIOUS_BUTTON_ID);
	}

	private void clickButton(int id) {
		Button button = (Button) activity.findViewById(id);
		TouchUtils.clickView(this, button);
	}

	private String currentPlayerName() {
		return labelTextById(GameView.PLAYER_NAME_LABEL_ID);
	}

	private String gameName() {
		return labelTextById(GameView.GAME_NAME_LABEL_ID);
	}

	private String currentScore() {
		return labelTextById(GameView.PLAYER_SCORE_LABEL_ID);
	}

	private void setScore(final String text) {
		final TextView textField = textFieldById(GameView.SCORE_TEXTFIELD_ID);
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textField.setText(text);
			}
		});
	}

	private TextView textFieldById(int id) {
		return (TextView) activity.findViewById(id);
	}

	private String labelTextById(int id) {
		TextView playerNameText = (TextView) activity.findViewById(id);
		return playerNameText.getText().toString();
	}
}
