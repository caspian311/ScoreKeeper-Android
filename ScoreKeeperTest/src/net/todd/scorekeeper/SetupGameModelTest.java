package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SetupGameModelTest {
	@Mock
	private PlayerStore playerStore;
	@Mock
	private PageNavigator pageNavigator;
	
	private List<Player> allPlayers;
	private String playerOneId;
	private String playerTwoId;
	private String playerThreeId;
	private Player player1;
	private Player player2;
	private Player player3;

	private SetupGameModel testObject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		playerOneId = UUID.randomUUID().toString();
		player1 = new Player(playerOneId, "Peter");
		playerTwoId = UUID.randomUUID().toString();
		player2 = new Player(playerTwoId, "James");
		playerThreeId = UUID.randomUUID().toString();
		player3 = new Player(playerThreeId, "John");
		
		allPlayers = Arrays.asList(player1, player2, player3);
		doReturn(allPlayers).when(playerStore).getAllPlayers();

		testObject = new SetupGameModel(playerStore, pageNavigator);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ifTwoPlayersAreSelectedThenTheSelectedPlayersAreTheSelectedPlayers() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);

		testObject.startGame();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivity(eq(GameActivity.class), extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();
		
		assertEquals(Arrays.asList(player1, player2), extras.get("selectedPlayers"));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ifTwoPlayersAreSelectedAndOneIsDeselectedThenTheSelectedPlayersAreOnlyTheSelectedPlayers() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);
		testObject.selectionChanged(playerOneId, false);

		testObject.startGame();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivity(eq(GameActivity.class), extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();
		
		assertEquals(Arrays.asList(player2), extras.get("selectedPlayers"));
	}
	
	@Test
	public void cancellingFinishesTheActivity() {
		testObject.cancel();
		
		verify(pageNavigator).navigateToActivity(MainPageActivity.class);
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
		testObject.movePlayerDown(playerThreeId);
		List<Player> players = testObject.getAllPlayers();

		assertEquals(Arrays.asList(player1, player2, player3), players);
	}
	
	@Test
	public void movingTheHighestPlayerUpDoesNothing() {
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
}
