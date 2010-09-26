package net.todd.android.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer {
	private final Cube cube;
	private final Pyramid pyramid;
	private final Floor floor;

	public OpenGLRenderer() {
		cube = new Cube();
		pyramid = new Pyramid();
		floor = new Floor();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		new GLU(gl).lookAt(
				0, 2, 5, 
				0, 0, 0, 
				0, 1, 0);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		floor.draw(gl);
		cube.draw(gl);
		pyramid.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		float ratio = (float) width / height;
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
//		if (useTranslucentBackground) {
//			gl.glClearColor(0, 0, 0, 0);
//		} else {
			gl.glClearColor(0, 0, 0, 0);
//		}
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}
}
