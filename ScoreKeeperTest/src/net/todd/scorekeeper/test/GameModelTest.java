package net.todd.scorekeeper.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.todd.scorekeeper.GameModel;
import net.todd.scorekeeper.Player;

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
		assertSame(player1, testObject.getNextPlayer());
		assertSame(player2, testObject.getNextPlayer());
		assertSame(player3, testObject.getNextPlayer());
		assertSame(player1, testObject.getNextPlayer());
		assertSame(player2, testObject.getNextPlayer());
		assertSame(player3, testObject.getNextPlayer());
	}

	@Test
	public void everyTimeYouCallPreviousPlayerYouGetThePreviousPlayer() {
		assertSame(player3, testObject.getPreviousPlayer());
		assertSame(player2, testObject.getPreviousPlayer());
		assertSame(player1, testObject.getPreviousPlayer());
		assertSame(player3, testObject.getPreviousPlayer());
		assertSame(player2, testObject.getPreviousPlayer());
		assertSame(player1, testObject.getPreviousPlayer());
	}

	@Test
	public void initiallyAllPlayersScoreIs0() {
		testObject.getNextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());

		testObject.getNextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());

		testObject.getNextPlayer();
		assertEquals(0, testObject.getCurrentPlayersScore());
	}

	@Test
	public void afterSettingAPlayersScoreTheScoreIsStored() {
		int firstScore = new Random().nextInt();
		int secondScore = new Random().nextInt();
		int thirdScore = new Random().nextInt();

		testObject.getNextPlayer();
		testObject.setScoreForCurrentPlayer(firstScore);

		testObject.getNextPlayer();
		testObject.setScoreForCurrentPlayer(secondScore);

		testObject.getNextPlayer();
		testObject.setScoreForCurrentPlayer(thirdScore);

		testObject.getNextPlayer();
		assertEquals(firstScore, testObject.getCurrentPlayersScore());

		testObject.getNextPlayer();
		assertEquals(secondScore, testObject.getCurrentPlayersScore());

		testObject.getNextPlayer();
		assertEquals(thirdScore, testObject.getCurrentPlayersScore());
	}

	@Test
	public void settingANewScoreAddsToThePreviousScore() {
		int firstScore = new Random().nextInt();
		int secondScore = new Random().nextInt();
		testObject.getNextPlayer();
		testObject.setScoreForCurrentPlayer(firstScore);
		testObject.setScoreForCurrentPlayer(secondScore);

		assertEquals(firstScore + secondScore, testObject.getCurrentPlayersScore());
	}
}
