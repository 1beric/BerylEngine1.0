package renderEngine.shaders;

public class BrightCutoffShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/renderEngine/shaders/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/shaders/brightCutoffFragment.glsl";
	
	public BrightCutoffShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() { }

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
