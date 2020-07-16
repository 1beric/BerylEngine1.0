package renderEngine.renderers;

import org.lwjgl.opengl.GL11;

import meshCreation.fontMeshCreation.GUIText;
import renderEngine.shaders.TextShader;

public class TextRenderer {

	private TextShader shader;
	
	public TextRenderer() {
		shader = new TextShader();
	}

	public void render(GUIText text) {
		shader.bind();
		shader.loadTexture(text.getFont().getTextureAtlas());
		shader.loadTransparency(text.getTransparency());
		shader.loadColor(text.getColor());
		shader.loadTranslation(text.getPosition());
		text.getData().bind();
		text.getData().draw(GL11.GL_TRIANGLES);
		text.getData().unbind();
		shader.unbind();
	}

	public void cleanUp(){
		shader.cleanUp();
	}
	
}
