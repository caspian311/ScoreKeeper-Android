package net.todd.scorekeeper;

import java.util.Arrays;
import java.util.List;

import net.todd.scorekeeper.data.DataConverter;
import net.todd.scorekeeper.data.DataConverter.ClassConversionBean;
import android.content.Context;

@SuppressWarnings("deprecation")
public class DataConverterFactory {
	public static DataConverter createDataConverter(Context context) {
		ClassConversionBean gameConversion = new ClassConversionBean(Game.class,
				net.todd.scorekeeper.data.Game.class);
		ClassConversionBean scoreBoardConversion = new ClassConversionBean(ScoreBoard.class,
				net.todd.scorekeeper.data.ScoreBoard.class);
		ClassConversionBean scoreBoardEntryConversion = new ClassConversionBean(
				ScoreBoardEntry.class, net.todd.scorekeeper.data.ScoreBoardEntry.class);
		ClassConversionBean playerConversion = new ClassConversionBean(Player.class,
				net.todd.scorekeeper.data.Player.class);
		ClassConversionBean currentGameConversion = new ClassConversionBean(CurrentGame.class,
				net.todd.scorekeeper.data.CurrentGame.class);
		List<ClassConversionBean> conversions = Arrays.asList(gameConversion, scoreBoardConversion,
				scoreBoardEntryConversion, playerConversion, currentGameConversion);
		DataConverter dataConverter = new DataConverter(context, conversions);
		return dataConverter;
	}
}
