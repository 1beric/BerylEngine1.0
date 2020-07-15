package renderEngine.skybox;

import renderEngine.ShaderProgram;
import settings.Constants;
import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class SkyboxShader extends ShaderProgram {
	
	private float rotation;
	
	public SkyboxShader() {
		super((String)Constants.get("SKYBOX_VERTEX"), (String)Constants.get("SKYBOX_FRAGMENT"));
	}

	@Override
	public void addAllUniforms() {
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("fogColor");
		addUniform("lowerLimit");
		addUniform("upperLimit");
		addUniform("cubeMap1");
		addUniform("cubeMap2");
		addUniform("blendFactor");
	}
	
	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadBlendFactor(float blend) {
		super.loadFloat(getUniform("blendFactor"), blend);
	}
	
	public void connectTextureUnits() {
		super.loadInt(getUniform("cubeMap1"), 0);
		super.loadInt(getUniform("cubeMap2"), 1);
	}
	
	public void loadFogColor(BerylVector color) {
		super.loadVector3(getUniform("fogColor"), color);
	}
	
	public void loadFogLimits(float lower, float upper) {
		super.loadFloat(getUniform("lowerLimit"), lower);
		super.loadFloat(getUniform("upperLimit"), upper);
	}
	
	public void loadProjectionMatrix(BerylMatrix matrix) {
		super.loadMatrix(getUniform("projectionMatrix"), matrix);
	}

	public void loadViewMatrix(BerylVector pos, BerylVector rot) {
		BerylMatrix matrix = BerylMath.createViewMatrix(pos,rot);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix = matrix.multiply(BerylMatrix.rotate(rotation,0,1,0));
		super.loadMatrix(getUniform("viewMatrix"), matrix);
	}
	
	public void loadRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
