package net.todd.scorekeeper;

import static org.mockito.Mockito.*;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameWatcherTest {
	@Mock
	private GameModel model;
	@Mock
	private CurrentGameStore currentGameStore;

	private Listener playerChangedListener;
	private Listener gameOverListener;
	private Listener cancellationListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		GameWatcher.create(model, currentGameStore);

		ArgumentCaptor<Listener> playerChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addPlayerChangedListener(playerChangedListenerCaptor.capture());
		playerChangedListener = playerChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> gameOverListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(model).addGameOverListener(gameOverListenerCaptor.capture());
		gameOverListener = gameOverListenerCaptor.getValue();

		ArgumentCaptor<Listener> cancellationListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addCancelGameListener(cancellationListenerCaptor.capture());
		cancellationListener = cancellationListenerCaptor.getValue();

		reset(model, currentGameStore);
	}

	@Test
	public void whenGameStartsNotifyTheCurrentStateStoreWithCorrectInformation() {
		ScoreBoard scoreBoard = mock(ScoreBoard.class);
		doReturn(scoreBoard).when(model).getScoreBoard();
		Player currentPlayer = mock(Player.class);
		doReturn(currentPlayer).when(model).getCurrentPlayer();

		GameWatcher.create(model, currentGameStore);

		verify(currentGameStore).saveState(scoreBoard, currentPlayer);
	}

	@Test
	public void whenPlayerChangesUpdateTheGamesCurrentState() {
		ScoreBoard scoreBoard = mock(ScoreBoard.class);
		doReturn(scoreBoard).when(model).getScoreBoard();
		Player currentPlayer = mock(Player.class);
		doReturn(currentPlayer).when(model).getCurrentPlayer();

		playerChangedListener.handle();

		verify(currentGameStore).saveState(scoreBoard, currentPlayer);
	}

	@Test
	public void whenGamesIsOverThenTheCurrentStateIsCleared() {
		gameOverListener.handle();

		verify(currentGameStore).clearState();
	}

	@Test
	public void whenGamesIsCancelledThenTheCurrentStateIsCleared() {
		cancellationListener.handle();

		verify(currentGameStore).clearState();
	}
}
