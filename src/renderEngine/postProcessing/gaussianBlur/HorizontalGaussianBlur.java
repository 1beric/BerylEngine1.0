package renderEngine.postProcessing.gaussianBlur;

import models.data.Entity;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.HorizontalGaussianBlurShader;
import renderEngine.shaders.ShaderProgram;
import tools.io.BerylDisplay;

public class HorizontalGaussianBlur extends PostProcessingEffect {

	public HorizontalGaussianBlur(float scale, Entity entity) {
		super(new HorizontalGaussianBlurShader(), new ImageRenderer(true, scale), entity);
	}
	
	@Override
	public void loadUniforms(ShaderProgram shader) {
		HorizontalGaussianBlurShader hgbShader = (HorizontalGaussianBlurShader) shader;
		hgbShader.loadTargetWidth(BerylDisplay.getVPWidth());
	}

}
