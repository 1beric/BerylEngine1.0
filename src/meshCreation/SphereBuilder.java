package meshCreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.data.ModelData;
import tools.math.BerylVector;

public class SphereBuilder {
	
//	private static final double ANGLE = Math.PI * 2 * (1 + Math.sqrt(5)) / 2;
	private static final int[] vertexPairs = new int[] {
			0,1, 0,2, 0,3, 0,4,
			1,2, 2,3, 3,4, 4,1,
			5,1, 5,2, 5,3, 5,4
	};
	private static final int[] edgeTriplets = new int[] {
			0,1,4, 1,2,5,  2,3,6,   3,0,7,
			8,9,4, 9,10,5, 10,11,6, 11,8,7
	};
	private static final BerylVector[] baseVertices = new BerylVector[] {
			new BerylVector(0,1,0), new BerylVector(1,0,0),
			new BerylVector(0,0,1), new BerylVector(-1,0,0),
			new BerylVector(0,0,-1), new BerylVector(0,-1,0)
	};
	
	
	private static Map<Integer, ModelData> built = new HashMap<>();

	public static ModelData build(int resolution) {
		if (built.containsKey(resolution)) return built.get(resolution);
		
		
//		List<Vertex> vertices = new ArrayList<Vertex>();
		List<BerylVector> textures = new ArrayList<BerylVector>();
		List<BerylVector> normals = new ArrayList<BerylVector>();
//		List<Integer> indices = new ArrayList<Integer>();
		
//		fibonacci circle
//		for (int i=0; i<numOfPoints; i++) {
//			double angle1 = i / (double)numOfPoints;
//			double angle2 = ANGLE * i;
//			BerylVector v = new BerylVector();
//			v.x = (float) (Math.sin(angle1) * Math.cos(angle2));
//			v.y = (float) (Math.sin(angle1) * Math.sin(angle2));
//			v.z = (float) (Math.cos(angle1));
//			vertices.add(new Vertex(i, v));
//		}
		
		int divisions = Math.max(0, resolution);
		int vertsPerFace = ((divisions + 3) * (divisions + 3) - (divisions + 3)) / 2;
		
		int numVerts = vertsPerFace * 8 - (divisions + 2) * 12 + 6;
		int trisPerFace = (divisions + 1) * (divisions + 1); // divisions * (divisions + 2) + 1;
		
		List<BerylVector> vertices = new ArrayList<>(numVerts);
		List<Integer> triangles = new ArrayList<>(trisPerFace * 8 * 3);

		for (BerylVector v : baseVertices) {
			vertices.add(v.copy());
		}
		
		
		
		return null;
	}

}
