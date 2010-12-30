package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.todd.scorekeeper.data.Game;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;

import org.junit.Test;

public class GameStoreTest extends AbstractStoreTest {
	@Test
	public void initiallyThereAreNoGames() {
		assertTrue("games should be empty", new GameStore(getContext()).getAllGames().isEmpty());
	}

	@Test
	public void afterAddingAPlayerThenPlayerShouldBeReturnedInTheAllPlayersCall() {
		Game game = new Game();
		game.setGameOverTimestamp(new Date());
		game.setGameType(UUID.randomUUID().toString());
		ScoreBoard scoreBoard = new ScoreBoard();
		Player player1 = new Player();
		player1.setId(UUID.randomUUID().toString());
		player1.setName(UUID.randomUUID().toString());
		player1.setSelected(true);
		scoreBoard.setPlayers(new ArrayList<Player>(Arrays.asList(player1)));
		scoreBoard.getEntries().get(0).setScore(new Random().nextInt());
		game.setScoreBoard(scoreBoard);
		new GameStore(getContext()).addGame(game);

		List<Game> savedGames = new GameStore(getContext()).getAllGames();
		assertEquals(1, savedGames.size());
		assertEquals(game.getGameOverTimestamp(), savedGames.get(0).getGameOverTimestamp());
		assertEquals(game.getGameType(), savedGames.get(0).getGameType());
		assertEquals(1, savedGames.get(0).getScoreBoard().getEntries().size());
		assertEquals(player1.getId(), savedGames.get(0).getScoreBoard().getEntries().get(0)
				.getPlayer().getId());
		assertEquals(player1.getName(), savedGames.get(0).getScoreBoard().getEntries().get(0)
				.getPlayer().getName());
		assertEquals(player1.isSelected(), savedGames.get(0).getScoreBoard().getEntries().get(0)
				.getPlayer().isSelected());
		assertEquals(game.getScoreBoard().getEntries().get(0).getScore(), savedGames.get(0)
				.getScoreBoard().getEntries().get(0).getScore());
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
		game1.setScoreBoard(new ScoreBoard());
		game1.setGameOverTimestamp(date1);
		Game game2 = new Game();
		game2.setScoreBoard(new ScoreBoard());
		game2.setGameOverTimestamp(date2);
		Game game3 = new Game();
		game3.setScoreBoard(new ScoreBoard());
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
		game1.setScoreBoard(new ScoreBoard());
		Game game2 = new Game();
		game2.setGameOverTimestamp(date2);
		game2.setScoreBoard(new ScoreBoard());
		Game game3 = new Game();
		game3.setGameOverTimestamp(date3);
		game3.setScoreBoard(new ScoreBoard());

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
