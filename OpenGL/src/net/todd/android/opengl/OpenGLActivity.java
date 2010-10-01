package net.todd.android.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class OpenGLActivity extends Activity {
	private GLSurfaceView glSurfaceView;
	private OpenGLRenderer renderer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		glSurfaceView = new GLSurfaceView(this);
		renderer = new OpenGLRenderer();
		glSurfaceView.setRenderer(renderer);
		glSurfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				renderer.toggleAnimation();
			}
		});
		setContentView(glSurfaceView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			finish();
			break;
		case R.id.enableLighting:
			toggleLighting();
			break;
		case R.id.enableWireframes:
			toggleWireframes();
			break;
		case R.id.enableDrawNormals:
			toggleDrawNormals();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void toggleDrawNormals() {
		renderer.toggleDrawNormals();
	}

	private void toggleWireframes() {
		renderer.toggleWireframes();
	}

	private void toggleLighting() {
		renderer.toggleLighting();
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