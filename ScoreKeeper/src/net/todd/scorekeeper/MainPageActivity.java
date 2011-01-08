package net.todd.scorekeeper;

import net.todd.scorekeeper.data.DataConverter;
import android.app.Activity;
import android.os.Bundle;

public class MainPageActivity extends Activity {
	private MainPageView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		convertLegacyData();

		CurrentGameStore currentStateStore = new CurrentGameStore(this);
		PageNavigator pageNavigator = new PageNavigator(this);

		new GameRestorer(currentStateStore, pageNavigator).restoreGameInProgress();

		mainView = new MainPageView(this);
		MainPageModel mainModel = new MainPageModel(this, currentStateStore, pageNavigator);
		MainPagePresenter.create(mainView, mainModel);

		setContentView(mainView.getView());
	}

	@SuppressWarnings("deprecation")
	private void convertLegacyData() {
		DataConverter dataConverter = DataConverterFactory.createDataConverter(this);
		convert(dataConverter, Player.class, net.todd.scorekeeper.data.Player.class);
		convert(dataConverter, Game.class, net.todd.scorekeeper.data.Game.class);
		convert(dataConverter, CurrentGame.class, net.todd.scorekeeper.data.CurrentGame.class);
	}

	private void convert(DataConverter dataConverter, Class<?> oldClass, Class<?> newClass) {
		try {
			dataConverter.convertData(oldClass, newClass);
		} finally {
			deleteFile(oldClass.getName());
			deleteFile(newClass.getName());
		}
	}

	@Override
	public void onBackPressed() {
		mainView.backPressed();
	}
}