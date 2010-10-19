package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class GameTest {
	@Test
	public void brandNewObjectsAreEqual() {
		Game game1 = new Game();
		Game game2 = new Game();
		
		assertEquals(game1, game2);
	}
	
	@Test
	public void brandNewObjectsWithDifferentGameOverTimestampAreNotEqual() throws Exception {
		Game game1 = new Game();
		game1.setGameOverTimestamp(new Date());
		
		Thread.sleep(100);
		
		Game game2 = new Game();
		game2.setGameOverTimestamp(new Date());
		
		assertFalse("games should not be equal", game1.equals(game2));
	}
	
	@Test
	public void brandNewObjectsWithSameGameOverTimestampAreEqual() throws Exception {
		Date date = new Date();
		
		Game game1 = new Game();
		game1.setGameOverTimestamp(date);
		
		Thread.sleep(100);
		
		Game game2 = new Game();
		game2.setGameOverTimestamp(date);
		
		assertTrue("games should be equal", game1.equals(game2));
	}
	
	@Test
	public void brandNewObjectsWithDifferentGameTypesAreNotEqual() throws Exception {
		Game game1 = new Game();
		game1.setGameType(UUID.randomUUID().toString());
		
		Thread.sleep(100);
		
		Game game2 = new Game();
		game2.setGameType(UUID.randomUUID().toString());
		
		assertFalse("games should not be equal", game1.equals(game2));
	}
	
	@Test
	public void brandNewObjectsWithSameGameTypesAreEqual() throws Exception {
		String gameType = UUID.randomUUID().toString();
		
		Game game1 = new Game();
		game1.setGameType(gameType);
		
		Thread.sleep(100);
		
		Game game2 = new Game();
		game2.setGameType(gameType);
		
		assertTrue("games should be equal", game1.equals(game2));
	}
}
