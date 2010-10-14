package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderPlayersPresenterTest {
	@Mock
	private OrderPlayersView view;
	@Mock
	private OrderPlayersModel model;
	
	private Listener backButtonListener;
	private Listener startButtonListener;
	private Listener upButtonListener;
	private Listener downButtonListener;
	private Listener playersOrderChangedListener;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		OrderPlayersPresenter.create(view, model);
		
		ArgumentCaptor<Listener> backButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addBackButtonListener(backButtonListenerCaptor.capture());
		backButtonListener = backButtonListenerCaptor.getValue();
		
		ArgumentCaptor<Listener> startButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addStartGameButtonListener(startButtonListenerCaptor.capture());
		startButtonListener = startButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> upButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addUpButtonListener(upButtonListenerCaptor.capture());
		upButtonListener = upButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> downButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addDownButtonListener(downButtonListenerCaptor.capture());
		downButtonListener = downButtonListenerCaptor.getValue();
		
		ArgumentCaptor<Listener> playersOrderChangedListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(model).addPlayersOrderChangedListener(playersOrderChangedListenerCaptor.capture());
		playersOrderChangedListener = playersOrderChangedListenerCaptor.getValue();

		reset(view, model);
	}
	
	@Test
	public void whenBackButtonIsPressedCancelTheModel() {
		backButtonListener.handle();
		
		verify(model).cancel();
	}
	
	@Test
	public void whenStartButtonIsPressedStartTheGame() {
		startButtonListener.handle();
		
		verify(model).startGame();
	}
	
	@Test
	public void whenUpIsPressedMoveTheCurrentPlayerUp() {
		int currentPlayerId = new Random().nextInt();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();
		
		upButtonListener.handle();
		
		verify(model).movePlayerUp(currentPlayerId);
	}
	
	@Test
	public void whenDownIsPressedMoveTheCurrentPlayerDown() {
		int currentPlayerId = new Random().nextInt();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();
		
		downButtonListener.handle();
		
		verify(model).movePlayerDown(currentPlayerId);
	}
	
	@Test
	public void whenPlayersOrderChangesThenUpdateViewWithlistOfPlayersFromModel() {
		List<Player> players = Arrays.asList(mock(Player.class), mock(Player.class), mock(Player.class));
		doReturn(players).when(model).getSelectedPlayers();
		
		playersOrderChangedListener.handle();
		
		verify(view).setPlayers(players);
	}
}
