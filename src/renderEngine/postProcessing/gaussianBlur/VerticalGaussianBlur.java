package renderEngine.postProcessing.gaussianBlur;

import models.data.Entity;
import renderEngine.BerylDisplay;
import renderEngine.ShaderProgram;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;

public class VerticalGaussianBlur extends PostProcessingEffect {

	public VerticalGaussianBlur(float scale, Entity entity) {
		super(new VerticalGaussianBlurShader(), new ImageRenderer(true, scale), entity);
	}
	
	@Override
	public void loadUniforms(ShaderProgram shader) {
		VerticalGaussianBlurShader vgbShader = (VerticalGaussianBlurShader) shader;
		vgbShader.loadTargetHeight(BerylDisplay.getVpHeight());
	}

}
