package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import net.todd.scorekeeper.data.Player;

import org.junit.Before;
import org.junit.Test;

public class PlayerStoreTest extends AbstractStoreTest {
	private String playerId;
	private String playerName;

	@Before
	public void initializePlayerData() throws Exception {
		playerId = UUID.randomUUID().toString();
		playerName = UUID.randomUUID().toString();
	}

	@Test
	public void initiallyThereAreNoPlayers() {
		assertTrue("players should be empty", new PlayerStore(getContext()).getAllPlayers()
				.isEmpty());
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
	public void addingMultipleAndDeletingMultiple() {
		PlayerStore testObject = new PlayerStore(getContext());
		String id1 = UUID.randomUUID().toString();
		Player player1 = new Player(id1, playerName);
		String id2 = UUID.randomUUID().toString();
		Player player2 = new Player(id2, playerName);
		String id3 = UUID.randomUUID().toString();
		Player player3 = new Player(id3, playerName);

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

		assertTrue("players should be empty", new PlayerStore(getContext()).getAllPlayers()
				.isEmpty());
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
		String playerId1 = UUID.randomUUID().toString();
		String playerId3 = UUID.randomUUID().toString();
		Player player1 = new Player(playerId1, UUID.randomUUID().toString());
		Player player2 = new Player(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		Player player3 = new Player(playerId3, UUID.randomUUID().toString());
		new PlayerStore(getContext()).addPlayer(player1);
		new PlayerStore(getContext()).addPlayer(player2);
		new PlayerStore(getContext()).addPlayer(player3);

		assertEquals(Arrays.asList(player1, player3),
				new PlayerStore(getContext()).getPlayersById(Arrays.asList(playerId1, playerId3)));
	}
}
