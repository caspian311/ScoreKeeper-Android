package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.content.Intent;

public class GameModelTest {
	@Mock
	private Activity activity;
	@Mock
	private Player player1;
	@Mock
	private Player player2;
	@Mock
	private Player player3;

	private GameModel testObject;

	private ArrayList<Player> selectedPlayers;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		selectedPlayers = new ArrayList<Player>(Arrays.asList(player1, player2, player3));

		Intent intent = mock(Intent.class);
		doReturn(intent).when(activity).getIntent();
		doReturn(selectedPlayers).when(intent).getSerializableExtra("selectedPlayers");

		testObject = new GameModel(activity);
	}

	@Test
	public void everyTimeYouCallNextPlayerYouGetTheNextPlayer() {
		assertSame(player1, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
	}

	@Test
	public void everyTimeYouCallPreviousPlayerYouGetThePreviousPlayer() {
		testObject.previousPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
	}

	@Test
	public void initiallyAllPlayersScoreIs0() {
		testObject.nextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());

		testObject.nextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());

		testObject.nextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());
	}

	@Test
	public void afterSettingAPlayersScoreTheScoreIsStored() {
		int firstScore = new Random().nextInt();
		int secondScore = new Random().nextInt();
		int thirdScore = new Random().nextInt();

		testObject.nextPlayer();
		testObject.setScoreForCurrentPlayer(firstScore);

		testObject.nextPlayer();
		testObject.setScoreForCurrentPlayer(secondScore);

		testObject.nextPlayer();
		testObject.setScoreForCurrentPlayer(thirdScore);

		testObject.nextPlayer();
		assertEquals(firstScore, testObject.getCurrentPlayersScore());

		testObject.nextPlayer();
		assertEquals(secondScore, testObject.getCurrentPlayersScore());

		testObject.nextPlayer();
		assertEquals(thirdScore, testObject.getCurrentPlayersScore());
	}

	@Test
	public void settingANewScoreAddsToThePreviousScore() {
		int firstScore = new Random().nextInt();
		int secondScore = new Random().nextInt();
		testObject.nextPlayer();
		testObject.setScoreForCurrentPlayer(firstScore);
		testObject.setScoreForCurrentPlayer(secondScore);

		assertEquals(firstScore + secondScore, testObject.getCurrentPlayersScore());
	}

	@Test
	public void cancellingTheGameFinishesTheActivity() {
		testObject.cancelGame();

		verify(activity).finish();
	}

	@Test
	public void scoreChangedListenersAreNotifiedWhenScoresChange() {
		Listener listener = mock(Listener.class);
		testObject.addScoreChangedListener(listener);
		testObject.setScoreForCurrentPlayer(new Random().nextInt());

		verify(listener).handle();
	}

	@Test
	public void playerChangedListenersAreNotifiedWhenGoingToNextPlayer() {
		Listener listener = mock(Listener.class);
		testObject.addPlayerChangedListener(listener);
		testObject.nextPlayer();

		verify(listener).handle();
	}

	@Test
	public void playerChangedListenersAreNotifiedWhenGoingToPreviousPlayer() {
		Listener listener = mock(Listener.class);
		testObject.addPlayerChangedListener(listener);
		testObject.previousPlayer();

		verify(listener).handle();
	}
}
