package renderEngine.postProcessing.contrast;

import models.data.Entity;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.ContrastShader;
import renderEngine.shaders.ShaderProgram;

public class ContrastEffect extends PostProcessingEffect {

	private float contrast;
	
	public ContrastEffect(float contrast, Entity entity) {
		super(new ContrastShader(), new ImageRenderer(true), entity);
		this.contrast = contrast;
	}

	@Override
	public void loadUniforms(ShaderProgram shader) {
		ContrastShader contrastShader = (ContrastShader)shader;
		contrastShader.loadContrast(contrast);
	}

	/**
	 * @return the contrast
	 */
	public float getContrast() {
		return contrast;
	}

	/**
	 * @param contrast the contrast to set
	 */
	public void setContrast(float contrast) {
		this.contrast = contrast;
	}
	
}
