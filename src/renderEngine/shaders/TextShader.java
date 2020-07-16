package renderEngine.shaders;

import org.lwjgl.opengl.GL13;

import renderEngine.models.Texture;
import tools.math.BerylVector;

public class TextShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/renderEngine/shaders/textVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/shaders/textFragment.glsl";
	
	public TextShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("color");
		addUniform("translation");
		addUniform("fontAtlas");
		addUniform("transparency");
	}
	
	public void loadTranslation(BerylVector vec) {
		super.loadVector2(getUniform("translation"), vec);
	}

	public void loadColor(BerylVector color) {
		super.loadVector3(getUniform("color"), color);
	}

	public void loadTransparency(float t) {
		super.loadFloat(getUniform("transparency"), t);
	}
	
	public void bindTextureAtlas() {
		super.loadInt(getUniform("fontAtlas"), 0);
	}
	
	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadTexture(Texture textureAtlas) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		textureAtlas.bind();	
	}


}
