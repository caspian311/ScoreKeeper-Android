package net.todd.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class Shape {
	private boolean enableWireframes;
	private boolean enableDrawNormals;
	private float[] normals;
	
	public void draw(GL10 gl) {
		gl.glPushMatrix();

		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			
			positioning(gl);
			
			setupVerticies(gl);
			setupColors(gl);
			setupNormals(gl);
			setupIndecies(gl);
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			
			if (enableDrawNormals) {
				drawNormals(gl);
			}
		}
		
		gl.glPopMatrix();
	}

	private void drawNormals(GL10 gl) {
		float[] normals = getNormals();
		
		for (int i=0; i<normals.length; i=i+3) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glColor4f(1, 0, 0, 1);
			
			float[] verticies = new float[6];
			verticies[0] = 0;
			verticies[1] = 0;
			verticies[2] = 0;
			verticies[3] = normals[i];
			verticies[4] = normals[i+1];
			verticies[5] = normals[i+2];
			
			ByteBuffer vertexBufferBytes = ByteBuffer.allocateDirect(verticies.length * 4);
			vertexBufferBytes.order(ByteOrder.nativeOrder());
			FloatBuffer vertexBuffer = vertexBufferBytes.asFloatBuffer();
			vertexBuffer.put(verticies);
			vertexBuffer.position(0);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
			byte[] indicies = new byte[] {0, 1};
			ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indicies.length);
			indexBuffer.put(indicies);
			indexBuffer.position(0);
			gl.glDrawElements(GL10.GL_LINES, indicies.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
	}

	void positioning(GL10 gl) {
	}
	
	private void setupNormals(GL10 gl) {
		float normals[] = getNormals();
		
		ByteBuffer normalsBufferBytes = ByteBuffer.allocateDirect(normals.length * 4);
		normalsBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer normalsBuffer = normalsBufferBytes.asFloatBuffer();
		normalsBuffer.put(normals);
		normalsBuffer.position(0);
		
		gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffer);
	}

	private float[] getNormals() {
		if (normals == null) {
			byte[] indicies = getIndicies();
			float[] verticies = getVerticies();
			normals = new NormalCalculator().getNormals(indicies, verticies);
		}
		
		return normals;
	}

	abstract float[] getVerticies();
	
	private void setupIndecies(GL10 gl) {
		byte indicies[] = getIndicies();

		ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indicies.length);
		indexBuffer.put(indicies);
		indexBuffer.position(0);

		if (enableWireframes) {
			gl.glDrawElements(GL10.GL_LINES, indicies.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
		} else {
			gl.glDrawElements(GL10.GL_TRIANGLES, indicies.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
		}
	}

	abstract byte[] getIndicies();
	
	private void setupVerticies(GL10 gl) {
		float[] vertices = getVerticies();
		
		ByteBuffer vertexBufferBytes = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = vertexBufferBytes.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	}
	
	private void setupColors(GL10 gl) {
		float[] colors = getColors();
		
		ByteBuffer colorBufferBytes = ByteBuffer.allocateDirect(colors.length * 4);
		colorBufferBytes.order(ByteOrder.nativeOrder());
		FloatBuffer colorBuffer = colorBufferBytes.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
	}

	abstract float[] getColors();
	
	public void setWireframes(boolean enableWireframes) {
		this.enableWireframes = enableWireframes;
	}
	
	public void setDrawNormals(boolean enableDrawNormals) {
		this.enableDrawNormals = enableDrawNormals;
	}
}
