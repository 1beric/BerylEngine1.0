package renderEngine.shaders;

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

}
