package renderEngine.postProcessing.gaussianBlur;

import models.data.Entity;
import renderEngine.BerylDisplay;
import renderEngine.ShaderProgram;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;

public class HorizontalGaussianBlur extends PostProcessingEffect {

	public HorizontalGaussianBlur(float scale, Entity entity) {
		super(new HorizontalGaussianBlurShader(), new ImageRenderer(true, scale), entity);
	}
	
	@Override
	public void loadUniforms(ShaderProgram shader) {
		HorizontalGaussianBlurShader hgbShader = (HorizontalGaussianBlurShader) shader;
		hgbShader.loadTargetWidth(BerylDisplay.getVpWidth());
	}

}
