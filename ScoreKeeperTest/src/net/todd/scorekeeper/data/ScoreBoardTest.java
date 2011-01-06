package net.todd.scorekeeper.data;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.todd.scorekeeper.Scoring;

import org.junit.Before;
import org.junit.Test;

public class ScoreBoardTest {
	private Player player1;
	private Player player2;
	private Player player3;

	private ScoreBoard testObject;

	@Before
	public void setUp() {
		player1 = new Player();
		player1.setId(UUID.randomUUID().toString());
		player1.setName(UUID.randomUUID().toString());
		player2 = new Player();
		player2.setId(UUID.randomUUID().toString());
		player2.setName(UUID.randomUUID().toString());
		player3 = new Player();
		player3.setId(UUID.randomUUID().toString());
		player3.setName(UUID.randomUUID().toString());

		testObject = new ScoreBoard();
		testObject.setPlayers(Arrays.asList(player1, player2, player3));
	}

	@Test
	public void defaultScoringIsHighestWins() {
		assertEquals(Scoring.HIGH, testObject.getScoring());
	}

	@Test
	public void scoringIsSetAndRetreivedFromTheScoreBoard() {
		testObject.setScoring(Scoring.HIGH);
		assertEquals(Scoring.HIGH, testObject.getScoring());

		testObject.setScoring(Scoring.LOW);
		assertEquals(Scoring.LOW, testObject.getScoring());
	}

	@Test
	public void settingAndGettingThePlayersScore() {
		int score1 = new Random().nextInt();
		testObject.setScore(player1, score1);
		int score2 = new Random().nextInt();
		testObject.setScore(player2, score2);
		int score3 = new Random().nextInt();
		testObject.setScore(player3, score3);

		assertEquals(score1, testObject.getScore(player1));
		assertEquals(score2, testObject.getScore(player2));
		assertEquals(score3, testObject.getScore(player3));
	}

	@Test
	public void byDefaultEntriesAreReturnedSortedByHighestToLowestScore() {
		testObject.setScore(player1, 3);
		testObject.setScore(player2, 1);
		testObject.setScore(player3, 5);

		List<ScoreBoardEntry> entries = testObject.getEntries();

		assertEquals(player3, entries.get(0).getPlayer());
		assertEquals(player1, entries.get(1).getPlayer());
		assertEquals(player2, entries.get(2).getPlayer());
	}

	@Test
	public void ifScoringIsSetToLowestWinsThenEntriesAreReturnedSortedByLowestToHighestScore() {
		testObject.setScoring(Scoring.LOW);
		testObject.setScore(player1, 3);
		testObject.setScore(player2, 1);
		testObject.setScore(player3, 5);

		List<ScoreBoardEntry> entries = testObject.getEntries();

		assertEquals(player2, entries.get(0).getPlayer());
		assertEquals(player1, entries.get(1).getPlayer());
		assertEquals(player3, entries.get(2).getPlayer());
	}

	@Test
	public void ifScoringIsSetToHighestWinsThenEntriesAreReturnedSortedByHighestToLowestScore() {
		testObject.setScoring(Scoring.HIGH);
		testObject.setScore(player1, 3);
		testObject.setScore(player2, 1);
		testObject.setScore(player3, 5);

		List<ScoreBoardEntry> entries = testObject.getEntries();

		assertEquals(player3, entries.get(0).getPlayer());
		assertEquals(player1, entries.get(1).getPlayer());
		assertEquals(player2, entries.get(2).getPlayer());
	}
}
