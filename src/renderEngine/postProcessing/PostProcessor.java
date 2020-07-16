package renderEngine.postProcessing;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import meshCreation.Loader;
import models.data.ModelData;
import renderEngine.models.RawModel;
import renderEngine.models.Texture;

public class PostProcessor {

	private final RawModel MODEL;
	
	public PostProcessor() {
		MODEL = Loader.loadToVAO(new ModelData(
				new float[] { -1,  1,  -1, -1,   1,  1,   1, -1 }, 
				2));
	}
	
	public Texture render(Texture tex, List<PostProcessingEffect> effects) {
		start();
		
		// render effects in order
		for (PostProcessingEffect effect : effects) tex = effect.render(tex);
		
		end();
		return tex;
	}
	
	private void start() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(MODEL.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
}
