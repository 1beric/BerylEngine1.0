package renderEngine.postProcessing.gaussianBlur;

import models.data.Entity;
import renderEngine.ShaderProgram;
import renderEngine.models.Texture;
import renderEngine.postProcessing.PostProcessingEffect;

public class GaussianBlur extends PostProcessingEffect {

	private HorizontalGaussianBlur 	horizontalGaussianBlur;
	private VerticalGaussianBlur 	verticalGaussianBlur;
	
	public GaussianBlur(float scale, Entity entity) {
		super(null, null, entity);
		horizontalGaussianBlur 	= new HorizontalGaussianBlur(scale, null);
		verticalGaussianBlur 	= new VerticalGaussianBlur(scale, null);
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }

	@Override
	public Texture render(Texture tex) {
		Texture blurredH = horizontalGaussianBlur.render(tex);
		return verticalGaussianBlur.render(blurredH);
	}

	@Override
	public void cleanUp() {
		horizontalGaussianBlur.cleanUp();
		verticalGaussianBlur.cleanUp();
	}

}
