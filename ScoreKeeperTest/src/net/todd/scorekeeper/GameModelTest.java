package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Game;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameModelTest {
	@Mock
	private GameStore gameStore;
	@Mock
	private PageNavigator pageNavigator;

	private CurrentGame currentGame;

	@Mock
	private Player player1;
	@Mock
	private Player player2;
	@Mock
	private Player player3;

	private GameModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(Arrays.asList(player1, player2, player3));
		currentGame = new CurrentGame();
		currentGame.setGameName(UUID.randomUUID().toString());
		currentGame.setCurrentPlayer(player2);
		currentGame.setScoreBoard(scoreBoard);

		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new GameModel(gameStore, pageNavigator);
	}

	@Test
	public void everyTimeYouCallNextPlayerYouGetTheNextPlayer() {
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player3, testObject.getCurrentPlayer());
		testObject.nextPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
	}

	@Test
	public void everyTimeYouCallPreviousPlayerYouGetThePreviousPlayer() {
		assertSame(player2, testObject.getCurrentPlayer());
		testObject.previousPlayer();
		assertSame(player1, testObject.getCurrentPlayer());
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
	public void afterSettingPlayersScoresTheScoreBoardContainsAllScores() {
		int firstScore = new Random().nextInt();
		int secondScore = new Random().nextInt();
		int thirdScore = new Random().nextInt();

		testObject.setScoreForCurrentPlayer(firstScore);
		testObject.nextPlayer();

		testObject.setScoreForCurrentPlayer(secondScore);
		testObject.nextPlayer();

		testObject.setScoreForCurrentPlayer(thirdScore);
		testObject.nextPlayer();

		ScoreBoard scoreBoard = testObject.getScoreBoard();
		assertEquals(firstScore, scoreBoard.getScore(player2));
		assertEquals(secondScore, scoreBoard.getScore(player3));
		assertEquals(thirdScore, scoreBoard.getScore(player1));
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
	public void cancellingTheGameGoesToTheMainPage() {
		testObject.cancelGame();

		verify(pageNavigator).navigateToActivityAndFinish(MainPageActivity.class);
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

	@Test
	public void whenGameIsOverSaveGameThenFinishActivity() {
		testObject.gameOver();

		InOrder inOrder = inOrder(pageNavigator, gameStore);
		inOrder.verify(gameStore).addGame(any(Game.class));
		inOrder.verify(pageNavigator).navigateToActivityAndFinish(MainPageActivity.class);
	}

	@Test
	public void gameThatIsSavedHasCurrentTimestamp() {
		testObject.gameOver();

		ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
		verify(gameStore).addGame(gameCaptor.capture());
		Game game = gameCaptor.getValue();

		assertEquals(new Date().getTime(), game.getGameOverTimestamp().getTime(), 500);
	}

	@Test
	public void gameThatIsSavedHasGameTypeFromIntent() {
		testObject.gameOver();

		ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
		verify(gameStore).addGame(gameCaptor.capture());
		Game game = gameCaptor.getValue();

		assertEquals(currentGame.getGameName(), game.getGameName());
	}

	@Test
	public void gameThatIsSavedHasScoreBoard() {
		testObject.gameOver();

		ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
		verify(gameStore).addGame(gameCaptor.capture());
		Game game = gameCaptor.getValue();

		ScoreBoard scoreBoard = game.getScoreBoard();

		assertSame(testObject.getScoreBoard(), scoreBoard);
	}

	@Test
	public void notifyListenersOfCancellation() {
		Listener listener = mock(Listener.class);
		testObject.addCancelGameListener(listener);

		testObject.cancelGame();

		verify(listener).handle();
	}

	@Test
	public void notifyListenersOfGameOver() {
		Listener listener = mock(Listener.class);
		testObject.addGameOverListener(listener);

		testObject.gameOver();

		verify(listener).handle();
	}

	@Test
	public void ifNoCurrentPlayerWasGivenThenAssumeFirstPlayerIsCurrentPlayer() {
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(Arrays.asList(player1, player2, player3));
		currentGame = new CurrentGame();
		currentGame.setScoreBoard(scoreBoard);

		doReturn(currentGame).when(pageNavigator).getExtra("currentGame");

		testObject = new GameModel(gameStore, pageNavigator);

		assertEquals(player1, testObject.getCurrentPlayer());
	}

	@Test
	public void gameNameIsPulledFromCurrentGameGivenInExtras() {
		assertEquals(currentGame.getGameName(), testObject.getGameName());
	}
}
