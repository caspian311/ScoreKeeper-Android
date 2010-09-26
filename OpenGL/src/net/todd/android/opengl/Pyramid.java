package net.todd.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {
	private float angle;

	public void draw(GL10 gl) {
		gl.glTranslatef(-2, 0, 0);
		gl.glRotatef(angle, 0, 1, 0);

		gl.glFrontFace(GL10.GL_CW);

		setupVerticies(gl);
		setupColors(gl);
		setupIndecies(gl);

		angle += 1f;
		
		gl.glRotatef(-angle, 0, 1, 0);
		gl.glTranslatef(2, 0, 0);
	}

	private void setupIndecies(GL10 gl) {
		byte indices[] = { 
				0, 3, 4,
				0, 4, 1,
				0, 1, 2,
				0, 2, 3
				};

		ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);

		gl.glDrawElements(GL10.GL_TRIANGLES, 12, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	}

	private void setupVerticies(GL10 gl) {
		float vertices[] = {
				    0f,  1.0f,    0f, // 0 - top
				 -1.0f, -1.0f, -1.0f, // 1 - back, left
				  1.0f, -1.0f, -1.0f, // 2 - back, right
				  1.0f, -1.0f,  1.0f, // 3 - front, right
				 -1.0f, -1.0f,  1.0f  // 4 - front, left
				};

		ByteBuffer vertexBufferBytes = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = vertexBufferBytes.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	}
	
	private void setupColors(GL10 gl) {
		float colors[] = { 
				1.0f, 1.0f, 1.0f, 1.0f, // white
				1.0f,   0f,   0f, 1.0f, // red
				1.0f, 1.0f,   0f, 1.0f, // yellow
				  0f, 1.0f,   0f, 1.0f, // green
				  0f,   0f, 1.0f, 1.0f  // blue
		};
		
		ByteBuffer colorBufferBytes = ByteBuffer.allocateDirect(colors.length * 4);
		colorBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer colorBuffer = colorBufferBytes.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
	}
}
