package net.todd.scorekeeper;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.scorekeeper.data.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ManagePlayersPresenterTest {
	@Mock
	private ManagePlayersView view;
	@Mock
	private ManagePlayersModel model;

	private Listener addPlayerButtonListener;
	private Listener removePlayerButtonListener;
	private Listener playerChangedListener;
	private Listener doneButtonListener;
	private Listener backButtonListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		ManagePlayersPresenter.create(view, model);

		ArgumentCaptor<Listener> addPlayerButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addAddPlayerButtonListener(addPlayerButtonListenerCaptor.capture());
		addPlayerButtonListener = addPlayerButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> removePlayerButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addRemovePlayerButtonListener(removePlayerButtonListenerCaptor.capture());
		removePlayerButtonListener = removePlayerButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> playerChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addPlayerChangedListener(playerChangedListenerCaptor.capture());
		playerChangedListener = playerChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> doneButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addDoneButtonListener(doneButtonListenerCaptor.capture());
		doneButtonListener = doneButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> backButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addBackPressedListener(backButtonListenerCaptor.capture());
		backButtonListener = backButtonListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void allPlayersAreDisplayedInitially() {
		List<Player> allPlayers = Arrays.asList(mock(Player.class), mock(Player.class));
		doReturn(allPlayers).when(model).getPlayers();

		ManagePlayersPresenter.create(view, model);

		verify(view).setPlayers(allPlayers);
	}

	@Test
	public void whenAddButtonPressedThePlayerNameIsGivenToTheModel() {
		String playerName = UUID.randomUUID().toString();
		doReturn(playerName).when(view).getPlayerNameText();

		addPlayerButtonListener.handle();

		verify(model).addPlayer(playerName);
	}

	@Test
	public void whenAddButtonPressedClearThePlayerNameAfterTheModelHasReceivedIt() {
		addPlayerButtonListener.handle();

		InOrder inorder = inOrder(view, model);
		inorder.verify(model).addPlayer(anyString());
		inorder.verify(view).clearPlayerNameText();
	}

	@Test
	public void whenRemoveButtonPressedThenModelRemovesPlayerThatViewIndicates() {
		String playerToRemove = UUID.randomUUID().toString();
		doReturn(playerToRemove).when(view).getPlayerToRemove();

		removePlayerButtonListener.handle();

		verify(model).removePlayer(playerToRemove);
	}

	@Test
	public void whenThePlayersHaveChangedInTheModelThenUpdateTheView() {
		List<Player> allPlayers = Arrays.asList(mock(Player.class), mock(Player.class));
		doReturn(allPlayers).when(model).getPlayers();

		playerChangedListener.handle();

		verify(view).setPlayers(allPlayers);
	}

	@Test
	public void whenDoneButtonPressedThenModelIsToldToFinish() {
		doneButtonListener.handle();

		verify(model).finish();
	}

	@Test
	public void whenBackButtonPressedThenModelIsToldToFinish() {
		backButtonListener.handle();

		verify(model).finish();
	}
}
