package net.todd.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Floor {
	private static final float SIZE = 10f;

	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CW);

		gl.glScalef(SIZE, 1f, SIZE);
		
		setupVerticies(gl);
		setupColors(gl);
		setupIndecies(gl);
		
		gl.glScalef(1/SIZE, 1f, 1/SIZE);
	}

	private void setupIndecies(GL10 gl) {
		byte indices[] = { 
				0, 1, 2,
				2, 3, 0
				};

		ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);

		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	}

	private void setupVerticies(GL10 gl) {
		float vertices[] = {
				 -1.0f, -1.0f, -1.0f, // back, left
				  1.0f, -1.0f, -1.0f, // back, right
				  1.0f, -1.0f,  1.0f, // front, right
				 -1.0f, -1.0f,  1.0f  // front, left
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
				0f,   0f, 0.5f, 1.0f,
				0f,   0f, 0.5f, 1.0f,
				0f, 1.0f, 1.0f, 1.0f,
				0f, 1.0f, 1.0f, 1.0f
		};
		
		ByteBuffer colorBufferBytes = ByteBuffer.allocateDirect(colors.length * 4);
		colorBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer colorBuffer = colorBufferBytes.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
	}
}
