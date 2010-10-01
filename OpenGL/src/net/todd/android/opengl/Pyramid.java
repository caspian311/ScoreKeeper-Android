package net.todd.android.opengl;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid extends Shape {
	private float angle;
	private boolean doAnimate;

	@Override
	void positioning(GL10 gl) {
		gl.glTranslatef(-2, 0, 0);
		gl.glRotatef(angle, 0, 1, 0);

		if (doAnimate) {
			angle += 1f;
		}
	}

	@Override
	float[] getVerticies() {
		float verticies[] = {
			    0f,  1.0f,    0f, // 0 - top
			 -1.0f, -1.0f,  1.0f, // 1 - back, left
			  1.0f, -1.0f,  1.0f, // 2 - back, right
			  1.0f, -1.0f, -1.0f, // 3 - front, right
			 -1.0f, -1.0f, -1.0f  // 4 - front, left
			};
		return verticies;
	}

	@Override
	byte[] getIndicies() {
		byte indicies[] = { 
				0, 3, 4,
				0, 4, 1,
				0, 1, 2,
				0, 2, 3
				};
		return indicies;
	}

	@Override
	float[] getColors() {
		float colors[] = { 
				1.0f, 1.0f, 1.0f, 1.0f, // white
				1.0f,   0f,   0f, 1.0f, // red
				1.0f, 1.0f,   0f, 1.0f, // yellow
				  0f, 1.0f,   0f, 1.0f, // green
				  0f,   0f, 1.0f, 1.0f  // blue
		};
		return colors;
	}

	public void setAnimate(boolean doAnimate) {
		this.doAnimate = doAnimate;
	}
}
