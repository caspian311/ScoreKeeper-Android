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

	private Listener selectedPlayersChangedListener;

	private Listener doneButtonListener;
	private Listener backButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		AddPlayersToGamePresenter.create(view, model);

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
		verify(view).addDoneButtonListener(startButtonListenerCaptor.capture());
		doneButtonListener = startButtonListenerCaptor.getValue();

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

		doneButtonListener.handle();

		verify(model, never()).done();
		verify(view).popupErrorMessage();
	}

	@Test
	public void whenNextButtonIsPressedAndAtLeastThanTwoPlayersAreSelectedThenGoToTheNextPage() {
		doReturn(true).when(model).atLeastTwoPlayersSelected();

		doneButtonListener.handle();

		verify(model).done();
		verify(view, never()).popupErrorMessage();
	}

	@Test
	public void whenBackButtonIsPressedThenCancelTheModel() {
		backButtonListener.handle();

		verify(model).done();
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
}
