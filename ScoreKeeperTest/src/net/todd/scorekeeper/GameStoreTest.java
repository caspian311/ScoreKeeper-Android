package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Context;

public class GameStoreTest {
	private Context context;
	private File tempFile;

	@BeforeClass
	public static void setUpLogger() {
		Logger.setTestMode(true);
	}

	@AfterClass
	public static void tearDownLogger() {
		Logger.setTestMode(false);
	}

	@Before
	public void setUp() throws Exception {
		tempFile = File.createTempFile(getClass().getName(), ".data");

		context = mock(Context.class);

		when(context.openFileInput(anyString())).thenAnswer(new Answer<FileInputStream>() {
			@Override
			public FileInputStream answer(InvocationOnMock invocation) throws Throwable {
				return new FileInputStream(tempFile);
			}
		});

		when(context.openFileOutput(anyString(), anyInt())).thenAnswer(
				new Answer<FileOutputStream>() {
					@Override
					public FileOutputStream answer(InvocationOnMock invocation) throws Throwable {
						return new FileOutputStream(tempFile);
					}
				});

	}

	@After
	public void tearDown() throws Exception {
		tempFile.delete();
	}

	@Test
	public void initiallyThereAreNoGames() {
		assertTrue("games should be empty", new GameStore(context).getAllGames().isEmpty());
	}

	@Test
	public void afterAddingAPlayerThenPlayerShouldBeReturnedInTheAllPlayersCall() {
		Game game = new Game();
		new GameStore(context).addGame(game);

		assertEquals(Arrays.asList(game), new GameStore(context).getAllGames());
	}

	@Test
	public void addingMultipleAndClearing() {
		GameStore testObject = new GameStore(context);
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
		GameStore testObject = new GameStore(context);
		assertTrue(testObject.getAllGames().isEmpty());

		testObject.clearAllGames();

		assertTrue(testObject.getAllGames().isEmpty());
	}
}
