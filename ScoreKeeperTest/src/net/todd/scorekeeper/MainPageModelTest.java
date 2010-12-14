package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainPageModelTest {
	@Mock
	private PageNavigator pageNavigator;
	
	private MainPageModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new MainPageModel(pageNavigator);
	}
	
	@Test
	public void goingToManagePlayersPageStartsAnActivityBasedOnTheManagePlayersActivity() {
		testObject.goToManagePlayerPage();

		verify(pageNavigator).navigateToActivity(ManagePlayersActivity.class);
	}
	
	@Test
	public void goingToStartGamePageStartsAnActivityBasedOnTheSetupGameActivity() {
		testObject.goToStartGamePage();
		
		verify(pageNavigator).navigateToActivity(SetupGameActivity.class);
	}
	
	@Test
	public void goingToHistoryPageStartsAnActivityBasedOnHistoryActivity() {
		testObject.goToHistoryPage();
		
		verify(pageNavigator).navigateToActivity(HistoryActivity.class);
	}
}
