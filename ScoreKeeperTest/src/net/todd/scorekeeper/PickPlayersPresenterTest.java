package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import java.util.Random;

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
	private Listener cancelButtonListener;
	private Listener selectedPlayersChangedListener;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		PickPlayersPresenter.create(view, model);
		
		ArgumentCaptor<Listener> nextButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addNextButtonListener(nextButtonListenerCaptor.capture());
		nextButtonListener = nextButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> cancelButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addCancelButtonListener(cancelButtonListenerCaptor.capture());
		cancelButtonListener = cancelButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> selectedPlayersChangedListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addSelectedPlayersChangedListener(selectedPlayersChangedListenerCaptor.capture());
		selectedPlayersChangedListener = selectedPlayersChangedListenerCaptor.getValue();
		
		reset(view, model);
	}
	
	@Test
	public void whenNextButtonIsPressedAndLessThanTwoPlayersAreSelectedThenPopupErrorMessageAndDontGoToTheNextPage() {
		doReturn(false).when(model).atLeastTwoPlayersSelected();
		
		nextButtonListener.handle();
		
		verify(model, never()).goToOrderPlayerPage();
		verify(view).popupErrorMessage();
	}
	
	@Test
	public void whenNextButtonIsPressedAndAtLeastThanTwoPlayersAreSelectedThenGoToTheNextPage() {
		doReturn(true).when(model).atLeastTwoPlayersSelected();
		
		nextButtonListener.handle();
		
		verify(view, never()).popupErrorMessage();
		verify(model).goToOrderPlayerPage();
	}
	
	@Test
	public void whenCancelButtonIsPressedThenCancelTheModel() {
		cancelButtonListener.handle();
		
		verify(model).cancel();
	}
	
	@Test
	public void whenSelectionChangesOnTheViewCurrentPlayerAndPlayerIsSelectedArePassedToTheModel() {
		int currentPlayerId = new Random().nextInt();
		doReturn(currentPlayerId).when(view).getCurrentPlayer();
		doReturn(true).when(view).isCurrentPlayerSelected();
		
		selectedPlayersChangedListener.handle();
		
		verify(model).selectionChanged(currentPlayerId, true);
	}
	
	@Test
	public void whenSelectionChangesOnTheViewCurrentPlayerAndPlayerIsNotSelectedArePassedToTheModel() {
		int currentPlayerId = new Random().nextInt();
		doReturn(currentPlayerId).when(view).getCurrentPlayer();
		doReturn(false).when(view).isCurrentPlayerSelected();
		
		selectedPlayersChangedListener.handle();
		
		verify(model).selectionChanged(currentPlayerId, false);
	}
}
