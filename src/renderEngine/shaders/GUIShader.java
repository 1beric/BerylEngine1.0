package renderEngine.shaders;

import org.lwjgl.opengl.GL13;

import renderEngine.models.Texture;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class GUIShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/shaders/guiVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/shaders/guiFragment.glsl";

	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("transformationMatrix");
		addUniform("color");
		addUniform("transparency");
		addUniform("borderWidth");
		addUniform("borderRadius");
		addUniform("borderColor");
		addUniform("guiCenter");
		addUniform("guiScale");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadTransformation(BerylVector pos, BerylVector scale){
		super.loadMatrix(getUniform("transformationMatrix"), BerylMath.createTransformationMatrix(pos,scale));
		super.loadVector2(getUniform("guiCenter"), pos);
		super.loadVector2(getUniform("guiScale"), scale);
	}
	
	public void loadColor(BerylVector color){
		super.loadVector3(getUniform("color"), color);
	}
	
	public void loadTransparency(float t){
		super.loadFloat(getUniform("transparency"), t);
	}
	
	public void loadBorder(BerylVector width, BerylVector color, BerylVector radius){
		super.loadVector2(getUniform("borderWidth"), width);
		super.loadVector3(getUniform("borderColor"), color);
		super.loadVector2(getUniform("borderRadius"), radius);
	}
	
	public void loadTexture(Texture tex) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tex.bind();
	}

}
