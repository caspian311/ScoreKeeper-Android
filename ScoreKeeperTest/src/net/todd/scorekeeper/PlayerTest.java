package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.UUID;

import org.junit.Test;

public class PlayerTest {
	@Test
	public void equalityCheck() {
		int playerId = new Random().nextInt();
		String playerName = UUID.randomUUID().toString();
		
		Player player1 = new Player(playerId, playerName);
		Player player2 = new Player(playerId, playerName);
		
		assertEquals(player1, player2);
		assertFalse(player1 == player2);
	}
	
	@Test
	public void inequalityCheck() {
		Player player1 = new Player(new Random().nextInt(), UUID.randomUUID().toString());
		Player player2 = new Player(new Random().nextInt(), UUID.randomUUID().toString());
		
		assertFalse(player1.equals(player2));
	}
	
	@Test
	public void inequalityCheckWithNameTheSame() {
		String name = UUID.randomUUID().toString();
		Player player1 = new Player(new Random().nextInt(), name);
		Player player2 = new Player(new Random().nextInt(), name);
		
		assertFalse(player1.equals(player2));
	}
	
	@Test
	public void inequalityCheckWithIdTheSame() {
		int playerId = new Random().nextInt();
		Player player1 = new Player(playerId, UUID.randomUUID().toString());
		Player player2 = new Player(playerId, UUID.randomUUID().toString());
		
		assertFalse(player1.equals(player2));
	}
	
	@Test
	public void stringify() {
		int playerId = new Random().nextInt();
		String playerName = UUID.randomUUID().toString();
		Player player = new Player(playerId, playerName);
		
		assertEquals(playerId + ":" + playerName, player.toString());
	}
}
