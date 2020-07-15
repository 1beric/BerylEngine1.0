package models.data;

import renderEngine.models.Texture;
import tools.math.BerylVector;

public class Material3D extends Material {

	public static final Material3D defaultMaterial() {
		return new Material3D();
	}
	
	private String name;
	
	private Texture normalMap;
	private Texture specularAndEmissionMap;
	
	private float shineDamper;
	private float reflectivity;
	
	private boolean transparent;
	private boolean useFakeLighting;
	
	private int numberOfRows;
	private int textureIndex;
	
	public Material3D() {
		initDefault();
	}
	
	public Material3D(String name) {
		initDefault();
		this.name = name;
	}
	
	public Material3D(String name, BerylVector color) {
		initDefault();
		this.name = name;
		setColor(color);
	}
	
	public Material3D(String name, String textureName) {
		initDefault();
		this.name = name;
		setColorTexture(new Texture(textureName));
	}
	
	public Material3D(String name, BerylVector color, String textureName) {
		initDefault();
		this.name = name;
		setColor(color);
		setColorTexture(new Texture(textureName));
	}
	
	private void initDefault() {
		this.name = "Default Material";
		setColor(new BerylVector(1));
		setColorTexture(Texture.white());
		this.shineDamper = 1;
		this.reflectivity = 0;
		this.transparent = false;
		this.useFakeLighting = false;
		this.numberOfRows = 1;
		this.textureIndex = 0;
	}

	/**
	 * @return the shineDamper
	 */
	public float getShineDamper() {
		return shineDamper;
	}

	/**
	 * @param shineDamper the shineDamper to set
	 */
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	/**
	 * @return the reflectivity
	 */
	public float getReflectivity() {
		return reflectivity;
	}

	/**
	 * @param reflectivity the reflectivity to set
	 */
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	/**
	 * @return the hasTransparency
	 */
	public boolean isTransparent() {
		return transparent;
	}

	/**
	 * @param hasTransparency the hasTransparency to set
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	/**
	 * @return the useFakeLighting
	 */
	public boolean usesFakeLighting() {
		return useFakeLighting;
	}

	/**
	 * @param useFakeLighting the useFakeLighting to set
	 */
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	/**
	 * @return the numberOfRows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @param numberOfRows the numberOfRows to set
	 */
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public float getTextureXOffset() {
		int col = textureIndex%numberOfRows;
		return (float)col/(float)numberOfRows;
	}
	
	public float getTextureYOffset() {
		int row = textureIndex/numberOfRows;
		return (float)row/(float)numberOfRows;
	}

	/**
	 * @return the normalMap
	 */
	public Texture getNormalMap() {
		return normalMap;
	}

	/**
	 * @param normalMap the normalMap to set
	 */
	public void setNormalMap(Texture normalMap) {
		this.normalMap = normalMap;
	}

	/**
	 * @return the specularMap
	 */
	public Texture getSpecularAndEmissionMap() {
		return specularAndEmissionMap;
	}

	/**
	 * @param specularMap the specularMap to set
	 */
	public void setSpecularAndEmissionMap(Texture specularAndEmissionMap) {
		this.specularAndEmissionMap = specularAndEmissionMap;
	}
	
}
