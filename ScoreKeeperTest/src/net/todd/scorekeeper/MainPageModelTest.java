package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.content.Intent;

public class MainPageModelTest {
	@Mock
	private Activity activity;
	@Mock
	private IntentFactory intentFactory;
	
	private MainPageModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new MainPageModel(activity, intentFactory);
	}
	
	@Test
	public void goingToManagePlayersPageStartsAnActivityBasedOnTheManagePlayersActivity() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, ManagePlayersActivity.class);
		
		testObject.goToManagePlayerPage();
		
		verify(activity).startActivity(intent);
	}
	
	@Test
	public void goingToStartGamePageStartsAnActivityBasedOnTheSetupGameActivity() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, SetupGameActivity.class);
		
		testObject.goToStartGamePage();
		
		verify(activity).startActivity(intent);
	}
	
	@Test
	public void goingToHistoryPageStartsAnActivityBasedOnHistoryActivity() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, HistoryActivity.class);
		
		testObject.goToHistoryPage();
		
		verify(activity).startActivity(intent);
	}
	
	@Test
	public void whenGoingToHistoryPageClearActivityHistory() {
		Intent intent = mock(Intent.class);
		doReturn(intent).when(intentFactory).createIntent(activity, HistoryActivity.class);
		
		testObject.goToHistoryPage();

		verify(intent).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	}
}
