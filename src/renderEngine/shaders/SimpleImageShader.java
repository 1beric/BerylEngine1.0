package renderEngine.shaders;

import org.lwjgl.opengl.GL13;

import renderEngine.models.Texture;

public class SimpleImageShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/shaders/simpleVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/shaders/simpleImageFragment.glsl";
	
	public SimpleImageShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	public void addAllUniforms() { }

	public void loadTexture(Texture scene) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		scene.bind();		
	}

	
	
}
