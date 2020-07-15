package renderEngine.postProcessing.brightness;

import models.data.Entity;
import renderEngine.ShaderProgram;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;

public class BrightnessEffect extends PostProcessingEffect {

	private float brightness;
	
	public BrightnessEffect(float brightness, Entity entity) {
		super(new BrightnessShader(), new ImageRenderer(true), entity);
		this.brightness = brightness;
	}

	@Override
	public void loadUniforms(ShaderProgram shader) {
		((BrightnessShader)shader).loadBrightness(brightness);
	}

	/**
	 * @return the brightness
	 */
	public float getBrightness() {
		return brightness;
	}

	/**
	 * @param brightness the brightness to set
	 */
	public void setBrightness(float brightness) {
		this.brightness = brightness;
	}
	
	
	
}
