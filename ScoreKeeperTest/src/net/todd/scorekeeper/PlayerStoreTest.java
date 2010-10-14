package net.todd.scorekeeper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import android.content.Context;

public class PlayerStoreTest {
	private Context context;
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;
	private File tempFile;
	
	private int playerId;
	private String playerName;
	
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
		
		fileInputStream = new FileInputStream(tempFile);
		doReturn(fileInputStream).when(context).openFileInput(anyString());
		fileOutputStream = new FileOutputStream(tempFile);
		doReturn(fileOutputStream).when(context).openFileOutput(anyString(), anyInt());
		
		playerId = new Random().nextInt();
		playerName = UUID.randomUUID().toString();
	}
	
	@After
	public void tearDown() throws Exception {
		tempFile.delete();
	}
	
	@Test
	public void initiallyThereAreNoPlayers() {
		assertTrue("players should be empty", new PlayerStore(context).getAllPlayers().isEmpty());
	}
	
	@Test
	public void afterAddingAPlayerThenPlayerShouldBeReturnedInTheAllPlayersCall() {
		Player player = new Player(playerId, playerName);
		new PlayerStore(context).addPlayer(player);
		
		assertEquals(Arrays.asList(player), new PlayerStore(context).getAllPlayers());
	}
	
	@Test
	public void afterAddingAPlayerThenRemovingThatPlayerThenAllPlayersCallShouldBeEmpty() {
		Player player = new Player(playerId, playerName);
		new PlayerStore(context).addPlayer(player);
		new PlayerStore(context).removePlayer(player.getId());
		
		assertTrue("players should be empty", new PlayerStore(context).getAllPlayers().isEmpty());
	}
	
	@Test
	public void removingAPlayerThatWasNeverAddedDoesntDoAnything() {
		new PlayerStore(context).removePlayer(playerId);
		
		assertTrue("players should be empty", new PlayerStore(context).getAllPlayers().isEmpty());
	}
	
	@Test
	public void gettingPlayerById() {
		Player expectedPlayer = new Player(playerId, playerName);
		new PlayerStore(context).addPlayer(expectedPlayer);
		Player actualPlayer = new PlayerStore(context).getPlayerById(playerId);
		
		assertEquals(expectedPlayer, actualPlayer);
	}
	
	@Test
	public void gettingPlayersById() {
		Player player1 = new Player(1, UUID.randomUUID().toString());
		Player player2 = new Player(2, UUID.randomUUID().toString());
		Player player3 = new Player(3, UUID.randomUUID().toString());
		new PlayerStore(context).addPlayer(player1);
		new PlayerStore(context).addPlayer(player2);
		new PlayerStore(context).addPlayer(player3);
		
		assertEquals(Arrays.asList(player1, player3), new PlayerStore(context).getPlayersById(Arrays.asList(1, 3)));
	}
}
