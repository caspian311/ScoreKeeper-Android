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

public class PickPlayersModelTest {
	@Mock
	private PickPlayersActivity activity;
	@Mock
	private PlayerStore playerStore;

	private PickPlayersModel testObject;
	private int playerOneId;
	private int playerTwoId;
	private int playerThreeId;
	@Mock
	private Player player1;
	@Mock
	private Player player2;
	@Mock
	private Player player3;
	@Mock
	private IntentFactory intentFactory;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		playerOneId = new Random().nextInt();
		doReturn(player1).when(playerStore).getPlayerById(playerOneId);
		playerTwoId = new Random().nextInt();
		doReturn(player2).when(playerStore).getPlayerById(playerTwoId);
		playerThreeId = new Random().nextInt();
		doReturn(player3).when(playerStore).getPlayerById(playerThreeId);

		testObject = new PickPlayersModel(activity, playerStore, intentFactory);
	}

	@Test
	public void getAllPlayersReturnsAllPlayersFromPlayerStore() {
		List<Player> allPlayers = Arrays.asList(player1, player2);
		doReturn(allPlayers).when(playerStore).getAllPlayers();

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
				OrderPlayersActivity.class);

		testObject.goToOrderPlayerPage();

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
		doReturn(intent).when(intentFactory).createIntent(activity, OrderPlayersActivity.class);

		testObject.goToOrderPlayerPage();

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
		doReturn(intent).when(intentFactory).createIntent(activity, OrderPlayersActivity.class);

		testObject.goToOrderPlayerPage();

		ArgumentCaptor<Serializable> serializableCaptor = ArgumentCaptor
				.forClass(Serializable.class);
		verify(intent).putExtra(eq("selectedPlayers"), serializableCaptor.capture());
		ArrayList<?> selectedPlayers = (ArrayList<?>) serializableCaptor.getValue();

		assertEquals(Arrays.asList(player2), selectedPlayers);
	}
}
