package renderEngine.postProcessing.contrast;

import renderEngine.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/postProcessing/contrast/contrastVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/postProcessing/contrast/contrastFragment.glsl";
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("contrast");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadContrast(float contrast){
		super.loadFloat(getUniform("contrast"), contrast);
	}

}
