package renderEngine.postProcessing.gaussianBlur;

import renderEngine.ShaderProgram;

public class HorizontalGaussianBlurShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/postProcessing/gaussianBlur/horizontalGaussianBlurVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/postProcessing/gaussianBlur/gaussianBlurFragment.glsl";

	public HorizontalGaussianBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("width");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadTargetWidth(float width){
		super.loadFloat(getUniform("width"), width);
	}

}
