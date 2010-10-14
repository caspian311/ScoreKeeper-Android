package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ListenerManagerTest {
	@Mock
	private Listener listener1;
	@Mock
	private Listener listener2;
	@Mock
	private Listener listener3;
	
	private ListenerManager testObject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new ListenerManager();
	}
	
	@Test
	public void addedListenersAreNotifiedInOrder() {
		testObject.addListener(listener1);
		testObject.addListener(listener2);
		testObject.addListener(listener3);
		
		testObject.notifyListeners();
		
		InOrder inOrder = inOrder(listener1, listener2, listener3);
		inOrder.verify(listener1).handle();
		inOrder.verify(listener2).handle();
		inOrder.verify(listener3).handle();
	}
	
	@Test
	public void removedListenersAreNotNotified() {
		testObject.addListener(listener1);
		testObject.addListener(listener2);
		testObject.addListener(listener3);
		testObject.removeListener(listener2);
		
		testObject.notifyListeners();
		
		verify(listener2, never()).handle();
	}
	
	@Test
	public void addingListenerTwiceButStillOnlyNotifiedOnce() {
		testObject.addListener(listener1);
		testObject.addListener(listener1);
		
		testObject.notifyListeners();
		
		verify(listener1).handle();
	}
	
	@Test
	public void removingListenersThatWereNeverAddedDoesNotCauseIssues() {
		testObject.addListener(listener1);
		testObject.removeListener(listener2);
		
		testObject.notifyListeners();
		
		verify(listener1).handle();
	}
	
	@Test
	public void notifyingListenersMultipleTimesNotifiesAllAddedListenersMultipleTimes() {
		testObject.addListener(listener1);
		testObject.addListener(listener2);
		
		testObject.notifyListeners();
		testObject.notifyListeners();
		testObject.notifyListeners();
		
		verify(listener1, times(3)).handle();
		verify(listener2, times(3)).handle();
	}
}
