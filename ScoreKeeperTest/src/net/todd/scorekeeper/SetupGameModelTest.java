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

import android.content.Intent;

public class SetupGameModelTest {
	@Mock
	private SetupGameActivity activity;
	@Mock
	private PlayerStore playerStore;
	@Mock
	private IntentFactory intentFactory;
	
	private List<Player> allPlayers;
	private int playerOneId;
	private int playerTwoId;
	private int playerThreeId;
	private Player player1;
	private Player player2;
	private Player player3;

	private SetupGameModel testObject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		playerOneId = new Random().nextInt();
		player1 = new Player(playerOneId, "Peter");
		playerTwoId = new Random().nextInt();
		player2 = new Player(playerTwoId, "James");
		playerThreeId = new Random().nextInt();
		player3 = new Player(playerThreeId, "John");
		
		allPlayers = Arrays.asList(player1, player2, player3);
		doReturn(allPlayers).when(playerStore).getAllPlayers();

		testObject = new SetupGameModel(activity, playerStore, intentFactory);
	}

	@Test
	public void getAllPlayersReturnsAllPlayersFromPlayerStore() {
		assertEquals(allPlayers, testObject.getAllPlayers());
	}

	@Test
	public void initiallyAtLeastTwoPlayersAreNotSelected() {
		assertFalse(testObject.atLeastTwoPlayersSelected());
	}

	@Test
	public void ifOnePlayerIsSelectedThenAtLeastTwoPlayersAreNotSelected() {
		testObject.selectionChanged(playerOneId, true);

		assertFalse(testObject.atLeastTwoPlayersSelected());
	}

	@Test
	public void ifTwoPlayerIsSelectedThenAtLeastTwoPlayersAreSelected() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);

		assertTrue(testObject.atLeastTwoPlayersSelected());
	}

	@Test
	public void ifThreePlayerIsSelectedThenAtLeastTwoPlayersAreSelected() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);
		testObject.selectionChanged(playerThreeId, true);

		assertTrue(testObject.atLeastTwoPlayersSelected());
	}

	@Test
	public void ifTwoPlayersAreSelectedAndOneIsDeselectedThenAtLeastTwoPlayersAreNotSelected() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);
		testObject.selectionChanged(playerOneId, false);

		assertFalse(testObject.atLeastTwoPlayersSelected());
	}

	@Test
	public void whenGoingToOrderPlayerPageThenCreatIntentFromFactory() {
		Intent expectedIntent = mock(Intent.class);
		doReturn(expectedIntent).when(intentFactory).createIntent(activity,
				GameActivity.class);

		testObject.startGame();

		ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
		verify(activity).startActivity(intentCaptor.capture());
		Intent actualIntent = intentCaptor.getValue();

		assertSame(expectedIntent, actualIntent);
	}

	@Test
	public void ifTwoPlayersAreSelectedThenTheSelectedPlayersAreTheSelectedPlayers() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);

		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.startGame();

		ArgumentCaptor<Serializable> serializableCaptor = ArgumentCaptor
				.forClass(Serializable.class);
		verify(intent).putExtra(eq("selectedPlayers"), serializableCaptor.capture());
		ArrayList<?> selectedPlayers = (ArrayList<?>) serializableCaptor.getValue();

		assertEquals(Arrays.asList(player1, player2), selectedPlayers);
	}
	
	@Test
	public void ifTwoPlayersAreSelectedAndOneIsDeselectedThenTheSelectedPlayersAreOnlyTheSelectedPlayers() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);
		testObject.selectionChanged(playerOneId, false);

		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.startGame();

		ArgumentCaptor<Serializable> serializableCaptor = ArgumentCaptor
				.forClass(Serializable.class);
		verify(intent).putExtra(eq("selectedPlayers"), serializableCaptor.capture());
		ArrayList<?> selectedPlayers = (ArrayList<?>) serializableCaptor.getValue();

		assertEquals(Arrays.asList(player2), selectedPlayers);
	}
	
	@Test
	public void cancellingFinishesTheActivity() {
		testObject.cancel();
		
		verify(activity).finish();
	}
	
	@Test
	public void movingAPlayerDown() {
		testObject.movePlayerDown(playerOneId);
		List<Player> players = testObject.getAllPlayers();

		assertEquals(Arrays.asList(player2, player1, player3), players);
	}
	
	@Test
	public void movingAPlayerUp() {
		testObject.movePlayerUp(playerThreeId);
		List<Player> players = testObject.getAllPlayers();
		
		assertEquals(Arrays.asList(player1, player3, player2), players);
	}
	
	@Test
	public void movingTheLowestPlayerDownDoesNothing() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerDown(playerThreeId);
		List<Player> players = testObject.getAllPlayers();

		assertEquals(Arrays.asList(player1, player2, player3), players);
	}
	
	@Test
	public void movingTheHighestPlayerUpDoesNothing() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, GameActivity.class);

		testObject.movePlayerUp(playerOneId);
		List<Player> players = testObject.getAllPlayers();

		assertEquals(Arrays.asList(player1, player2, player3), players);
	}
	
	@Test
	public void movingAPlayerDownNotifiesThatThePlayersOrderChanged() {
		Listener listener = mock(Listener.class);
		testObject.addPlayersOrderChangedListener(listener);

		testObject.movePlayerDown(playerOneId);

		verify(listener).handle();
	}

	@Test
	public void movingAPlayerUpNotifiesThatThePlayersOrderChanged() {
		Listener listener = mock(Listener.class);
		testObject.addPlayersOrderChangedListener(listener);

		testObject.movePlayerUp(playerThreeId);

		verify(listener).handle();
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
}
