package renderEngine.postProcessing;

import org.lwjgl.opengl.GL13;

import models.components.renderable.BerylRC;
import models.data.Entity;
import renderEngine.models.Texture;
import renderEngine.shaders.ShaderProgram;

public abstract class PostProcessingEffect extends BerylRC {

	private ShaderProgram shader;
	private ImageRenderer renderer;
	
	public PostProcessingEffect(ShaderProgram shader, ImageRenderer renderer, Entity entity) {
		super(entity);
		this.shader = shader;
		this.renderer = renderer;
	}
	
	public abstract void loadUniforms(ShaderProgram shader);
	
	public Texture[] render(Texture[] texsO) {
		Texture[] texs = texsO.clone();
		shader.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texs[0].bind();
		loadUniforms(shader);
		texs[0] = renderer.render();
		shader.unbind();
		return texs;
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
