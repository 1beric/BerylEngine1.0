package models.data;

import renderEngine.models.Texture;
import tools.math.BerylVector;

public class Material {

	private BerylVector color;
	private Texture colorTexture;

	/**
	 * @return the color
	 */
	public BerylVector getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(BerylVector color) {
		this.color = color;
	}

	/**
	 * @return the texture
	 */
	public Texture getColorTexture() {
		return colorTexture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setColorTexture(Texture texture) {
		this.colorTexture = texture;
	}
	
	

}
