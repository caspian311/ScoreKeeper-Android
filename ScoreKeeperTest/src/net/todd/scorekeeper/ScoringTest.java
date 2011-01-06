package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class ScoringTest {
	@Test
	public void byNamereturnNullWhenGivenANonSensicalText() {
		assertNull(Scoring.byName(UUID.randomUUID().toString()));
	}

	@Test
	public void gettingAScoringByNameReturnsTheCorrectScoring() {
		assertEquals(Scoring.HIGH, Scoring.byName(Scoring.HIGH.getText()));

		assertEquals(Scoring.LOW, Scoring.byName(Scoring.LOW.getText()));
	}
}
