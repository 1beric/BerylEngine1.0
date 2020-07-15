package renderEngine.gameView;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.data.ModelData;
import renderEngine.models.RawModel;
import renderEngine.models.Texture;
import renderEngine.shaders.GameViewShader;
import renderEngine.Loader;

public class GameViewRenderer {

	private final RawModel MODEL;
	private GameViewShader gameViewShader;
	
	public GameViewRenderer() {
		MODEL = Loader.loadToVAO(new ModelData(
				new float[] { -1,  1,  -1, -1,   1,  1,   1, -1 }, 
				2));
		this.gameViewShader = new GameViewShader();
	}
	
	public void render(Texture tex) {
		start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tex.bind();
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		tex.unbind();
		end();
	}
	
	private void start() {
		gameViewShader.bind();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(MODEL.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void end() {
		gameViewShader.unbind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		gameViewShader.cleanUp();
	}

}
