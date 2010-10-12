package net.todd.scorekeeper.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.todd.scorekeeper.GameModel;
import net.todd.scorekeeper.GamePresenter;
import net.todd.scorekeeper.GameView;
import net.todd.scorekeeper.Listener;
import net.todd.scorekeeper.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GamePresenterTest {
	@Mock
	private GameView view;
	@Mock
	private GameModel model;
	private Listener nextPlayerButtonListener;
	private Listener previousPlayerButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		GamePresenter.create(view, model);

		ArgumentCaptor<Listener> nextPlayerButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addNextPlayerButtonListener(nextPlayerButtonListenerCaptor.capture());
		nextPlayerButtonListener = nextPlayerButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> previousPlayerButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addPreviousPlayerButtonListener(previousPlayerButtonListenerCaptor.capture());
		previousPlayerButtonListener = previousPlayerButtonListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void currentPlayerIsSetInitiallyToNextPlayerFromModel() {
		Player player = mock(Player.class);
		doReturn(player).when(model).getNextPlayer();
		int score = new Random().nextInt();
		doReturn(score).when(model).getCurrentPlayersScore();

		GamePresenter.create(view, model);

		verify(view).setCurrentPlayer(player, score);
	}

	@Test
	public void scoreBoardIsSetInitiallyFromModel() {
		@SuppressWarnings("unchecked")
		Map<Player, Integer> scoreBoard = mock(HashMap.class);
		doReturn(scoreBoard).when(model).getScoreBoard();

		GamePresenter.create(view, model);

		verify(view).setScoreBoard(scoreBoard);
	}

	@Test
	public void scoreBoardIsUpdatedFromModelWhenNextPlayerButtonIsPressed() {
		@SuppressWarnings("unchecked")
		Map<Player, Integer> scoreBoard = mock(HashMap.class);
		doReturn(scoreBoard).when(model).getScoreBoard();

		nextPlayerButtonListener.handle();

		verify(view).setScoreBoard(scoreBoard);
	}

	@Test
	public void scoreTextFieldIsClearedWhenNextPlayerButtonIsPressed() {
		nextPlayerButtonListener.handle();

		verify(view).clearScore();
	}

	@Test
	public void currentPlayerIsSetWhenNextPlayerButtonIsPressed() {
		Player player = mock(Player.class);
		doReturn(player).when(model).getNextPlayer();
		int score = new Random().nextInt();
		doReturn(score).when(model).getCurrentPlayersScore();

		nextPlayerButtonListener.handle();

		verify(view).setCurrentPlayer(player, score);
	}

	@Test
	public void ensureNextPlayerIsSetBeforeGettingTheCurrentPlayersScoreWhenNextPlayerButtonIsPressed() {
		nextPlayerButtonListener.handle();

		InOrder inOrder = inOrder(model);
		inOrder.verify(model).getNextPlayer();
		inOrder.verify(model).getCurrentPlayersScore();
	}
	
	@Test
	public void currentPlayersScoreIsCollectedWhenNextPlayerButtonIsPressed() {
		int score = new Random().nextInt();
		doReturn(score).when(view).getScore();

		nextPlayerButtonListener.handle();

		verify(model).setScoreForCurrentPlayer(score);
	}
	
	@Test
	public void currentPlayersScoreIsCollectedBeforeClearingTheScoreAndSwitchingToTheNextPlayerWhenNextPlayerButtonIsPressed() {
		nextPlayerButtonListener.handle();

		InOrder inOrder = inOrder(view, model);
		inOrder.verify(view).getScore();
		inOrder.verify(view).clearScore();
		inOrder.verify(model).getNextPlayer();
	}
	
	@Test
	public void clearScoreWhenPreviousPlayerButtonIsPressed() {
		previousPlayerButtonListener.handle();

		verify(view).clearScore();
	}
	
	@Test
	public void setCurrentPlayerToPreviousPlayerWhenPreviousPlayerButtonIsPressed() {
		Player player = mock(Player.class);
		doReturn(player).when(model).getPreviousPlayer();
		int score = new Random().nextInt();
		doReturn(score).when(model).getCurrentPlayersScore();
		
		previousPlayerButtonListener.handle();

		verify(view).setCurrentPlayer(player, score);
	}
	
	@Test
	public void ensureClearScoreFirstThenSetPlayerThenSetScoreWhenPreviousPlayerButtonIsPressed() {
		previousPlayerButtonListener.handle();

		InOrder inOrder = inOrder(view, model);
		inOrder.verify(view).clearScore();
		inOrder.verify(model).getPreviousPlayer();
		inOrder.verify(model).getCurrentPlayersScore();
		inOrder.verify(view).setCurrentPlayer(any(Player.class), anyInt());
	}
}
