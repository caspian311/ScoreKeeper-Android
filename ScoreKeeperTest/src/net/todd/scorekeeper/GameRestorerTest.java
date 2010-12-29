package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Map;

import net.todd.scorekeeper.data.CurrentGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameRestorerTest {
	@Mock
	private CurrentGameStore currentStateStore;
	@Mock
	private PageNavigator pageNavigator;

	private GameRestorer testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new GameRestorer(currentStateStore, pageNavigator);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void createAnIdentityIfNoIdentityWasFound() {
		CurrentGame currentGame = mock(CurrentGame.class);
		doReturn(currentGame).when(currentStateStore).getCurrentGame();

		testObject.restoreGameInProgress();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivity(eq(GameActivity.class), extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		assertEquals(currentGame, extras.get("currentGame"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void ifGameIsNotInProgressDontGoToTheGamePage() {
		doReturn(null).when(currentStateStore).getCurrentGame();

		testObject.restoreGameInProgress();

		verify(pageNavigator, never()).navigateToActivity(eq(GameActivity.class), anyMap());
	}
}
