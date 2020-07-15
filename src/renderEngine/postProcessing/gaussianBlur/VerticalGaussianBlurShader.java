package renderEngine.postProcessing.gaussianBlur;

import renderEngine.ShaderProgram;

public class VerticalGaussianBlurShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/postProcessing/gaussianBlur/verticalGaussianBlurVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/postProcessing/gaussianBlur/gaussianBlurFragment.glsl";
	
	public VerticalGaussianBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("height");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadTargetHeight(float height){
		super.loadFloat(getUniform("height"), height);
	}

}
