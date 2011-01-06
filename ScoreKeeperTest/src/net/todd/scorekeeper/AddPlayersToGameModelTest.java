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
import net.todd.scorekeeper.data.ScoreBoardEntry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddPlayersToGameModelTest {
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

	private AddPlayersToGameModel testObject;
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
		doReturn(allPlayers).when(playerStore).getAllPlayers();

		currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setScoring(Scoring.LOW);
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new AddPlayersToGameModel(playerStore, pageNavigator);
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

		testObject.done();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualCurrentGame = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), actualCurrentGame.getGameName());
		assertEquals(currentGame.getScoreBoard().getScoring(), actualCurrentGame.getScoreBoard()
				.getScoring());
		List<ScoreBoardEntry> entries = actualCurrentGame.getScoreBoard().getEntries();
		assertEquals(2, entries.size());
		assertEquals(player1, entries.get(0).getPlayer());
		assertEquals(player2, entries.get(1).getPlayer());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ifTwoPlayersAreSelectedAndOneIsDeselectedThenTheSelectedPlayersAreOnlyTheSelectedPlayers() {
		testObject.selectionChanged(playerOneId, true);
		testObject.selectionChanged(playerTwoId, true);
		testObject.selectionChanged(playerOneId, false);

		testObject.done();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualCurrentGame = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), actualCurrentGame.getGameName());
		List<ScoreBoardEntry> entries = actualCurrentGame.getScoreBoard().getEntries();
		assertEquals(1, entries.size());
		assertEquals(player2, entries.get(0).getPlayer());
	}

	@Test
	public void playersThatHaveAScoreBoardEntryAreAlreadySelected() {
		currentGame = new CurrentGame();
		ScoreBoard scoreBoard = new ScoreBoard();
		ScoreBoardEntry scoreBoardEntry = new ScoreBoardEntry();
		scoreBoardEntry.setPlayer(player2);
		scoreBoard.getEntries().add(scoreBoardEntry);
		currentGame.setScoreBoard(scoreBoard);
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new AddPlayersToGameModel(playerStore, pageNavigator);

		List<Player> allPlayers = testObject.getAllPlayers();
		assertEquals(3, allPlayers.size());
		assertFalse(allPlayers.get(0).isSelected());
		assertTrue(allPlayers.get(1).isSelected());
		assertFalse(allPlayers.get(2).isSelected());
	}
}
