package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.content.Intent;

public class HistoryModelTest {
	@Mock
	private Activity activity;
	@Mock
	private GameStore gameStore;
	@Mock
	private IntentFactory intentFactory;

	private HistoryModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new HistoryModel(activity, gameStore, intentFactory);
	}

	@Test
	public void whenModelFinishesGoToMainPage() {
		Intent expectedIntent = mock(Intent.class);
		doReturn(expectedIntent).when(intentFactory).createIntent(activity, MainPageActivity.class);
		
		testObject.finish();

		ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
		verify(activity).startActivity(intentCaptor.capture());
		Intent actualIntent = intentCaptor.getValue();
		
		assertSame(expectedIntent, actualIntent);
	}
	
	@Test
	public void whenGoingBackToMainPageClearActivityHistory() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, MainPageActivity.class);
		
		testObject.finish();

		verify(intent).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
