package net.todd.scorekeeper.test;

import static org.mockito.Mockito.*;

import java.util.Random;

import junit.framework.TestCase;
import net.todd.scorekeeper.GameModel;
import net.todd.scorekeeper.GamePresenter;
import net.todd.scorekeeper.GameView;
import net.todd.scorekeeper.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GamePresenterTest extends TestCase {
	@Mock
	private GameView view;
	@Mock
	private GameModel model;

	@Override
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCurrentPlayerIsSetInitiallyToNextPlayerFromModel() {
		Player player = mock(Player.class);
		doReturn(player).when(model).getNextPlayer();
		int score = new Random().nextInt();
		doReturn(score).when(model).getCurrentPlayersScore();

		GamePresenter.create(view, model);

		verify(view).setCurrentPlayer(player, score);
	}
}
