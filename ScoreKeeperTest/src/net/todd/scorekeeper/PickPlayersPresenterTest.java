package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PickPlayersPresenterTest {
	@Mock
	private PickPlayersView view;
	@Mock
	private PickPlayersModel model;
	
	private Listener nextButtonListener;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		PickPlayersPresenter.create(view, model);
		
		ArgumentCaptor<Listener> nextButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addNextButtonListener(nextButtonListenerCaptor.capture());
		nextButtonListener = nextButtonListenerCaptor.getValue();
		
		reset(view, model);
	}
	
	@Test
	public void ifLessThanTwoPlayersAreSelectedThenPopupErrorMessageAndDontGoToTheNextPage() {
		doReturn(false).when(model).atLeastTwoPlayersSelected();
		
		nextButtonListener.handle();
		
		verify(model, never()).goToOrderPlayerPage();
		verify(view).popupErrorMessage();
	}
	
	@Test
	public void ifAtLeastThanTwoPlayersAreSelectedThenGoToTheNextPage() {
		doReturn(true).when(model).atLeastTwoPlayersSelected();
		
		nextButtonListener.handle();
		
		verify(view, never()).popupErrorMessage();
		verify(model).goToOrderPlayerPage();
	}
}
