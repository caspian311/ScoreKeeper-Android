package net.todd.android.opengl;

public class NormalCalculator {
	public float[] getNormals(byte[] indicies, float[] verticies) {
		float[] normals = new float[indicies.length];
		
		for (int i=0; i<indicies.length; i=i+3) {
			byte vertext1 = indicies[i];
			byte vertext2 = indicies[i+1];
			byte vertext3 = indicies[i+2];
			
			Triangle triangle = new Triangle();
			
			triangle.p1.x = verticies[vertext1 * 3];
			triangle.p1.y = verticies[vertext1 * 3 + 1];
			triangle.p1.z = verticies[vertext1 * 3 + 2];
			
			triangle.p2.x = verticies[vertext2 * 3];
			triangle.p2.y = verticies[vertext2 * 3 + 1];
			triangle.p2.z = verticies[vertext2 * 3 + 2];
			
			triangle.p3.x = verticies[vertext3 * 3];
			triangle.p3.y = verticies[vertext3 * 3 + 1];
			triangle.p3.z = verticies[vertext3 * 3 + 2];
			
			Point normal = calculateNormalForTriangle(triangle);
			normals[i] = normal.x;
			normals[i+1] = normal.y;
			normals[i+2] = normal.z;
		}
		
		return normals;
	}
	
	/**
	 * normal_x = (y2 - y1)*(z3 - z1) - (z2 - z1)*(y3 - y1)
	 * normal_y = (z2 - z1)*(x3 - x1) - (y2 - y1)*(z3 - z1)
	 * normal_z = (x2 - x1)*(y3 - y1) - (x2 - x1)*(x3 - x1) 
	 * 
	 * @param triangle
	 * @return normal
	 */
	private Point calculateNormalForTriangle(Triangle triangle) {
//		Point normal = new Point();
//		normal.x = (triangle.p2.y - triangle.p1.y)*(triangle.p3.z - triangle.p1.z) - (triangle.p2.z - triangle.p1.z)*(triangle.p3.y - triangle.p1.y);
//		normal.y = (triangle.p2.z - triangle.p1.z)*(triangle.p3.x - triangle.p1.x) - (triangle.p2.y - triangle.p1.y)*(triangle.p3.z - triangle.p1.z);
//		normal.z = (triangle.p2.x - triangle.p1.x)*(triangle.p3.y - triangle.p1.y) - (triangle.p2.x - triangle.p1.x)*(triangle.p3.x - triangle.p1.x);
//		return normal;

		/**
		v1.x = Vector[3] - Vector[0];
		v1.y = Vector[4] - Vector[1];
		v1.z = Vector[5] - Vector[2];

		v2.x = Vector[6] - Vector[0];
		v2.y = Vector[7] - Vector[1];
		v2.z = Vector[8] - Vector[2];

		Normal.x = (v1.y * v2.z) - (v1.z * v2.y);
		Normal.y = -((v2.z * v1.x) - (v2.x * v1.z));
		Normal.z = (v1.x * v2.y) - (v1.y * v2.x);
		*/
		
		Point v1 = new Point();
		v1.x = triangle.p2.x - triangle.p1.x;
		v1.y = triangle.p2.y - triangle.p1.y;
		v1.z = triangle.p2.z - triangle.p1.z;
		
		Point v2 = new Point();
		v2.x = triangle.p3.x - triangle.p1.x;
		v2.y = triangle.p3.y - triangle.p1.y;
		v2.z = triangle.p3.z - triangle.p1.z;
		
		Point normal = new Point();
		normal.x = (v1.y * v2.z) - (v1.z * v2.y); 
		normal.y = -((v2.z * v1.x) - (v2.x * v1.z));
		normal.z = (v1.x * v2.y) - (v1.y * v2.x);
		
		return normal;
	}
	
	private class Triangle {
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		
		@Override
		public String toString() {
			return "(" + p1 + ", " + p2 + ", " + p3 + ")";
		}
	}

	private class Point {
		float x, y, z;
		
		@Override
		public String toString() {
			return "[" + x + ", " + y + ", " + z + "]";
		}
	}
}
