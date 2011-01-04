package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
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

public class SetupGameModelTest {
	@Mock
	private PageNavigator pageNavigator;

	private SetupGameModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new SetupGameModel(pageNavigator);
	}

	@Test
	public void cancellingFinishesTheActivity() {
		testObject.cancel();

		verify(pageNavigator).navigateToActivityAndFinish(MainPageActivity.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void goingToTheAddPlayersScreenNavigatesToTheCorrectActivity() {
		String gameName = UUID.randomUUID().toString();
		testObject.setGameName(gameName);

		testObject.goToAddPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(AddPlayersToGameActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(gameName, game.getGameName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void goingToTheAddPlayersScreenWithExistingPlayersNavigatesToTheCorrectActivity() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2, player3));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");
		testObject = new SetupGameModel(pageNavigator);

		testObject.goToAddPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(AddPlayersToGameActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), game.getGameName());
		assertEquals(3, game.getScoreBoard().getEntries().size());
		assertEquals(player1, game.getScoreBoard().getEntries().get(0).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(0).getScore());
		assertEquals(player2, game.getScoreBoard().getEntries().get(1).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(1).getScore());
		assertEquals(player3, game.getScoreBoard().getEntries().get(2).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(2).getScore());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void goingToTheAddPlayersScreenWithoutGameNameAndPlayersAddedStillNavigatesCorrectly() {
		testObject.goToAddPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(AddPlayersToGameActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals("", game.getGameName());
		assertEquals(null, game.getCurrentPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().size());
	}

	@Test
	public void initiallyPlayersAreNotAddedToTheGame() {
		assertFalse(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifEmptyPlayersAreAddedThenPlayersAreNotAddedToTheGame() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.<Player> asList());
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifOnlyOnePlayerIsAddedThenPlayersAreNotAddedToTheGame() {
		Player player1 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifTwoPlayersAreAddedThenPlayersAreAddedToTheGame() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertTrue(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifMoreThanTwoPlayersAreAddedThenPlayersAreAddedToTheGame() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		Player player4 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2, player3, player4));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertTrue(testObject.arePlayersAddedToGame());
	}

	@Test
	public void changingGameNameOnlyNotifiesStateChangeListenersWhenValueIsDifferent() {
		String gameName1 = UUID.randomUUID().toString();
		String gameName2 = UUID.randomUUID().toString();

		Listener listener = mock(Listener.class);
		testObject.addStateChangedListener(listener);

		testObject.setGameName(gameName1);

		verify(listener).handle();
		reset(listener);

		testObject.setGameName(gameName1);

		verify(listener, never()).handle();
		reset(listener);

		testObject.setGameName(gameName2);

		verify(listener).handle();
	}

	@Test
	public void gameNameIsAvailableBeforeStateChangeListenersAreNotified() {
		String gameName = UUID.randomUUID().toString();

		final String[] gameNames = new String[1];
		testObject.addStateChangedListener(new Listener() {
			@Override
			public void handle() {
				gameNames[0] = testObject.getGameName();
			}
		});

		testObject.setGameName(gameName);

		assertEquals(gameName, gameNames[0]);
	}

	@Test
	public void initiallyGameIsNotSetupCompletely() {
		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsNotSetButPlayersAreSetThenGameIsNotSetupCompletely() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName("");
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(
				Arrays.asList(mock(Player.class), mock(Player.class)));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetButPlayersAreNotSetThenGameIsNotSetupCompletely() {
		testObject.setGameName(UUID.randomUUID().toString());

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetButNotEnoughPlayersAreSetThenGameIsNotSetupCompletely() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(mock(Player.class)));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);
		testObject.setGameName(UUID.randomUUID().toString());

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetAndEnoughPlayersAreSetThenGameIsSetupCompletely() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);
		testObject.setGameName(UUID.randomUUID().toString());

		assertTrue(testObject.isGameSetupComplete());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void goingToOrderPlayersScreenNavigatesToTheOrderPlayersActivityWithTheCorrectGame() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2, player3));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		testObject.goToOrderPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(OrderPlayersActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), game.getGameName());
		assertEquals(3, game.getScoreBoard().getEntries().size());
		assertEquals(player1, game.getScoreBoard().getEntries().get(0).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(0).getScore());
		assertEquals(player2, game.getScoreBoard().getEntries().get(1).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(1).getScore());
		assertEquals(player3, game.getScoreBoard().getEntries().get(2).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(2).getScore());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void startingTheGameNavigatesToTheGameActivityWithTheCorrectCurrentGame() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(Arrays.asList(player1, player2, player3));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);
		reset(pageNavigator);

		testObject.startGame();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(GameActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(currentGame.getGameName(), game.getGameName());
		assertEquals(3, game.getScoreBoard().getEntries().size());
		assertEquals(player1, game.getScoreBoard().getEntries().get(0).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(0).getScore());
		assertEquals(player2, game.getScoreBoard().getEntries().get(1).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(1).getScore());
		assertEquals(player3, game.getScoreBoard().getEntries().get(2).getPlayer());
		assertEquals(0, game.getScoreBoard().getEntries().get(2).getScore());
	}

	@Test
	public void whenACurrentGameIsNotAvailableThenTheGameNameIsEmpty() {
		assertEquals("", testObject.getGameName());
	}

	@Test
	public void whenACurrentGameIsAvailableThenPopulateTheGameName() {
		String gameName = UUID.randomUUID().toString();
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.setGameName(gameName);
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertEquals(gameName, testObject.getGameName());
	}

	@Test
	public void whenACurrentGameIsNotAvailableThenPlayersAreNotAddedToTheGame() {
		assertFalse("should be false by default", testObject.arePlayersAddedToGame());
	}

	@Test
	public void whenACurrentGameIsAvailableAndThereAreNoPlayersAddedThenArePlayersAreAddedToTheGameReturnFalse() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setScoreBoard(new ScoreBoard());
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse("should be false if there are no players", testObject.arePlayersAddedToGame());
	}

	@Test
	public void whenACurrentGameIsAvailableAndThereArePlayersAddedThenArePlayersAreAddedToTheGameReturnTrue() {
		ScoreBoard scoreBoard = mock(ScoreBoard.class);
		ScoreBoardEntry scoreBoardEntry1 = mock(ScoreBoardEntry.class);
		ScoreBoardEntry scoreBoardEntry2 = mock(ScoreBoardEntry.class);
		doReturn(Arrays.asList(scoreBoardEntry1, scoreBoardEntry2)).when(scoreBoard).getEntries();
		CurrentGame currentGame = mock(CurrentGame.class);
		doReturn(scoreBoard).when(currentGame).getScoreBoard();
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertTrue(testObject.arePlayersAddedToGame());
	}

	@Test
	public void whenACurrentGameIsAvailableAndThereArePlayersButNoGameNameThenGameSetupIsNotComplete() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName("");
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(
				Arrays.asList(mock(Player.class), mock(Player.class)));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void whenACurrentGameIsAvailableAndThereArePlayersAndAGameNameThenGameSetupIsComplete() {
		CurrentGame currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setScoreBoard(new ScoreBoard());
		currentGame.getScoreBoard().setPlayers(
				Arrays.asList(mock(Player.class), mock(Player.class)));
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertTrue(testObject.isGameSetupComplete());
	}
}
