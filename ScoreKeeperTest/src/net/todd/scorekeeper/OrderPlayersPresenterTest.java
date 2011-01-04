package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.scorekeeper.data.Player;

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

	private Listener doneButtonListener;
	private Listener upButtonListener;
	private Listener downButtonListener;
	private Listener playersOrderChangedListener;
	private Listener backButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		OrderPlayersPresenter.create(view, model);

		ArgumentCaptor<Listener> cancelButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addDoneButtonListener(cancelButtonListenerCaptor.capture());
		doneButtonListener = cancelButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> backButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addBackPressedListener(backButtonListenerCaptor.capture());
		backButtonListener = backButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> upButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addUpButtonListener(upButtonListenerCaptor.capture());
		upButtonListener = upButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> downButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addDownButtonListener(downButtonListenerCaptor.capture());
		downButtonListener = downButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> playersOrderChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addPlayersOrderChangedListener(playersOrderChangedListenerCaptor.capture());
		playersOrderChangedListener = playersOrderChangedListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void initiallyAllPlayersAreSetOnTheView() {
		Player player1 = new Player();
		player1.setId("1");
		player1.setName("Peter");
		Player player2 = new Player();
		player2.setId("2");
		player2.setName("James");
		Player player3 = new Player();
		player3.setId("3");
		player3.setName("John");
		List<Player> allPlayers = new ArrayList<Player>(Arrays.asList(player1, player2, player3));
		doReturn(allPlayers).when(model).getAllPlayers();

		OrderPlayersPresenter.create(view, model);

		verify(view).setAllPlayers(allPlayers);
	}

	@Test
	public void whenCancelButtonIsPressedThenModelIsDone() {
		doneButtonListener.handle();

		verify(model).done();
	}

	@Test
	public void whenBackButtonIsPressedThenModelIsDone() {
		backButtonListener.handle();

		verify(model).done();
	}

	@Test
	public void whenUpIsPressedMoveTheCurrentPlayerUp() {
		String currentPlayerId = UUID.randomUUID().toString();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();

		upButtonListener.handle();

		verify(model).movePlayerUp(currentPlayerId);
	}

	@Test
	public void whenDownIsPressedMoveTheCurrentPlayerDown() {
		String currentPlayerId = UUID.randomUUID().toString();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();

		downButtonListener.handle();

		verify(model).movePlayerDown(currentPlayerId);
	}

	@Test
	public void whenPlayersOrderChangesThenUpdateViewWithlistOfPlayersFromModel() {
		List<Player> players = Arrays.asList(mock(Player.class), mock(Player.class),
				mock(Player.class));
		doReturn(players).when(model).getAllPlayers();

		playersOrderChangedListener.handle();

		verify(view).setAllPlayers(players);
	}
}
