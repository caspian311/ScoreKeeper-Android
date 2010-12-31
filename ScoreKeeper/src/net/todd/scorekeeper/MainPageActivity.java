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
		dataConverter.convertData(Player.class, net.todd.scorekeeper.data.Player.class);
		dataConverter.convertData(Game.class, net.todd.scorekeeper.data.Game.class);
		dataConverter.convertData(CurrentGame.class, net.todd.scorekeeper.data.CurrentGame.class);
	}

	@Override
	public void onBackPressed() {
		mainView.backPressed();
	}
}