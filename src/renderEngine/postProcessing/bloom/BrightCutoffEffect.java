package renderEngine.postProcessing.bloom;

import renderEngine.ShaderProgram;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;

public class BrightCutoffEffect extends PostProcessingEffect {

	public BrightCutoffEffect() {
		super(new BrightCutoffShader(), new ImageRenderer(true), null);
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }
	
}
