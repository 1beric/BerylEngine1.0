package renderEngine.postProcessing.bloom;

import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.BrightCutoffShader;
import renderEngine.shaders.ShaderProgram;

public class BrightCutoffEffect extends PostProcessingEffect {

	public BrightCutoffEffect() {
		super(new BrightCutoffShader(), new ImageRenderer(true), null);
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }
	
}
