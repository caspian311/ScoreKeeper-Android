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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		HistoryPresenter.create(view, model);

		ArgumentCaptor<Listener> backPressedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addBackPressedListener(backPressedListenerCaptor.capture());
		backPressedListener = backPressedListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void whenBackIsPressedModelFinishesTheActivity() {
		backPressedListener.handle();

		verify(model).finish();
	}

	@Test
	public void getAllGamesFromModelAndDisplayThemInitially() {
		List<Game> games = Arrays.asList(mock(Game.class), mock(Game.class), mock(Game.class));
		doReturn(games).when(model).getAllGames();

		HistoryPresenter.create(view, model);

		verify(view).setHistory(games);
	}
}
