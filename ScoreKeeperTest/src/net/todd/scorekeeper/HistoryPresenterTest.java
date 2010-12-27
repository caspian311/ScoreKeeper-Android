package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HistoryPresenterTest {
	@Mock
	private HistoryView view;
	@Mock
	private HistoryModel model;

	private Listener backPressedListener;
	private Listener donePressedListener;
	private Listener clearButtonPressedListener;
	private Listener clearHistoryConfirmedListener;
	private Listener historyChangedListener;
	private Listener clearGameConfirmationListener;
	private Listener clearGameButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		HistoryPresenter.create(view, model);

		ArgumentCaptor<Listener> backPressedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addBackPressedListener(backPressedListenerCaptor.capture());
		backPressedListener = backPressedListenerCaptor.getValue();

		ArgumentCaptor<Listener> donePressedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addDonePressedListener(donePressedListenerCaptor.capture());
		donePressedListener = donePressedListenerCaptor.getValue();

		ArgumentCaptor<Listener> clearButtonPressedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addClearButtonPressedListener(clearButtonPressedListenerCaptor.capture());
		clearButtonPressedListener = clearButtonPressedListenerCaptor.getValue();

		ArgumentCaptor<Listener> clearHistoryConfirmedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addClearHistoryConfirmationListener(
				clearHistoryConfirmedListenerCaptor.capture());
		clearHistoryConfirmedListener = clearHistoryConfirmedListenerCaptor.getValue();

		ArgumentCaptor<Listener> historyChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addHistoryChangedListener(historyChangedListenerCaptor.capture());
		historyChangedListener = historyChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> clearHistoryConfirmationListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addClearGameConfirmationListener(
				clearHistoryConfirmationListenerCaptor.capture());
		clearGameConfirmationListener = clearHistoryConfirmationListenerCaptor.getValue();

		ArgumentCaptor<Listener> clearGameButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addClearGameButtonPressedListener(clearGameButtonListenerCaptor.capture());
		clearGameButtonListener = clearGameButtonListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void whenBackIsPressedModelFinishesTheActivity() {
		backPressedListener.handle();

		verify(model).finish();
	}

	@Test
	public void whenDoneIsPressedModelFinishesTheActivity() {
		donePressedListener.handle();

		verify(model).finish();
	}

	@Test
	public void getAllGamesFromModelAndDisplayThemInitially() {
		List<Game> games = Arrays.asList(mock(Game.class), mock(Game.class), mock(Game.class));
		doReturn(games).when(model).getAllGames();

		HistoryPresenter.create(view, model);

		verify(view).setHistory(games);
	}

	@Test
	public void whenClearButtonIsPressedViewShowsTheConfirmDialog() {
		clearButtonPressedListener.handle();

		verify(view).confirmClearingHistory();
	}

	@Test
	public void whenConfirmationIsGivenThenClearTheHistory() {
		clearHistoryConfirmedListener.handle();

		verify(model).clearHistory();
	}

	@Test
	public void whenHistoryChangesThenRepopulateTheHistoryOnTheView() {
		List<Game> allGames = Arrays.asList(mock(Game.class), mock(Game.class), mock(Game.class));
		doReturn(allGames).when(model).getAllGames();

		historyChangedListener.handle();

		verify(view).setHistory(allGames);
	}

	@Test
	public void whenConfirmationIsGivenToClearTheGameThenRemoveTheSelectedGame() {
		Game selectedGame = mock(Game.class);
		doReturn(selectedGame).when(view).getSelectedGame();

		clearGameConfirmationListener.handle();

		verify(model).removeGame(selectedGame);
	}

	@Test
	public void whenClearGameButtonIsPressedConfirmTheClearing() {
		clearGameButtonListener.handle();

		verify(view).confirmClearingGame();
	}

	@Test
	public void initiallyIfThereAreGamesInHistoryThenEnableTheClearButton() {
		doReturn(true).when(model).areThereGamesInHistory();

		HistoryPresenter.create(view, model);

		verify(view).setClearButtonEnabled(true);
	}

	@Test
	public void initiallyIfThereAreNoGamesInHistoryThenDisableTheClearButton() {
		doReturn(false).when(model).areThereGamesInHistory();

		HistoryPresenter.create(view, model);

		verify(view).setClearButtonEnabled(false);
	}

	@Test
	public void whenHistoryHasChangedAndThereAreGamesInHistoryThenEnableTheClearButton() {
		doReturn(true).when(model).areThereGamesInHistory();

		historyChangedListener.handle();

		verify(view).setClearButtonEnabled(true);
	}

	@Test
	public void whenHistoryHasChangedAndThereAreNoGamesInHistoryThenDisableTheClearButton() {
		doReturn(false).when(model).areThereGamesInHistory();

		historyChangedListener.handle();

		verify(view).setClearButtonEnabled(false);
	}
}
