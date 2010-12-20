package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainPageModelTest {
	@Mock
	private PageNavigator pageNavigator;
	@Mock
	private CurrentGameStore currentGameStore;

	private MainPageModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new MainPageModel(currentGameStore, pageNavigator);
	}

	@Test
	public void goingToManagePlayersPageStartsAnActivityBasedOnTheManagePlayersActivity() {
		testObject.goToManagePlayerPage();

		verify(pageNavigator).navigateToActivity(ManagePlayersActivity.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void goingToStartGamePageStartsAnActivityBasedOnTheSetupGameActivity() {
		CurrentGame expectedGame = mock(CurrentGame.class);
		doReturn(expectedGame).when(currentGameStore).getCurrentGame();

		testObject.goToStartGamePage();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivity(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualGame = (CurrentGame) extras.get("currentGame");

		assertEquals(expectedGame, actualGame);
	}

	@Test
	public void goingToHistoryPageStartsAnActivityBasedOnHistoryActivity() {
		testObject.goToHistoryPage();

		verify(pageNavigator).navigateToActivity(HistoryActivity.class);
	}
}
