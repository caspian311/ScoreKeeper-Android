package net.todd.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer {
	private final Cube cube;
	private final Pyramid pyramid;
	private final Floor floor;
	
	private boolean lightingEnabled;
	private boolean enableWireframes;
	private boolean enableDrawNormals;
	private boolean doAnimate;

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
		setupLighting(gl);

		new GLU(gl).lookAt(0, 2, 5, 0, 0, 0, 0, 1, 0);

		gl.glEnable(GL10.GL_NORMALIZE);
		
		if (lightingEnabled) {
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			gl.glDisable(GL10.GL_LIGHTING);
		}

		floor.draw(gl);
		cube.draw(gl);
		pyramid.draw(gl);
		
		gl.glFlush();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		float ratio = (float) width / height;
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glFrontFace(GL10.GL_CCW);
	}

	private void setupLighting(GL10 gl) {
		float[] lightAmbient = new float[] { 0.5f, 0.5f, 0.5f, 1.0f };
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);

		float[] lightDiffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);

		float[] lightPosition = new float[] { 2.0f, 2.0f, 2.0f, 1.0f };
		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);
		gl.glEnable(GL10.GL_LIGHT0);
	}

	public void toggleLighting() {
		lightingEnabled = lightingEnabled ? false: true; 
	}

	public void toggleDrawNormals() {
		enableDrawNormals = enableDrawNormals ? false: true; 
		
		cube.setDrawNormals(enableDrawNormals);
		pyramid.setDrawNormals(enableDrawNormals);
		floor.setDrawNormals(enableDrawNormals);
	}
	
	public void toggleWireframes() {
		enableWireframes = enableWireframes ? false: true;
		
		cube.setWireframes(enableWireframes);
		pyramid.setWireframes(enableWireframes);
		floor.setWireframes(enableWireframes);
	}

	public void toggleAnimation() {
		doAnimate = doAnimate ? false : true;
		
		cube.setAnimate(doAnimate);
		pyramid.setAnimate(doAnimate);
	}
}
