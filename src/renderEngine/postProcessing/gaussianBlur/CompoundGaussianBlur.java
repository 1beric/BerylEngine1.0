package renderEngine.postProcessing.gaussianBlur;

import java.util.ArrayList;
import java.util.List;

import models.data.Entity;
import renderEngine.models.Texture;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.ShaderProgram;

public class CompoundGaussianBlur extends PostProcessingEffect {

	private List<GaussianBlur> blurs;
	
	public CompoundGaussianBlur(int levelOfDetail, Entity entity) {
		super(null, null, entity);
		blurs = new ArrayList<>();
		for (int i=0; i<levelOfDetail; i++) {
			blurs.add(new GaussianBlur((float)Math.pow(2,-i), entity));
		}
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }

	@Override
	public Texture render(Texture tex) {
		for (GaussianBlur blur : blurs) tex = blur.render(tex);
		return tex;
	}

	@Override
	public void cleanUp() {
		blurs.forEach(blur->blur.cleanUp());
	}
}
