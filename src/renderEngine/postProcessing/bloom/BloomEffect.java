package renderEngine.postProcessing.bloom;

import models.data.Entity;
import renderEngine.ShaderProgram;
import renderEngine.models.Texture;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.postProcessing.gaussianBlur.CompoundGaussianBlur;

public class BloomEffect extends PostProcessingEffect {

	private BrightCutoffEffect brighten;
	private CompoundGaussianBlur blur;
	private CombineEffect combine;
	
	public BloomEffect(int levelOfDetail, float bloomAmount, Entity entity) {
		super(null, null, entity);
		blur = new CompoundGaussianBlur(levelOfDetail, null);
		brighten = new BrightCutoffEffect();
		combine = new CombineEffect(bloomAmount);
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }

	@Override
	public Texture render(Texture tex) {

		Texture highlights = brighten.render(tex);
		highlights = blur.render(highlights);
		Texture bloomed = combine.render(tex, highlights);
		
		return bloomed;
	}

	@Override
	public void cleanUp() {
		brighten.cleanUp();
		blur.cleanUp();
		combine.cleanUp();
	}

	
	
	
}
