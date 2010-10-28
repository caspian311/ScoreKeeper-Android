package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class PlayerStoreTest extends AbstractStoreTest {
	private int playerId;
	private String playerName;

	@Before
	public void initializePlayerData() throws Exception {
		playerId = new Random().nextInt();
		playerName = UUID.randomUUID().toString();
	}

	@Test
	public void initiallyThereAreNoPlayers() {
		assertTrue("players should be empty", new PlayerStore(getContext()).getAllPlayers().isEmpty());
	}

	@Test
	public void afterAddingAPlayerThenPlayerShouldBeReturnedInTheAllPlayersCall() {
		Player player = new Player(playerId, playerName);
		new PlayerStore(getContext()).addPlayer(player);

		assertEquals(Arrays.asList(player), new PlayerStore(getContext()).getAllPlayers());
	}

	@Test
	public void afterAddingAPlayerThenRemovingThatPlayerThenAllPlayersCallShouldBeEmpty() {
		Player player = new Player(playerId, playerName);
		new PlayerStore(getContext()).addPlayer(player);
		assertEquals(1, new PlayerStore(getContext()).getAllPlayers().size());

		new PlayerStore(getContext()).removePlayer(player.getId());

		assertEquals(0, new PlayerStore(getContext()).getAllPlayers().size());
	}

	@Test
	public void addingMultipleandDeletingMultiple() {
		PlayerStore testObject = new PlayerStore(getContext());
		Player player1 = new Player(1, playerName);
		Player player2 = new Player(2, playerName);
		Player player3 = new Player(3, playerName);

		testObject.addPlayer(player1);
		testObject.addPlayer(player2);
		testObject.addPlayer(player3);
		assertEquals(3, testObject.getAllPlayers().size());

		testObject.removePlayer(player1.getId());
		assertEquals(2, testObject.getAllPlayers().size());
		testObject.removePlayer(player2.getId());
		assertEquals(1, testObject.getAllPlayers().size());
		testObject.removePlayer(player3.getId());
		assertEquals(0, testObject.getAllPlayers().size());
	}

	@Test
	public void removingAPlayerThatWasNeverAddedDoesntDoAnything() {
		new PlayerStore(getContext()).removePlayer(playerId);

		assertTrue("players should be empty", new PlayerStore(getContext()).getAllPlayers().isEmpty());
	}

	@Test
	public void gettingPlayerById() {
		Player expectedPlayer = new Player(playerId, playerName);
		new PlayerStore(getContext()).addPlayer(expectedPlayer);
		Player actualPlayer = new PlayerStore(getContext()).getPlayerById(playerId);

		assertEquals(expectedPlayer, actualPlayer);
	}

	@Test
	public void gettingPlayersById() {
		Player player1 = new Player(1, UUID.randomUUID().toString());
		Player player2 = new Player(2, UUID.randomUUID().toString());
		Player player3 = new Player(3, UUID.randomUUID().toString());
		new PlayerStore(getContext()).addPlayer(player1);
		new PlayerStore(getContext()).addPlayer(player2);
		new PlayerStore(getContext()).addPlayer(player3);

		assertEquals(Arrays.asList(player1, player3),
				new PlayerStore(getContext()).getPlayersById(Arrays.asList(1, 3)));
	}
}
