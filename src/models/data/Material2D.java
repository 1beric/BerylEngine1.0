package models.data;

import renderEngine.models.Texture;
import tools.math.BerylVector;

public class Material2D extends Material {

	private float transparency;
	private float borderWidth;
	private float borderRadius;
	private BerylVector borderColor;
	
	public Material2D() {
		setColor(BerylVector.one(3));
		setColorTexture(Texture.white());
		transparency = 1;
		borderWidth = 0;
		borderRadius = 0;
		borderColor = BerylVector.zero();
	}

	/**
	 * @return the transparency
	 */
	public float getTransparency() {
		return transparency;
	}

	/**
	 * @param transparency the transparency to set
	 */
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	/**
	 * @return the borderWidth
	 */
	public float getBorderWidth() {
		return borderWidth;
	}

	/**
	 * @param borderWidth the borderWidth to set
	 */
	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}

	/**
	 * @return the borderRadius
	 */
	public float getBorderRadius() {
		return borderRadius;
	}

	/**
	 * @param borderRadius the borderRadius to set
	 */
	public void setBorderRadius(float borderRadius) {
		this.borderRadius = borderRadius;
	}

	/**
	 * @return the borderColor
	 */
	public BerylVector getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(BerylVector borderColor) {
		this.borderColor = borderColor;
	}
	
}
