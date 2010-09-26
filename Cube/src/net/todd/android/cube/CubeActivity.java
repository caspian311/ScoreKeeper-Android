package net.todd.android.cube;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class CubeActivity extends Activity {
	private GLSurfaceView glSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(new CubeRenderer());

		setContentView(glSurfaceView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}
}