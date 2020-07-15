package renderEngine.postProcessing;

import org.lwjgl.opengl.GL13;

import models.components.renderable.BerylRC;
import models.data.Entity;
import renderEngine.ShaderProgram;
import renderEngine.models.Texture;

public abstract class PostProcessingEffect extends BerylRC {

	private ShaderProgram shader;
	private ImageRenderer renderer;
	
	public PostProcessingEffect(ShaderProgram shader, ImageRenderer renderer, Entity entity) {
		super(entity);
		this.shader = shader;
		this.renderer = renderer;
	}
	
	public abstract void loadUniforms(ShaderProgram shader);
	
	public Texture render(Texture tex) {
		shader.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tex.bind();
		loadUniforms(shader);
		Texture out = renderer.render();
		tex.unbind();
		shader.unbind();
		return out;
	}
	
	public ShaderProgram getShader() {
		return shader;
	}
	
	public ImageRenderer getRenderer() {
		return renderer;
	}
	
	public void cleanUp() {
		shader.cleanUp();
		renderer.cleanUp();
	}
	
}
