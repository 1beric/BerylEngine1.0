package renderEngine.shaders;

public class CombineShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/renderEngine/shaders/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/shaders/combineFragment.glsl";
	
	public CombineShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	public void addAllUniforms() {
		addUniform("colorTexture");
		addUniform("highlightTexture");
		addUniform("bloom");
	}
	
	public void connectTextureUnits() {
		super.loadInt(getUniform("colorTexture"), 0);
		super.loadInt(getUniform("highlightTexture"), 1);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadBloomFactor(float bloom) {
		loadFloat(getUniform("bloom"), bloom);
	}
	
}
