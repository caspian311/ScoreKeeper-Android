package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.todd.scorekeeper.data.Game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HistoryModelTest {
	@Mock
	private GameStore gameStore;
	@Mock
	private PageNavigator pageNavigator;

	private HistoryModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new HistoryModel(gameStore, pageNavigator);
	}

	@Test
	public void whenModelFinishesGoToMainPage() {
		testObject.finish();

		verify(pageNavigator).navigateToActivityAndFinish(MainPageActivity.class);
	}

	@Test
	public void allGamesReturnsAllGamesFromTheGameStoreInOrderWithTheNewsetGameFirstAndOldestGameLast() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2010, 7, 15);
		Date date1 = calendar.getTime();

		calendar.set(2010, 2, 15);
		Date date2 = calendar.getTime();

		calendar.set(2010, 10, 15);
		Date date3 = calendar.getTime();

		Game game1 = new Game();
		game1.setGameOverTimestamp(date1);
		Game game2 = new Game();
		game2.setGameOverTimestamp(date2);
		Game game3 = new Game();
		game3.setGameOverTimestamp(date3);

		List<Game> expectedGames = Arrays.asList(game1, game2, game3);
		doReturn(expectedGames).when(gameStore).getAllGames();

		assertEquals(Arrays.asList(game3, game1, game2), testObject.getAllGames());
	}

	@Test
	public void clearingHistoryDeletesGamesFromGameStoreThenNotifiesTheHistoryChangedListeners() {
		Listener listener = mock(Listener.class);
		testObject.addHistoryChangedListener(listener);

		testObject.clearHistory();

		InOrder inOrder = inOrder(gameStore, listener);
		inOrder.verify(gameStore).clearAllGames();
		inOrder.verify(listener).handle();
	}

	@Test
	public void removingAGameDeletesThatGameFromGameStoreThenNotifiesTheHistoryChangedListeners() {
		Listener listener = mock(Listener.class);
		testObject.addHistoryChangedListener(listener);

		Game selectedGame = mock(Game.class);
		testObject.removeGame(selectedGame);

		InOrder inOrder = inOrder(gameStore, listener);
		inOrder.verify(gameStore).deleteGame(selectedGame);
		inOrder.verify(listener).handle();
	}

	@Test
	public void thereAreNoGamesInHistoryIfTheStoreHasNoGames() {
		doReturn(Arrays.asList()).when(gameStore).getAllGames();

		assertFalse("there should not be games in history", testObject.areThereGamesInHistory());
	}

	@Test
	public void thereAreGamesInHistoryIfTheStoreHasGames() {
		doReturn(Arrays.asList(mock(Game.class))).when(gameStore).getAllGames();

		assertTrue("there should be games in history", testObject.areThereGamesInHistory());
	}
}
