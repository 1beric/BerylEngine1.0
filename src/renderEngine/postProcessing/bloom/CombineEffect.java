package renderEngine.postProcessing.bloom;

import org.lwjgl.opengl.GL13;

import renderEngine.models.Texture;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.shaders.CombineShader;
import renderEngine.shaders.ShaderProgram;

public class CombineEffect extends PostProcessingEffect {
	
	private float bloomAmount;
	
	public CombineEffect(float bloomAmount) {
		super(new CombineShader(), new ImageRenderer(true), null);
		CombineShader shader = (CombineShader) getShader();
		this.bloomAmount = bloomAmount;
		shader.bind();
		shader.connectTextureUnits();
		shader.unbind();
	}

	@Override
	public void loadUniforms(ShaderProgram shader) { }

	@Override
	public Texture[] render(Texture[] texsO) {
		getShader().bind();
		Texture[] texs = texsO.clone();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texs[0].bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		texs[1].bind();
		
		((CombineShader)getShader()).loadBloomFactor(bloomAmount);
		
		texs[0] = getRenderer().render();
		
		getShader().unbind();
		return texs;
	}

	/**
	 * @return the bloomAmount
	 */
	public float getBloomAmount() {
		return bloomAmount;
	}

	/**
	 * @param bloomAmount the bloomAmount to set
	 */
	public void setBloomAmount(float bloomAmount) {
		this.bloomAmount = bloomAmount;
	}

}
