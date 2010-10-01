package net.todd.android.opengl;

import javax.microedition.khronos.opengles.GL10;

public class Floor extends Shape {
	@Override
	void positioning(GL10 gl) {
		gl.glTranslatef(0, -1, 0);
		gl.glScalef(10, 1f, 10);
	}

	@Override
	float[] getVerticies() {
		float verticies[] = {
				 -1.0f, 0f,  1.0f, // back, left
				  1.0f, 0f,  1.0f, // back, right
				  1.0f, 0f, -1.0f, // front, right
				 -1.0f, 0f, -1.0f  // front, left
				};
		return verticies;
	}

	@Override
	byte[] getIndicies() {
		byte indicies[] = { 
				0, 1, 2,
				2, 3, 0
				};
		return indicies;
	}

	@Override
	float[] getColors() {
		float colors[] = { 
				0f,   0f, 0.5f, 1.0f,
				0f,   0f, 0.5f, 1.0f,
				0f, 1.0f, 1.0f, 1.0f,
				0f, 1.0f, 1.0f, 1.0f
		};
		return colors;
	}
}
