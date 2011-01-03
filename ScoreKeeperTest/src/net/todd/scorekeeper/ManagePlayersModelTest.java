package net.todd.scorekeeper;

import static org.junit.Assert.*;
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

public class ManagePlayersModelTest {
	@Mock
	private PlayerStore playerStore;
	@Mock
	private PageNavigator pageNavigator;

	private ManagePlayersModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new ManagePlayersModel(playerStore, pageNavigator);
	}

	@Test
	public void finishingTheModelFinishesTheActivity() {
		testObject.finish();

		verify(pageNavigator).navigateToActivityAndFinish(MainPageActivity.class);
	}

	@Test
	public void getPlayersPullsFromThePlayerStore() {
		List<Player> expectedPlayers = Arrays.asList(mock(Player.class), mock(Player.class));
		doReturn(expectedPlayers).when(playerStore).getAllPlayers();

		List<Player> actualPlayers = testObject.getPlayers();

		assertSame(expectedPlayers, actualPlayers);
	}

	@Test
	public void notifiyPlayerChangeListenerWhenAPlayerGetsAddedWithANonEmptyName() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer("hello");

		verify(playerChangedListener).handle();
	}

	@Test
	public void doNotNotifiyPlayerChangeListenerWhenAPlayerGetsAddedWithAnEmptyName() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer("");

		verify(playerChangedListener, never()).handle();
	}

	@Test
	public void doNotNotifiyPlayerChangeListenerWhenAPlayerGetsAddedWithANullName() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer(null);

		verify(playerChangedListener, never()).handle();
	}

	@Test
	public void addPlayerToStoreWithNextPlayerIdFromPlayerStore() {
		String playerId = UUID.randomUUID().toString();
		doReturn(playerId).when(playerStore).nextPlayerId();
		String playerName = UUID.randomUUID().toString();

		testObject.addPlayer(playerName);

		ArgumentCaptor<Player> playerCaptor = ArgumentCaptor.forClass(Player.class);
		verify(playerStore).addPlayer(playerCaptor.capture());
		Player player = playerCaptor.getValue();

		assertEquals(playerId, player.getId());
		assertEquals(playerName, player.getName());
	}

	@Test
	public void dontAddPlayerToStoreIfPlayerNameIsEmpty() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer("");

		verify(playerStore, never()).addPlayer(any(Player.class));
	}

	@Test
	public void dontAddPlayerToStoreIfPlayerNameIsNull() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer(null);

		verify(playerStore, never()).addPlayer(any(Player.class));
	}

	@Test
	public void notifiyPlayerChangeListenerAfterAddingPlayerToStore() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.addPlayer(UUID.randomUUID().toString());

		InOrder inOrder = inOrder(playerChangedListener, playerStore);
		inOrder.verify(playerStore).addPlayer(any(Player.class));
		inOrder.verify(playerChangedListener).handle();
	}

	@Test
	public void removingPlayerByIdRemovesPlayerFromStore() {
		String playerId = UUID.randomUUID().toString();

		testObject.removePlayer(playerId);

		verify(playerStore).removePlayer(playerId);
	}

	@Test
	public void notifiyPlayerChangeListenerAfterRemovingPlayerFromStore() {
		Listener playerChangedListener = mock(Listener.class);
		testObject.addPlayerChangedListener(playerChangedListener);

		testObject.removePlayer(UUID.randomUUID().toString());

		InOrder inOrder = inOrder(playerChangedListener, playerStore);
		inOrder.verify(playerStore).removePlayer(anyString());
		inOrder.verify(playerChangedListener).handle();
	}
}
