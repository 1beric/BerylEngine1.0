package meshCreation;

import models.data.ModelData;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;

public class CubeBuilder {

	private static final float[] VERTICES = new float[] {
			-1,  1, -1,
			1,  1, -1,
			1, -1, -1,
			1, -1, -1,			// NEG Z
		    -1, -1, -1,
		    -1,  1, -1,

		    -1, -1,  1,
		     -1,  1,  1,
		     -1,  1, -1,
		     -1,  1, -1,			// NEG X
		    -1, -1, -1,
		    -1, -1,  1,

		    1, -1, -1,
		    1,  1, -1,
		    1,  1,  1,
		    1,  1,  1,			// POS X
		     1, -1,  1,
		     1, -1, -1,

		     -1, -1,  1,
		     1, -1,  1,
		     1,  1,  1,
		     1,  1,  1,			// POS Z
		    -1,  1,  1,
		     -1, -1,  1,

		     -1,  1, -1,
		     -1,  1,  1,
		     1,  1,  1,
		     1,  1,  1,			// POS Y
		     1,  1, -1,
		     -1,  1, -1,

		     1, -1,  1,
		     -1, -1,  1,
		     1, -1, -1,
		     1, -1, -1,			// NEG Y
		    -1, -1,  1,
		     -1, -1, -1,
	};
	private static final float[] TEXTURE_COORDS = new float[] {
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
	private static final float[] NORMALS = new float[] {
			0,   0,  -1,		// NEG Z
			0,   0,  -1,		// NEG Z
			0,   0,  -1,		// NEG Z
			0,   0,  -1,		// NEG Z
			0,   0,  -1,		// NEG Z
			0,   0,  -1,		// NEG Z

			-1,   0,   0,		// NEG X
			-1,   0,   0,		// NEG X
			-1,   0,   0,		// NEG X
			-1,   0,   0,		// NEG X
			-1,   0,   0,		// NEG X
			-1,   0,   0,		// NEG X

			1,   0,   0,		// POS X
			1,   0,   0,		// POS X
			1,   0,   0,		// POS X
			1,   0,   0,		// POS X
			1,   0,   0,		// POS X
			1,   0,   0,		// POS X

			0,   0,   1,		// POS Z
			0,   0,   1,		// POS Z
			0,   0,   1,		// POS Z
			0,   0,   1,		// POS Z
			0,   0,   1,		// POS Z
			0,   0,   1,		// POS Z

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
	private static final int[] INDICES = new int[] {
			0,  1,  2,
			3,  4,  5,
			
			6,  7,  8,
			9,  10, 11,
			
			12, 13, 14,
			15, 16, 17,
			
			18, 19, 20,
			21, 22, 23,
			
			24, 25, 26,
			27, 28, 29,
			
			30, 31, 32,
			33, 34, 35
	};
	
	private static final RawModel CUBE = Loader.loadToVAO(new ModelData(VERTICES, TEXTURE_COORDS, NORMALS, INDICES));
	private static boolean put = false;
	
	
	public static final RawModel buildCube() {
		if (!put) {
			LookupTable.putRawModel(CUBE.getData(), CUBE);
			put = true;
		}
		return CUBE;
	}
	
}
