package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import net.todd.scorekeeper.data.CurrentGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class MainPageModelTest {
	@Mock
	private PageNavigator pageNavigator;
	@Mock
	private CurrentGameStore currentGameStore;
	@Mock
	private Context context;

	private MainPageModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new MainPageModel(context, currentGameStore, pageNavigator);
	}

	@Test
	public void goingToManagePlayersPageStartsAnActivityBasedOnTheManagePlayersActivity() {
		testObject.goToManagePlayerPage();

		verify(pageNavigator).navigateToActivityAndFinish(ManagePlayersActivity.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void goingToStartGamePageStartsAnActivityBasedOnTheSetupGameActivity() {
		CurrentGame expectedGame = mock(CurrentGame.class);
		doReturn(expectedGame).when(currentGameStore).getCurrentGame();

		testObject.goToStartGamePage();

		ArgumentCaptor<Map> extrasCaptor = ArgumentCaptor.forClass(Map.class);
		verify(pageNavigator).navigateToActivityAndFinish(eq(SetupGameActivity.class),
				extrasCaptor.capture());
		Map<String, Serializable> extras = extrasCaptor.getValue();

		CurrentGame actualGame = (CurrentGame) extras.get("currentGame");

		assertEquals(expectedGame, actualGame);
	}

	@Test
	public void goingToHistoryPageStartsAnActivityBasedOnHistoryActivity() {
		testObject.goToHistoryPage();

		verify(pageNavigator).navigateToActivityAndFinish(HistoryActivity.class);
	}

	@Test
	public void versionNumberStartsWithTheWorldVersion() throws Exception {
		PackageManager packageManager = mock(PackageManager.class);
		doReturn(packageManager).when(context).getPackageManager();
		String packageName = UUID.randomUUID().toString();
		doReturn(packageName).when(context).getPackageName();
		PackageInfo packageInfo = mock(PackageInfo.class);
		doReturn(packageInfo).when(packageManager).getPackageInfo(eq(packageName), anyInt());

		assertTrue(testObject.getVersion().startsWith("Version "));
	}
}
