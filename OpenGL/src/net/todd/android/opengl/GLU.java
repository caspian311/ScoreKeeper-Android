package net.todd.android.opengl;

import javax.microedition.khronos.opengles.GL10;

public class GLU {
	private final GL10 gl;

	public GLU(GL10 gl) {
		this.gl = gl;
	}

	public void lookAt(float eyex, float eyey, float eyez, float centerx,
			float centery, float centerz, float upx, float upy, float upz) {

		float[] forward = new float[3];
		float[] side = new float[3];
		float[] up = new float[3];
		float[] m = new float[16];

		forward[0] = centerx - eyex;
		forward[1] = centery - eyey;
		forward[2] = centerz - eyez;

		up[0] = upx;
		up[1] = upy;
		up[2] = upz;

		normalize(forward);

		/* Side = forward x up */
		cross(forward, up, side);
		normalize(side);

		/* Recompute up as: up = side x forward */
		cross(side, forward, up);

		gluMakeIdentityf(m);
		m[0] = side[0];
		m[4] = side[1];
		m[8] = side[2];

		m[1] = up[0];
		m[5] = up[1];
		m[9] = up[2];

		m[2] = -forward[0];
		m[6] = -forward[1];
		m[10] = -forward[2];

		// XXX verify that this works as expected
		// java opengles equivalent --> gl.glMultMatrixf(&m[0][0]);
		gl.glMultMatrixf(m, 0);
		gl.glTranslatef(-eyex, -eyey, -eyez);

	}

	private void gluMakeIdentityf(float[] m) {
		m[0] = 1;
		m[1] = 0;
		m[2] = 0;
		m[3] = 0;

		m[4] = 0;
		m[5] = 1;
		m[6] = 0;
		m[7] = 0;

		m[8] = 0;
		m[9] = 0;
		m[10] = 1;
		m[11] = 0;

		m[12] = 0;
		m[13] = 0;
		m[14] = 0;
		m[15] = 1;
	}

	private void cross(float[] v1, float[] v2, float[] result) {
		result[0] = v1[1] * v2[2] - v1[2] * v2[1];
		result[1] = v1[2] * v2[0] - v1[0] * v2[2];
		result[2] = v1[0] * v2[1] - v1[1] * v2[0];
	}

	private void normalize(float[] v) {
		double r;

		r = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
		if (r == 0.0)
			return;

		v[0] /= r;
		v[1] /= r;
		v[2] /= r;
	}

	public void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
		float[] m = new float[16];
		double sine, cotangent, deltaZ;
		double radians = fovy / 2 * Math.PI / 180;

		deltaZ = zFar - zNear;
		sine = Math.sin(radians);
		if ((deltaZ == 0) || (sine == 0) || (aspect == 0)) {
			return;
		}
		cotangent = Math.cos(radians) / sine;

		gluMakeIdentityf(m);
		m[0] = (float) cotangent / aspect;
		m[5] = (float) cotangent;
		m[10] = (float) (-(zFar + zNear) / deltaZ);
		m[11] = -1f;
		m[14] = (float) (-2 * zNear * zFar / deltaZ);
		m[15] = 0f;
		// XXX verify that this works as expected
		// java opengles equivalent --> gl.glMultMatrixf(&m[0][0]);
		gl.glMultMatrixf(m, 0);
	}
}
