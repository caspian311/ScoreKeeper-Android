package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.todd.scorekeeper.data.CurrentGame;
import net.todd.scorekeeper.data.Player;
import net.todd.scorekeeper.data.ScoreBoard;
import net.todd.scorekeeper.data.ScoreBoardEntry;

import org.junit.Test;

public class CurrentGameStoreTest extends AbstractStoreTest {
	@Test
	public void initiallyThereIsNoCurrentGame() {
		assertNull(new CurrentGameStore(getContext()).getCurrentGame());
	}

	@Test
	public void currentGameDataShouldBeAvailableAfterSavingTheState() {
		String expectedGameName = UUID.randomUUID().toString();

		Player player1 = new Player();
		player1.setId("1");
		player1.setName("matt");
		Player player2 = new Player();
		player2.setId("2");
		player2.setName("abbi");
		Player player3 = new Player();
		player3.setId("3");
		player3.setName("caleb");

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(players);
		scoreBoard.setScore(player1, 56);
		scoreBoard.setScore(player2, 34);
		scoreBoard.setScore(player3, 12);
		new CurrentGameStore(getContext()).saveState(expectedGameName, scoreBoard, player2);

		CurrentGame currentGame = new CurrentGameStore(getContext()).getCurrentGame();
		String actualGameName = currentGame.getGameName();
		ScoreBoard actualScoreBoard = currentGame.getScoreBoard();
		List<ScoreBoardEntry> actualEntries = actualScoreBoard.getEntries();
		assertEquals(expectedGameName, actualGameName);
		assertEquals(3, actualEntries.size());
		assertEquals("1", actualEntries.get(0).getPlayer().getId());
		assertEquals("matt", actualEntries.get(0).getPlayer().getName());
		assertEquals(56, actualEntries.get(0).getScore());
		assertEquals("2", actualEntries.get(1).getPlayer().getId());
		assertEquals("abbi", actualEntries.get(1).getPlayer().getName());
		assertEquals(34, actualEntries.get(1).getScore());
		assertEquals("3", actualEntries.get(2).getPlayer().getId());
		assertEquals("caleb", actualEntries.get(2).getPlayer().getName());
		assertEquals(12, actualEntries.get(2).getScore());
		assertEquals("2", currentGame.getCurrentPlayer().getId());
		assertEquals("abbi", currentGame.getCurrentPlayer().getName());
	}

	@Test
	public void currentGameDataShouldNotBeAvailableAfterClearingTheState() {
		String gameName = UUID.randomUUID().toString();

		Player player1 = new Player();
		player1.setId("1");
		player1.setName("matt");
		Player player2 = new Player();
		player2.setId("2");
		player2.setName("abbi");

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setPlayers(players);
		scoreBoard.setScore(player1, 12);
		scoreBoard.setScore(player2, 34);
		new CurrentGameStore(getContext()).saveState(gameName, scoreBoard, player2);

		new CurrentGameStore(getContext()).clearState();

		assertNull(new CurrentGameStore(getContext()).getCurrentGame());
	}

	@Test
	public void clearingStateWhenThereAreNoCurrentGamesDoesNotHurtAnything() {
		CurrentGameStore testObject = new CurrentGameStore(getContext());
		assertNull(testObject.getCurrentGame());

		testObject.clearState();

		assertNull(testObject.getCurrentGame());
	}
}
