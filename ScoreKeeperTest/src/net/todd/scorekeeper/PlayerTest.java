package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.UUID;

import net.todd.scorekeeper.data.Player;

import org.junit.Test;

public class PlayerTest {
	@Test
	public void equalityCheck() {
		String playerId = UUID.randomUUID().toString();
		String playerName = UUID.randomUUID().toString();

		Player player1 = new Player();
		player1.setId(playerId);
		player1.setName(playerName);
		Player player2 = new Player();
		player2.setId(playerId);
		player2.setName(playerName);

		assertEquals(player1, player2);
		assertFalse(player1 == player2);
	}

	@Test
	public void inequalityCheck() {
		Player player1 = new Player();
		player1.setId(UUID.randomUUID().toString());
		player1.setName(UUID.randomUUID().toString());
		Player player2 = new Player();
		player2.setId(UUID.randomUUID().toString());
		player2.setName(UUID.randomUUID().toString());
		assertFalse(player1.equals(player2));
	}

	@Test
	public void inequalityCheckWithNameTheSame() {
		String name = UUID.randomUUID().toString();
		Player player1 = new Player();
		player1.setId(UUID.randomUUID().toString());
		player1.setName(name);
		Player player2 = new Player();
		player2.setId(UUID.randomUUID().toString());
		player2.setName(name);

		assertFalse(player1.equals(player2));
	}

	@Test
	public void inequalityCheckWithIdTheSame() {
		String playerId = UUID.randomUUID().toString();
		Player player1 = new Player();
		player1.setId(playerId);
		player1.setName(UUID.randomUUID().toString());
		Player player2 = new Player();
		player2.setId(playerId);
		player2.setName(UUID.randomUUID().toString());

		assertFalse(player1.equals(player2));
	}

	@Test
	public void stringify() {
		String playerId = UUID.randomUUID().toString();
		String playerName = UUID.randomUUID().toString();
		Player player = new Player();
		player.setId(playerId);
		player.setName(playerName);

		assertEquals(playerId + ":" + playerName, player.toString());
	}
}
