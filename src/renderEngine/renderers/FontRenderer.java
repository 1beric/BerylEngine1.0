package renderEngine.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import meshCreation.fontMeshCreation.GUIText;
import renderEngine.shaders.FontShader;

public class FontRenderer {

	private FontShader shader;
	
	public FontRenderer() {
		shader = new FontShader();
	}

	public void cleanUp(){
		shader.cleanUp();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.bind();
	}
	
	public void render(GUIText text) {
		prepare();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		text.getFont().getTextureAtlas().bind();
		text.getData().bind();
		shader.loadTransparency(text.getTransparency());
		shader.loadColor(text.getColor());
		shader.loadTranslation(text.getPosition());
		text.getData().draw();
		text.getData().unbind();
		endRendering();
	}
	
	private void endRendering() {
		shader.unbind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
