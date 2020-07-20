package renderEngine.postProcessing.bloom;

import models.data.Entity;
import renderEngine.models.Texture;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.postProcessing.gaussianBlur.CompoundGaussianBlur;
import renderEngine.shaders.ShaderProgram;

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
	public Texture[] render(Texture[] texsO) {
		Texture[] texs = texsO.clone();
		texs[1] = blur.render(new Texture[] {texs[1], null})[0];
		texs = combine.render(texs);
		
		return texs;
	}

	@Override
	public void cleanUp() {
		brighten.cleanUp();
		blur.cleanUp();
		combine.cleanUp();
	}

	
	
	
}
