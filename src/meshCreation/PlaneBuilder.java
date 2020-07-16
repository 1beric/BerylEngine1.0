package meshCreation;

import models.data.ModelData;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;

public class PlaneBuilder {

	private static final float[] D_VERTICES = new float[] {
		     -1,  0, -1,
		     -1,  0,  1,
		      1,  0,  1,
		      1,  0,  1,			// POS Y
		      1,  0, -1,
		     -1,  0, -1,

		      1, -0,  1,
		     -1, -0,  1,
		      1, -0, -1,
		      1, -0, -1,			// NEG Y
		     -1, -0,  1,
		     -1, -0, -1,
	};
	private static final float[] D_TEXTURE_COORDS = new float[] {
			0, 0,	// BOTTOM LEFT
			0, 1,	// TOP LEFT
			1, 0,	// BOTTOM RIGHT
			1, 0,	// BOTTOM RIGHT
			0, 1,	// TOP LEFT
			1, 1,	// TOP RIGHT
			
			0, 0,	// BOTTOM LEFT
			0, 1,	// TOP LEFT
			1, 0,	// BOTTOM RIGHT
			1, 0,	// BOTTOM RIGHT
			0, 1,	// TOP LEFT
			1, 1,	// TOP RIGHT
	};
	private static final float[] D_NORMALS = new float[] {
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y

			0,  -1,   0,		// NEG Y
			0,  -1,   0,		// NEG Y
			0,  -1,   0,		// NEG Y
			0,  -1,   0,		// NEG Y
			0,  -1,   0,		// NEG Y
			0,  -1,   0,		// NEG Y
	};	
	private static final int[] D_INDICES = new int[] {
			0,  1,  2,
			3,  4,  5,
			
			6,  7,  8,
			9,  10, 11,
	};
	
	private static final float[] VERTICES = new float[] {
		     -1,  0, -1,
		     -1,  0,  1,
		      1,  0,  1,
		      1,  0,  1,			// POS Y
		      1,  0, -1,
		     -1,  0, -1,
	};
	private static final float[] TEXTURE_COORDS = new float[] {
			0, 0,	// BOTTOM LEFT
			0, 1,	// TOP LEFT
			1, 0,	// BOTTOM RIGHT
			1, 0,	// BOTTOM RIGHT
			0, 1,	// TOP LEFT
			1, 1,	// TOP RIGHT
	};
	private static final float[] NORMALS = new float[] {
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
			0,   1,   0,		// POS Y
	};	
	private static final int[] INDICES = new int[] {
			0,  1,  2,
			3,  4,  5,
	};
	
	private static final RawModel D_PLANE = Loader.loadToVAO(new ModelData(D_VERTICES, D_TEXTURE_COORDS, D_NORMALS, D_INDICES));
	private static final RawModel PLANE = Loader.loadToVAO(new ModelData(VERTICES, TEXTURE_COORDS, NORMALS, INDICES));
	private static boolean putD = false;
	private static boolean put = false;
	
	
	public static final RawModel buildPlane() {
		if (!put) {
			LookupTable.putRawModel(PLANE.getData(), PLANE);
			put = true;
		}
		return PLANE;
	}
	
	public static final RawModel buildDoubleSidedPlane() {
		if (!putD) {
			LookupTable.putRawModel(D_PLANE.getData(), D_PLANE);
			putD = true;
		}
		return D_PLANE;
	}
	
}
