package renderEngine.postProcessing.gaussianBlur;

import models.data.Entity;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.ShaderProgram;
import renderEngine.shaders.VerticalGaussianBlurShader;
import tools.BerylDisplay;

public class VerticalGaussianBlur extends PostProcessingEffect {

	public VerticalGaussianBlur(float scale, Entity entity) {
		super(new VerticalGaussianBlurShader(), new ImageRenderer(true, scale), entity);
	}
	
	@Override
	public void loadUniforms(ShaderProgram shader) {
		VerticalGaussianBlurShader vgbShader = (VerticalGaussianBlurShader) shader;
		vgbShader.loadTargetHeight(BerylDisplay.getVPHeight());
	}

}
