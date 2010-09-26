package net.todd.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
	private float angle;

	public void draw(GL10 gl) {
		gl.glTranslatef(2, 0, 0);
		gl.glRotatef(angle, 0, 1, 0);
		
		gl.glFrontFace(GL10.GL_CW);

		setupVerticies(gl);
		setupColors(gl);
		setupIndecies(gl);
		
		angle -= 1.5f;
		
		gl.glRotatef(-angle, 0, 1, 0);
		gl.glTranslatef(-2, 0, 0);
	}

	private void setupIndecies(GL10 gl) {
		byte indices[] = { 
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

		ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);

		gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	}

	private void setupVerticies(GL10 gl) {
		float vertices[] = {
				-1.0f, -1.0f, -1.0f, // vertex 1
				 1.0f, -1.0f, -1.0f, // vertex 2 
				 1.0f,  1.0f, -1.0f, // vertex 3
				-1.0f,  1.0f, -1.0f, // vertex 4
				-1.0f, -1.0f,  1.0f, // vertex 5
				 1.0f, -1.0f,  1.0f, // vertex 6
				 1.0f,  1.0f,  1.0f, // vertex 7
				-1.0f,  1.0f,  1.0f  // vertex 8
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
				  0f,   0f,   0f, 1.0f, // black
				1.0f,   0f,   0f, 1.0f, // red
				1.0f, 1.0f,   0f, 1.0f, // yellow
				  0f, 1.0f,   0f, 1.0f, // green
				  0f,   0f, 1.0f, 1.0f, // blue
				1.0f,   0f, 1.0f, 1.0f, // purple
				1.0f, 1.0f, 1.0f, 1.0f, // white
			      0f, 1.0f, 1.0f, 1.0f  // teal
		};
		
		ByteBuffer colorBufferBytes = ByteBuffer.allocateDirect(colors.length * 4);
		colorBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer colorBuffer = colorBufferBytes.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
	}
}
