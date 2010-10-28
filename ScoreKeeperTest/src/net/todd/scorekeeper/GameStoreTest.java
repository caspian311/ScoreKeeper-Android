package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class GameStoreTest extends AbstractStoreTest {
	@Test
	public void initiallyThereAreNoGames() {
		assertTrue("games should be empty", new GameStore(getContext()).getAllGames().isEmpty());
	}

	@Test
	public void afterAddingAPlayerThenPlayerShouldBeReturnedInTheAllPlayersCall() {
		Game game = new Game();
		new GameStore(getContext()).addGame(game);

		assertEquals(Arrays.asList(game), new GameStore(getContext()).getAllGames());
	}

	@Test
	public void addingMultipleAndClearing() {
		GameStore testObject = new GameStore(getContext());
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();

		testObject.addGame(game1);
		testObject.addGame(game2);
		testObject.addGame(game3);
		assertEquals(3, testObject.getAllGames().size());

		testObject.clearAllGames();
		assertTrue(testObject.getAllGames().isEmpty());
	}

	@Test
	public void clearingOutTheGameswhenThereAreNoGamesDoesNotHurtAnything() {
		GameStore testObject = new GameStore(getContext());
		assertTrue(testObject.getAllGames().isEmpty());

		testObject.clearAllGames();

		assertTrue(testObject.getAllGames().isEmpty());
	}
}
