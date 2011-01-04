package net.todd.scorekeeper.data;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.todd.scorekeeper.DataConverterFactory;
import net.todd.scorekeeper.Game;
import net.todd.scorekeeper.Logger;
import net.todd.scorekeeper.ObjectSerializerPersistor;
import net.todd.scorekeeper.Persistor;
import net.todd.scorekeeper.Player;
import net.todd.scorekeeper.ScoreBoard;
import net.todd.scorekeeper.data.DataConverter.ClassConversionBean;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Context;

@SuppressWarnings("deprecation")
public class DataConverterTest {
	private File tempFile1;
	private File tempFile2;
	private Context context;

	@BeforeClass
	public static void setUpLogger() {
		Logger.setTestMode(true);
	}

	@AfterClass
	public static void tearDownLogger() {
		Logger.setTestMode(false);
	}

	@Before
	public void setUpContextAndTempFile() throws Exception {
		tempFile1 = File.createTempFile(getClass().getName(), ".data");
		tempFile2 = File.createTempFile(getClass().getName(), ".xml");

		context = mock(Context.class);

		when(context.openFileInput(endsWith(".data"))).thenAnswer(new Answer<FileInputStream>() {
			@Override
			public FileInputStream answer(InvocationOnMock invocation) throws Throwable {
				return new FileInputStream(tempFile1);
			}
		});

		when(context.openFileInput(endsWith(".xml"))).thenAnswer(new Answer<FileInputStream>() {
			@Override
			public FileInputStream answer(InvocationOnMock invocation) throws Throwable {
				return new FileInputStream(tempFile2);
			}
		});

		when(context.openFileOutput(endsWith(".data"), anyInt())).thenAnswer(
				new Answer<FileOutputStream>() {
					@Override
					public FileOutputStream answer(InvocationOnMock invocation) throws Throwable {
						return new FileOutputStream(tempFile1);
					}
				});

		when(context.openFileOutput(endsWith(".xml"), anyInt())).thenAnswer(
				new Answer<FileOutputStream>() {
					@Override
					public FileOutputStream answer(InvocationOnMock invocation) throws Throwable {
						return new FileOutputStream(tempFile2);
					}
				});
	}

	@After
	public void tearDown() throws Exception {
		tempFile1.delete();
		tempFile2.delete();
	}

	@Test
	public void convertSimpleObjectsFromObjectSerializationToXml() {
		Cat cat = new Cat();
		cat.setName(UUID.randomUUID().toString());
		Persistor<Cat> source = ObjectSerializerPersistor.create(Cat.class, context);
		source.persist(new ArrayList<Cat>(Arrays.asList(cat)));

		new DataConverter(context, Arrays.asList(new ClassConversionBean(Cat.class, Dog.class)))
				.convertData(Cat.class, Dog.class);

		Persistor<Dog> target = XmlPersistor.create(Dog.class, context);
		assertEquals(1, target.load().size());
		assertEquals(cat.getName(), target.load().get(0).getName());

		assertTrue("Should have removed the original", source.load().isEmpty());
	}

	@Test
	public void doingADataConversionMultipleTimesDoesNotDestroyNewlyConvertedData() {
		Cat cat = new Cat();
		cat.setName(UUID.randomUUID().toString());
		Persistor<Cat> source = ObjectSerializerPersistor.create(Cat.class, context);
		source.persist(new ArrayList<Cat>(Arrays.asList(cat)));

		DataConverter dataConverter = new DataConverter(context,
				Arrays.asList(new ClassConversionBean(Cat.class, Dog.class)));
		dataConverter.convertData(Cat.class, Dog.class);
		dataConverter.convertData(Cat.class, Dog.class);
		dataConverter.convertData(Person.class, Person.class);

		Persistor<Dog> target = XmlPersistor.create(Dog.class, context);
		assertEquals(1, target.load().size());
		assertEquals(cat.getName(), target.load().get(0).getName());

		assertTrue("Should have removed the original", source.load().isEmpty());
	}

	@Test
	public void convertPlayerObjectsFromObjectSerializationToXml() {
		Player player1 = new Player(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		Player player2 = new Player(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		Player player3 = new Player(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		Persistor<Player> source = ObjectSerializerPersistor.create(Player.class, context);
		source.persist(new ArrayList<Player>(Arrays.asList(player1, player2, player3)));

		DataConverter dataConverter = DataConverterFactory.createDataConverter(context);
		dataConverter.convertData(Player.class, net.todd.scorekeeper.data.Player.class);

		Persistor<net.todd.scorekeeper.data.Player> target = XmlPersistor.create(
				net.todd.scorekeeper.data.Player.class, context);
		List<net.todd.scorekeeper.data.Player> translatedPlayers = target.load();
		assertEquals(3, translatedPlayers.size());
		assertEquals(player1.getId(), translatedPlayers.get(0).getId());
		assertEquals(player1.getName(), translatedPlayers.get(0).getName());
		assertEquals(player2.getId(), translatedPlayers.get(1).getId());
		assertEquals(player2.getName(), translatedPlayers.get(1).getName());
		assertEquals(player3.getId(), translatedPlayers.get(2).getId());
		assertEquals(player3.getName(), translatedPlayers.get(2).getName());
		assertTrue("Should have removed the original", source.load().isEmpty());
	}

	@Test
	public void convertComplexObjectsFromObjectSerializationToXml() {
		Game game = new Game();
		game.setGameOverTimestamp(new Date());
		game.setGameType(UUID.randomUUID().toString());
		Player player1 = new Player(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		player1.setSelected(true);
		ScoreBoard scoreBoard = new ScoreBoard(Arrays.asList(player1));
		scoreBoard.getEntries().get(0).setScore(new Random().nextInt());
		game.setScoreBoard(scoreBoard);

		Persistor<Game> source = ObjectSerializerPersistor.create(Game.class, context);
		source.persist(new ArrayList<Game>(Arrays.asList(game)));

		DataConverter dataConverter = DataConverterFactory.createDataConverter(context);
		dataConverter.convertData(Game.class, net.todd.scorekeeper.data.Game.class);

		Persistor<net.todd.scorekeeper.data.Game> target = XmlPersistor.create(
				net.todd.scorekeeper.data.Game.class, context);
		List<net.todd.scorekeeper.data.Game> translatedGames = target.load();
		assertEquals(1, translatedGames.size());
		assertEquals(game.getGameOverTimestamp(), translatedGames.get(0).getGameOverTimestamp());
		assertEquals(1, translatedGames.get(0).getScoreBoard().getEntries().size());
		assertEquals(player1.getId(), translatedGames.get(0).getScoreBoard().getEntries().get(0)
				.getPlayer().getId());
		assertEquals(player1.getName(), translatedGames.get(0).getScoreBoard().getEntries().get(0)
				.getPlayer().getName());
		assertEquals(player1.istSelected(), translatedGames.get(0).getScoreBoard().getEntries()
				.get(0).getPlayer().isSelected());
		assertEquals(game.getScoreBoard().getEntries().get(0).getScore(), translatedGames.get(0)
				.getScoreBoard().getEntries().get(0).getScore());

		assertTrue("Should have removed the original", source.load().isEmpty());
	}
}
