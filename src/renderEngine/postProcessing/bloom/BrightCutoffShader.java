package renderEngine.postProcessing.bloom;

import renderEngine.ShaderProgram;

public class BrightCutoffShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/renderEngine/postProcessing/bloom/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/postProcessing/bloom/brightCutoffFragment.glsl";
	
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
