package models.components.renderable;

import models.data.Entity;
import settings.Constants;
import tools.math.BerylVector;

public class SkyboxRC extends BerylRC {

	private static final BerylVector DAY_COLOR = (BerylVector)Constants.get("DAY_COLOR");
	private static final String[] DEFAULT_SKYBOX = (String[])Constants.get("DEFAULT_SKYBOX");
	
	private BerylVector color;
	private String[] cubeMap1;
	private String[] cubeMap2;
	private float factor;
	private float lower;
	private float upper;
	private float rotation;

	public SkyboxRC(Entity entity) {
		super(entity);
		init(DAY_COLOR,DEFAULT_SKYBOX,DEFAULT_SKYBOX,Float.MAX_VALUE,Float.MAX_VALUE);
	}
	
	public SkyboxRC(BerylVector color, String[] textures1, String[] textures2, float lower, float upper, Entity entity) {
		super(entity);
		init(color,textures1,textures2,lower,upper);
	}

	private void init(BerylVector color, String[] textures1, String[] textures2, float lower, float upper) {
		this.color = color;
		cubeMap1 = textures1;
		cubeMap2 = textures2;
		this.lower = lower;
		this.upper = upper;
		this.factor = 0;
		this.rotation = 0;
	}

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
	 * @return the cubeMap1
	 */
	public String[] getCubeMap1() {
		return cubeMap1;
	}

	/**
	 * @param cubeMap1 the cubeMap1 to set
	 */
	public void setCubeMap1(String[] cubeMap1) {
		this.cubeMap1 = cubeMap1;
	}

	/**
	 * @return the cubeMap2
	 */
	public String[] getCubeMap2() {
		return cubeMap2;
	}

	/**
	 * @param cubeMap2 the cubeMap2 to set
	 */
	public void setCubeMap2(String[] cubeMap2) {
		this.cubeMap2 = cubeMap2;
	}

	/**
	 * @return the factor
	 */
	public float getFactor() {
		return factor;
	}

	/**
	 * @param factor the factor to set
	 */
	public void setFactor(float factor) {
		this.factor = factor;
	}
	
	/**
	 * @return the lower
	 */
	public float getLower() {
		return lower;
	}

	/**
	 * @param lower the lower to set
	 */
	public void setLower(float lower) {
		this.lower = lower;
	}

	/**
	 * @return the upper
	 */
	public float getUpper() {
		return upper;
	}

	/**
	 * @param upper the upper to set
	 */
	public void setUpper(float upper) {
		this.upper = upper;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation % 360;
	}
	
}
