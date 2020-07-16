package renderEngine.shaders;

import org.lwjgl.opengl.GL13;

import models.components.renderable.LightRC;
import renderEngine.models.Texture;
import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class EntityShader extends ShaderProgram {

	
	private final static String VERTEX_FILE = "src/renderEngine/shaders/entityVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/shaders/entityFragment.glsl";
	
	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	public void addAllUniforms() {
		addUniform("transformationMatrix");
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		
		addUniform("color");
		addUniform("modelTexture");
		addUniform("numberOfRows");
		addUniform("offset");
		
		addUniform("light");
		addUniform("pointLight");
		addUniform("lightColor");
		addUniform("attenuation");
		addUniform("shineDamper");
		addUniform("reflectivity");
		addUniform("useFakeLighting");
		
		addUniform("specularAndEmissionMap");
		addUniform("usesSpecularMap");
	}
	
	public void connectTextureUnits() {
		super.loadInt(getUniform("modelTexture"), 0);
		super.loadInt(getUniform("specularAndEmissionMap"), 1);
	}
	
	public void loadTransformationMatrix(BerylMatrix matrix) {
		super.loadMatrix(getUniform("transformationMatrix"), matrix);
	}

	public void loadProjectionMatrix(BerylMatrix matrix) {
		super.loadMatrix(getUniform("projectionMatrix"), matrix);
	}
	
	public void loadViewMatrix(BerylVector pos, BerylVector rot) {
		BerylMatrix viewMatrix = BerylMath.createViewMatrix(pos,rot);
		super.loadMatrix(getUniform("viewMatrix"), viewMatrix);
	}
	
	public void loadColor(BerylVector color) {
		super.loadVector3(getUniform("color"), color);
	}
	
	public void loadLight(LightRC light) {
		super.loadVector3(getUniform("lightColor"), light.getColor());
		if (light.isPoint()) super.loadVector3(getUniform("light"), light.getPos());
		else 				 super.loadVector3(getUniform("light"), light.getDirection());
		super.loadVector3(getUniform("attenuation"), light.getAttenuation());
		super.loadBoolean(getUniform("pointLight"), light.isPoint());
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(getUniform("reflectivity"), reflectivity);
		super.loadFloat(getUniform("shineDamper"), damper);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(getUniform("numberOfRows"), numberOfRows);
	}
	
	public void loadOffset(float x, float y) {
		super.loadVector2(getUniform("offset"), new BerylVector(x,y));
	}

	public void loadFakeLightingVariable(boolean usesFakeLighting) {
		super.loadBoolean(getUniform("useFakeLighting"), usesFakeLighting);
	}
	
	public void loadTextures(Texture colorMap, Texture specularAndEmissionMap) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		colorMap.bind();
		if (specularAndEmissionMap != null) {
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			specularAndEmissionMap.bind();
			super.loadBoolean(getUniform("usesSpecularMap"), true);
		} else {
			super.loadBoolean(getUniform("usesSpecularMap"), false);
		}
	}
	
}
