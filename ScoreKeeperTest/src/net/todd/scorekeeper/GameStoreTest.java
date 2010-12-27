package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date date2 = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date date3 = cal.getTime();

		Game game1 = new Game();
		game1.setGameOverTimestamp(date1);
		Game game2 = new Game();
		game2.setGameOverTimestamp(date2);
		Game game3 = new Game();
		game3.setGameOverTimestamp(date3);

		GameStore testObject = new GameStore(getContext());
		testObject.addGame(game1);
		testObject.addGame(game2);
		testObject.addGame(game3);
		assertEquals(3, testObject.getAllGames().size());

		testObject.clearAllGames();
		assertTrue(testObject.getAllGames().isEmpty());
	}

	@Test
	public void addingMultipleAndDeletingMultiple() {
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date date2 = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date date3 = cal.getTime();

		Game game1 = new Game();
		game1.setGameOverTimestamp(date1);
		Game game2 = new Game();
		game2.setGameOverTimestamp(date2);
		Game game3 = new Game();
		game3.setGameOverTimestamp(date3);

		new GameStore(getContext()).addGame(game1);
		new GameStore(getContext()).addGame(game2);
		new GameStore(getContext()).addGame(game3);

		new GameStore(getContext()).deleteGame(game2);
		assertEquals(2, new GameStore(getContext()).getAllGames().size());
		new GameStore(getContext()).deleteGame(game3);
		assertEquals(1, new GameStore(getContext()).getAllGames().size());
		new GameStore(getContext()).deleteGame(game1);
		assertTrue("games should be empty", new GameStore(getContext()).getAllGames().isEmpty());
	}

	@Test
	public void removingAPlayerThatWasNeverAddedDoesntDoAnything() {
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		Game game1 = new Game();
		game1.setGameOverTimestamp(date1);

		new GameStore(getContext()).deleteGame(game1);

		assertTrue("games should be empty", new GameStore(getContext()).getAllGames().isEmpty());
	}

	@Test
	public void clearingOutTheGameswhenThereAreNoGamesDoesNotHurtAnything() {
		GameStore testObject = new GameStore(getContext());
		assertTrue(testObject.getAllGames().isEmpty());

		testObject.clearAllGames();

		assertTrue(testObject.getAllGames().isEmpty());
	}
}
