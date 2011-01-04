package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		List<Player> players = Arrays.asList(player1, player2, player3);
		testObject.setPlayers(players);

		testObject.goToAddPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(AddPlayersToGameActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(gameName, game.getGameName());
		assertEquals(player1, game.getCurrentPlayer());
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
		testObject.setPlayers(Arrays.<Player> asList());

		assertFalse(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifOnlyOnePlayerIsAddedThenPlayersAreNotAddedToTheGame() {
		testObject.setPlayers(Arrays.asList(mock(Player.class)));

		assertFalse(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifTwoPlayersAreAddedThenPlayersAreAddedToTheGame() {
		testObject.setPlayers(Arrays.asList(mock(Player.class), mock(Player.class)));

		assertTrue(testObject.arePlayersAddedToGame());
	}

	@Test
	public void ifMoreThanTwoPlayersAreAddedThenPlayersAreAddedToTheGame() {
		testObject.setPlayers(Arrays.asList(mock(Player.class), mock(Player.class),
				mock(Player.class), mock(Player.class)));

		assertTrue(testObject.arePlayersAddedToGame());
	}

	@Test
	public void notifyStateChangedListenerWhenAddedPlayersAreChangedToADiffererntSetOfPlayers() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);

		Listener listener = mock(Listener.class);
		testObject.addStateChangedListener(listener);

		testObject.setPlayers(Arrays.asList(player1));

		verify(listener).handle();
		reset(listener);

		testObject.setPlayers(Arrays.asList(player1));

		verify(listener, never()).handle();
		reset(listener);

		testObject.setPlayers(Arrays.asList(player2));

		verify(listener).handle();
	}

	@Test
	public void playersAreMadeAvailableBeforeTheStateChangeListenersAreNotified() {
		List<Player> expectedPlayers = Arrays.asList(mock(Player.class), mock(Player.class));

		final List<Player> actualPlayers = new ArrayList<Player>();
		testObject.addStateChangedListener(new Listener() {
			@Override
			public void handle() {
				actualPlayers.addAll(testObject.getSelectedPlayers());
			}
		});

		testObject.setPlayers(expectedPlayers);

		assertEquals(expectedPlayers, actualPlayers);
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
		testObject.setPlayers(Arrays.asList(mock(Player.class), mock(Player.class)));

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetButPlayersAreNotSetThenGameIsNotSetupCompletely() {
		testObject.setGameName(UUID.randomUUID().toString());

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetButNotEnoughPlayersAreSetThenGameIsNotSetupCompletely() {
		testObject.setPlayers(Arrays.asList(mock(Player.class)));
		testObject.setGameName(UUID.randomUUID().toString());

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void ifGameNameIsSetAndEnoughPlayersAreSetThenGameIsSetupCompletely() {
		testObject.setPlayers(Arrays.asList(mock(Player.class), mock(Player.class)));
		testObject.setGameName(UUID.randomUUID().toString());

		assertTrue(testObject.isGameSetupComplete());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void goingToOrderPlayersScreenNavigatesToTheOrderPlayersActivityWithTheCorrectGame() {
		String gameName = UUID.randomUUID().toString();
		testObject.setGameName(gameName);
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		List<Player> players = Arrays.asList(player1, player2, player3);
		testObject.setPlayers(players);

		testObject.goToOrderPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(OrderPlayersActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(gameName, game.getGameName());
		assertEquals(player1, game.getCurrentPlayer());
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
		String gameName = UUID.randomUUID().toString();
		testObject.setGameName(gameName);
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		Player player3 = mock(Player.class);
		List<Player> players = Arrays.asList(player1, player2, player3);
		testObject.setPlayers(players);

		testObject.goToOrderPlayersScreen();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityButDontFinish(eq(OrderPlayersActivity.class),
				extrasCaptor.capture());
		Map<?, ?> extras = extrasCaptor.getValue();

		CurrentGame game = (CurrentGame) extras.get("currentGame");
		assertEquals(gameName, game.getGameName());
		assertEquals(player1, game.getCurrentPlayer());
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
		CurrentGame currentGame = mock(CurrentGame.class);
		doReturn(gameName).when(currentGame).getGameName();
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
		CurrentGame currentGame = mock(CurrentGame.class);
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
		ScoreBoard scoreBoard = mock(ScoreBoard.class);
		ScoreBoardEntry scoreBoardEntry1 = mock(ScoreBoardEntry.class);
		ScoreBoardEntry scoreBoardEntry2 = mock(ScoreBoardEntry.class);
		doReturn(Arrays.asList(scoreBoardEntry1, scoreBoardEntry2)).when(scoreBoard).getEntries();
		CurrentGame currentGame = mock(CurrentGame.class);
		doReturn(scoreBoard).when(currentGame).getScoreBoard();
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertFalse(testObject.isGameSetupComplete());
	}

	@Test
	public void whenACurrentGameIsAvailableAndThereArePlayersAndAGameNameThenGameSetupIsComplete() {
		ScoreBoard scoreBoard = mock(ScoreBoard.class);
		ScoreBoardEntry scoreBoardEntry1 = mock(ScoreBoardEntry.class);
		ScoreBoardEntry scoreBoardEntry2 = mock(ScoreBoardEntry.class);
		doReturn(Arrays.asList(scoreBoardEntry1, scoreBoardEntry2)).when(scoreBoard).getEntries();
		CurrentGame currentGame = mock(CurrentGame.class);
		doReturn(scoreBoard).when(currentGame).getScoreBoard();
		doReturn(UUID.randomUUID().toString()).when(currentGame).getGameName();
		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new SetupGameModel(pageNavigator);

		assertTrue(testObject.isGameSetupComplete());
	}
}
