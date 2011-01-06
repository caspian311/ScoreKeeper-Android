package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderPlayersModelTest {
	@Mock
	private PageNavigator pageNavigator;

	private List<Player> allPlayers;
	private String playerOneId;
	private String playerTwoId;
	private String playerThreeId;
	private Player player1;
	private Player player2;
	private Player player3;

	private OrderPlayersModel testObject;

	private CurrentGame currentGame;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		playerOneId = UUID.randomUUID().toString();
		player1 = new Player();
		player1.setId(playerOneId);
		player1.setName("Peter");
		playerTwoId = UUID.randomUUID().toString();
		player2 = new Player();
		player2.setId(playerTwoId);
		player2.setName("James");
		playerThreeId = UUID.randomUUID().toString();
		player3 = new Player();
		player3.setId(playerThreeId);
		player3.setName("John");
		allPlayers = Arrays.asList(player1, player2, player3);

		currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setScoring(Scoring.HIGH);
		scoreBoard.setPlayers(allPlayers);
		currentGame.setScoreBoard(scoreBoard);
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new OrderPlayersModel(pageNavigator);
	}

	@Test
	public void getAllPlayersReturnsAllPlayersFromPlayerStore() {
		assertEquals(allPlayers, testObject.getAllPlayers());
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ifNoReorderingWasDonePlayersArePassedAlongWithTheCurrentGameInOriginalOrder() {
		testObject.done();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualCurrentGame = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), actualCurrentGame.getGameName());
		assertEquals(currentGame.getScoreBoard().getScoring(), actualCurrentGame.getScoreBoard()
				.getScoring());
		assertEquals(3, actualCurrentGame.getScoreBoard().getEntries().size());
		assertEquals(player1, actualCurrentGame.getScoreBoard().getEntries().get(0).getPlayer());
		assertEquals(player2, actualCurrentGame.getScoreBoard().getEntries().get(1).getPlayer());
		assertEquals(player3, actualCurrentGame.getScoreBoard().getEntries().get(2).getPlayer());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void reorderedPlayersArePassedAlongWithTheCurrentGameToTheSetupGamePage() {
		testObject.movePlayerDown(playerOneId);
		testObject.movePlayerUp(playerThreeId);

		testObject.done();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualCurrentGame = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), actualCurrentGame.getGameName());
		assertEquals(currentGame.getScoreBoard().getScoring(), actualCurrentGame.getScoreBoard()
				.getScoring());
		assertEquals(3, actualCurrentGame.getScoreBoard().getEntries().size());
		assertEquals(player2, actualCurrentGame.getScoreBoard().getEntries().get(0).getPlayer());
		assertEquals(player3, actualCurrentGame.getScoreBoard().getEntries().get(1).getPlayer());
		assertEquals(player1, actualCurrentGame.getScoreBoard().getEntries().get(2).getPlayer());
	}
}
