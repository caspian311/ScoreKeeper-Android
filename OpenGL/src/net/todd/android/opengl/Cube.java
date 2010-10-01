package net.todd.android.opengl;

import javax.microedition.khronos.opengles.GL10;

public class Cube extends Shape {
	private float angle;
	private boolean doAnimate;

	@Override
	void positioning(GL10 gl) {
		gl.glTranslatef(2, 0, 0);
		gl.glRotatef(angle, 0, 1, 0);

		if (doAnimate) {
			angle -= 1.5f;		
		}
	}

	@Override
	float[] getVerticies() {
		float verticies[] = {
				-1.0f, -1.0f,  1.0f, // vertex 1
				 1.0f, -1.0f,  1.0f, // vertex 2 
				 1.0f,  1.0f,  1.0f, // vertex 3
				-1.0f,  1.0f,  1.0f, // vertex 4
				-1.0f, -1.0f, -1.0f, // vertex 5
				 1.0f, -1.0f, -1.0f, // vertex 6
				 1.0f,  1.0f, -1.0f, // vertex 7
				-1.0f,  1.0f, -1.0f  // vertex 8
				};
		return verticies;
	}

	@Override
	byte[] getIndicies() {
		byte[] indicies = new byte[]{ 
			0, 4, 5, // triangle 1  (-1, -1, -1), (-1, 1, -1), (-1, -1, 1)
			0, 5, 1, // triangle 2
			1, 5, 6, // triangle 3
			1, 6, 2, // triangle 4
			2, 6, 7, // triangle 5
			2, 7, 3, // triangle 6
			3, 7, 4, // triangle 7
			3, 4, 0, // triangle 8
			4, 7, 6, // triangle 9
			4, 6, 5, // triangle 10
			3, 0, 1, // triangle 11
			3, 1, 2  // triangle 12
			};
		return indicies;
	}

	@Override
	float[] getColors() {
		float colors[] = { 
				  0f,   0f,   0f, 1.0f, // black
				1.0f,   0f,   0f, 1.0f, // red
				1.0f, 1.0f,   0f, 1.0f, // yellow
				  0f, 1.0f,   0f, 1.0f, // green
				  0f,   0f, 1.0f, 1.0f, // blue
				1.0f,   0f, 1.0f, 1.0f, // purple
				1.0f, 1.0f, 1.0f, 1.0f, // white
			      0f, 1.0f, 1.0f, 1.0f  // teal
		};
		return colors;
	}

	public void setAnimate(boolean doAnimate) {
		this.doAnimate = doAnimate;
	}
}
