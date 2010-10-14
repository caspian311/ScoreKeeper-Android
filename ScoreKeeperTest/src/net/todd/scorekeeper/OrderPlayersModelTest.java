package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.content.Intent;

public class OrderPlayersModelTest {
	@Mock
	private Activity activity;
	@Mock
	private IntentFactory intentFactory;

	private OrderPlayersModel testObject;

	private ArrayList<Player> selectedPlayers;

	@Mock
	private Player player1;
	@Mock
	private Player player2;
	@Mock
	private Player player3;

	private int playerId1;
	private int playerId2;
	private int playerId3;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		playerId1 = new Random().nextInt();
		doReturn(playerId1).when(player1).getId();

		playerId2 = new Random().nextInt();
		doReturn(playerId2).when(player2).getId();

		playerId3 = new Random().nextInt();
		doReturn(playerId3).when(player3).getId();

		selectedPlayers = new ArrayList<Player>(Arrays.asList(player1, player2, player3));

		Intent intent = mock(Intent.class);
		doReturn(intent).when(activity).getIntent();
		doReturn(selectedPlayers).when(intent).getSerializableExtra("selectedPlayers");

		testObject = new OrderPlayersModel(activity, intentFactory);
	}

	@Test
	public void selectedPlayersArePulledFromTheIntent() {
		assertSame(selectedPlayers, testObject.getSelectedPlayers());
	}

	@Test
	public void goingBackToPickPlayersFinishesTheActivity() {
		testObject.cancel();

		verify(activity).finish();
	}

	@Test
	public void movingAPlayerDown() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerDown(playerId1);
		List<Player> players = testObject.getSelectedPlayers();

		assertEquals(Arrays.asList(player2, player1, player3), players);
	}
	
	@Test
	public void movingTheLowestPlayerDownDoesNothing() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerDown(playerId3);
		testObject.startGame();

		ArgumentCaptor<Serializable> playersCaptor = ArgumentCaptor.forClass(Serializable.class);
		verify(intent).putExtra(eq("selectedPlayers"), playersCaptor.capture());
		List<?> players = (List<?>)playersCaptor.getValue();
		
		assertEquals(Arrays.asList(player1, player2, player3), players);
	}
	
	@Test
	public void movingAPlayerUp() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerUp(playerId3);
		List<Player> players = testObject.getSelectedPlayers();
		
		assertEquals(Arrays.asList(player1, player3, player2), players);
	}

	@Test
	public void movingTheHighestPlayerUpDoesNothing() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerUp(playerId1);
		testObject.startGame();

		ArgumentCaptor<Serializable> playersCaptor = ArgumentCaptor.forClass(Serializable.class);
		verify(intent).putExtra(eq("selectedPlayers"), playersCaptor.capture());
		List<?> players = (List<?>)playersCaptor.getValue();
		
		assertEquals(Arrays.asList(player1, player2, player3), players);
	}
	
	@Test
	public void startingTheGame() {
		Intent expectedIntent = mock(Intent.class);
		doReturn(expectedIntent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.startGame();

		ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
		verify(activity).startActivity(intentCaptor.capture());
		Intent actualIntent = intentCaptor.getValue();

		assertSame(expectedIntent, actualIntent);
	}

	@Test
	public void movingAPlayerDownNotifiesThatThePlayersOrderChanged() {
		Listener listener = mock(Listener.class);
		testObject.addPlayersOrderChangedListener(listener);

		testObject.movePlayerDown(playerId1);

		verify(listener).handle();
	}

	@Test
	public void movingAPlayerUpNotifiesThatThePlayersOrderChanged() {
		Listener listener = mock(Listener.class);
		testObject.addPlayersOrderChangedListener(listener);

		testObject.movePlayerUp(playerId3);

		verify(listener).handle();
	}
}
