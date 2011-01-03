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

public class AddPlayersToGamePresenterTest {
	@Mock
	private AddPlayersToGameView view;
	@Mock
	private AddPlayersToGameModel model;

	private Listener cancelButtonListener;
	private Listener selectedPlayersChangedListener;

	private Listener startButtonListener;
	private Listener upButtonListener;
	private Listener downButtonListener;
	private Listener playersOrderChangedListener;
	private Listener backButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		AddPlayersToGamePresenter.create(view, model);

		ArgumentCaptor<Listener> cancelButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addCancelButtonListener(cancelButtonListenerCaptor.capture());
		cancelButtonListener = cancelButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> backButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addBackPressedListener(backButtonListenerCaptor.capture());
		backButtonListener = backButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> selectedPlayersChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addSelectedPlayersChangedListener(
				selectedPlayersChangedListenerCaptor.capture());
		selectedPlayersChangedListener = selectedPlayersChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> startButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addStartGameButtonListener(startButtonListenerCaptor.capture());
		startButtonListener = startButtonListenerCaptor.getValue();

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

		AddPlayersToGamePresenter.create(view, model);

		verify(view).setAllPlayers(allPlayers);
	}

	@Test
	public void whenStartButtonIsPressedStartTheGame() {
		doReturn(false).when(model).atLeastTwoPlayersSelected();

		startButtonListener.handle();

		verify(model, never()).startGame();
		verify(view).popupErrorMessage();
	}

	@Test
	public void whenNextButtonIsPressedAndAtLeastThanTwoPlayersAreSelectedThenGoToTheNextPage() {
		doReturn(true).when(model).atLeastTwoPlayersSelected();

		startButtonListener.handle();

		verify(model).startGame();
		verify(view, never()).popupErrorMessage();
	}

	@Test
	public void whenCancelButtonIsPressedThenCancelTheModel() {
		cancelButtonListener.handle();

		verify(model).cancel();
	}

	@Test
	public void whenBackButtonIsPressedThenCancelTheModel() {
		backButtonListener.handle();

		verify(model).cancel();
	}

	@Test
	public void whenSelectionChangesOnTheViewCurrentPlayerAndPlayerIsSelectedArePassedToTheModel() {
		String currentPlayerId = UUID.randomUUID().toString();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();
		doReturn(true).when(view).isCurrentPlayerSelected();

		selectedPlayersChangedListener.handle();

		verify(model).selectionChanged(currentPlayerId, true);
	}

	@Test
	public void whenSelectionChangesOnTheViewCurrentPlayerAndPlayerIsNotSelectedArePassedToTheModel() {
		String currentPlayerId = UUID.randomUUID().toString();
		doReturn(currentPlayerId).when(view).getCurrentPlayerId();
		doReturn(false).when(view).isCurrentPlayerSelected();

		selectedPlayersChangedListener.handle();

		verify(model).selectionChanged(currentPlayerId, false);
	}

	@Test
	public void whenBackButtonIsPressedCancelTheModel() {
		cancelButtonListener.handle();

		verify(model).cancel();
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
