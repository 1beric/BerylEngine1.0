package renderEngine.postProcessing.bloom;

import renderEngine.ShaderProgram;

public class CombineShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/renderEngine/postProcessing/bloom/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/postProcessing/bloom/combineFragment.glsl";
	
	protected CombineShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	public void addAllUniforms() {
		addUniform("colorTexture");
		addUniform("highlightTexture");
		addUniform("bloom");
	}
	
	protected void connectTextureUnits() {
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
