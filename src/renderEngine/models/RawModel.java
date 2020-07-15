package renderEngine.models;

import models.data.ModelData;

public class RawModel {

	
	private int vaoID;
	private int vertexCount;
	private ModelData data;


	public RawModel(int vaoID, int vertexCount, ModelData data) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.data = data;
	}
	
	
	/**
	 * @return the vaoID
	 */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * @return the vertexCount
	 */
	public int getVertexCount() {
		return vertexCount;
	}


	/**
	 * @return the data
	 */
	public ModelData getData() {
		return data;
	}
	
	
	
}
