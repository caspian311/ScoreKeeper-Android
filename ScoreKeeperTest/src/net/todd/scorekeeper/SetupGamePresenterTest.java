package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SetupGamePresenterTest {
	@Mock
	private SetupGameView view;
	@Mock
	private SetupGameModel model;

	private Listener backButtonListener;
	private Listener addPlayersButtonListener;
	private Listener modelStateChangedListener;
	private Listener startGameButtonListener;
	private Listener gameNameChangedListener;
	private Listener orderPlayersButtonListener;
	private Listener scoringChangedListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		SetupGamePresenter.create(view, model);

		ArgumentCaptor<Listener> backButtonListenerCaptor = ArgumentCaptor.forClass(Listener.class);
		verify(view).addBackPressedListener(backButtonListenerCaptor.capture());
		backButtonListener = backButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> addPlayersButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addAddPlayersButtonPressedListener(addPlayersButtonListenerCaptor.capture());
		addPlayersButtonListener = addPlayersButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> orderPlayersButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addOrderPlayersPressedListener(orderPlayersButtonListenerCaptor.capture());
		orderPlayersButtonListener = orderPlayersButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> startGameButtonListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addStartGamePressedListener(startGameButtonListenerCaptor.capture());
		startGameButtonListener = startGameButtonListenerCaptor.getValue();

		ArgumentCaptor<Listener> gameNameChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addGameNameChangedListener(gameNameChangedListenerCaptor.capture());
		gameNameChangedListener = gameNameChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> scoringChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(view).addScoringChangedListener(scoringChangedListenerCaptor.capture());
		scoringChangedListener = scoringChangedListenerCaptor.getValue();

		ArgumentCaptor<Listener> gameStateChangedListenerCaptor = ArgumentCaptor
				.forClass(Listener.class);
		verify(model).addStateChangedListener(gameStateChangedListenerCaptor.capture());
		modelStateChangedListener = gameStateChangedListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void initiallyPullValuesFromModelAndPopulateView() {
		String gameName = UUID.randomUUID().toString();
		doReturn(gameName).when(model).getGameName();

		Scoring scoring = Scoring.HIGH;
		doReturn(scoring).when(model).getScoring();

		SetupGamePresenter.create(view, model);

		verify(view).setGameName(gameName);
		verify(view).setScoring(scoring);
	}

	@Test
	public void whenBackButtonIsPressedThenCancelTheModel() {
		backButtonListener.handle();

		verify(model).cancel();
	}

	@Test
	public void whenAddPlayersButtonIsPressedThenGoToTheAddPlayersScreen() {
		addPlayersButtonListener.handle();

		verify(model).goToAddPlayersScreen();
	}

	@Test
	public void initiallyIfThereAreNoPlayersAddedToTheGameThenDisableTheOrderPlayersButton() {
		doReturn(false).when(model).arePlayersAddedToGame();

		SetupGamePresenter.create(view, model);

		verify(view).setOrderPlayersButtonEnabled(false);
	}

	@Test
	public void initiallyIfThereArePlayersAddedToTheGameThenEnableTheOrderPlayersButton() {
		doReturn(true).when(model).arePlayersAddedToGame();

		SetupGamePresenter.create(view, model);

		verify(view).setOrderPlayersButtonEnabled(true);
	}

	@Test
	public void initiallyIfTheTheGameIsCompleteThenEnableTheStartGameButton() {
		doReturn(true).when(model).isGameSetupComplete();

		SetupGamePresenter.create(view, model);

		verify(view).setStartGameButtonEnabled(true);
	}

	@Test
	public void initiallyIfTheTheGameIsNotCompleteThenDisableTheStartGameButton() {
		doReturn(false).when(model).isGameSetupComplete();

		SetupGamePresenter.create(view, model);

		verify(view).setStartGameButtonEnabled(false);
	}

	@Test
	public void whenTheModelStateChangesAndTheGameIsNotSetupCompletelyThenDisableTheStartGameButton() {
		doReturn(false).when(model).isGameSetupComplete();

		modelStateChangedListener.handle();

		verify(view).setStartGameButtonEnabled(false);
	}

	@Test
	public void whenTheModelStateChangesAndTheGameIsSetupCompletelyThenEnableTheStartGameButton() {
		doReturn(true).when(model).isGameSetupComplete();

		modelStateChangedListener.handle();

		verify(view).setStartGameButtonEnabled(true);
	}

	@Test
	public void whenTheModelStateChangesAndThereAreNoPlayersAddedThenDisableTheOrderPlayersButton() {
		doReturn(false).when(model).arePlayersAddedToGame();

		modelStateChangedListener.handle();

		verify(view).setOrderPlayersButtonEnabled(false);
	}

	@Test
	public void whenTheModelStateChangesAndThereArePlayersAddedThenEnableTheOrderPlayersButton() {
		doReturn(true).when(model).arePlayersAddedToGame();

		modelStateChangedListener.handle();

		verify(view).setOrderPlayersButtonEnabled(true);
	}

	@Test
	public void whenStartGameButtonIsPressedThenStartTheGame() {
		startGameButtonListener.handle();

		verify(model).startGame();
	}

	@Test
	public void whenGameNameChangesThenSetTheGameNameOnTheModel() {
		String gameName = UUID.randomUUID().toString();
		doReturn(gameName).when(view).getGameName();

		gameNameChangedListener.handle();

		verify(model).setGameName(gameName);
	}

	@Test
	public void whenOrderPlayersButtonIsPressedThenGoToOrderPlayersScreen() {
		orderPlayersButtonListener.handle();

		verify(model).goToOrderPlayersScreen();
	}

	@Test
	public void whenScoringChangesThenTakeTheScoringFromViewAndPutInOnTheModel() {
		doReturn(Scoring.LOW).when(view).getScoring();

		scoringChangedListener.handle();

		verify(model).setScoring(Scoring.LOW);
	}
}
