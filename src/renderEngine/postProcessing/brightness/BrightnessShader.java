package renderEngine.postProcessing.brightness;

import renderEngine.ShaderProgram;

public class BrightnessShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/renderEngine/postProcessing/brightness/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/renderEngine/postProcessing/brightness/brightnessFragment.glsl";
	
	public BrightnessShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("brightness");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadBrightness(float brightness) {
		loadFloat(getUniform("brightness"), brightness);
	}
	
}
