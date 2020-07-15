package models.components.renderable;

import models.data.Entity;
import models.data.Material3D;
import models.data.ModelData;
import models.data.ShaderType;

public class Mesh3RC extends BerylRC {

	private ModelData data;
	private Material3D material;
	
	public Mesh3RC(Entity entity) {
		super(entity);
	}

	/**
	 * @return the data
	 */
	public ModelData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ModelData data) {
		this.data = data;
	}

	/**
	 * @return if data == null
	 */
	public boolean isSet() {
		return data != null;
	}

	/**
	 * @return the material
	 */
	public Material3D getMat() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMat(Material3D material) {
		this.material = material;
	}
	
}
