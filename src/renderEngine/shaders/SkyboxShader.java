package renderEngine.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class SkyboxShader extends ShaderProgram {
	
	private float rotation;
	
	public SkyboxShader() {
		super("src/renderEngine/shaders/skyboxVertex.glsl", "src/renderEngine/shaders/skyboxFragment.glsl");
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
	
	public void bindTextures(int tex1, int tex2, float factor) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, tex1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, tex2);
		super.loadFloat(getUniform("blendFactor"), factor);
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
		matrix.m03 = 0;
		matrix.m13 = 0;
		matrix.m23 = 0;
		matrix = matrix.multiply(BerylMatrix.rotate(rotation,0,1,0));
		super.loadMatrix(getUniform("viewMatrix"), matrix);
	}
	
	public void loadRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
