package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
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

		verify(pageNavigator).navigateToActivity(MainPageActivity.class);
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
}
