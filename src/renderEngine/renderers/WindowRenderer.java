package renderEngine.renderers;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import editor.Window;
import meshCreation.Loader;
import models.data.ModelData;
import renderEngine.models.RawModel;
import renderEngine.shaders.WindowShader;
import tools.math.BerylMath;

public class WindowRenderer {

	private final RawModel MODEL;
	private WindowShader shader;
	
	public WindowRenderer() {
		MODEL = Loader.loadToVAO(new ModelData(
				new float[] { -1,  1,  -1, -1,   1,  1,   1, -1 }, 
				2));
		this.shader = new WindowShader();
	}
	
	public void render(Window window) {
		start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		window.getTexture().bind();
		shader.loadTransformationMatrix(
				BerylMath.createTransformationMatrix(
						window.getTransform().getPos(), 
						window.getTransform().getScale()));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		window.getTexture().unbind();
		end();
	}
	
	private void start() {
		shader.bind();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(MODEL.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void end() {
		shader.unbind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}

}
