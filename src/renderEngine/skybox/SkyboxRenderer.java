package renderEngine.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.components.renderable.CameraRC;
import models.components.renderable.SkyboxRC;
import models.data.ModelData;
import renderEngine.Loader;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import settings.Constants;
import tools.math.BerylMath;
import tools.math.BerylMatrix;

public class SkyboxRenderer {

	private static final float SIZE = (float)Constants.get("SKYBOX_SIZE");


	private RawModel cube;
	private SkyboxShader shader;
	
	public SkyboxRenderer(BerylMatrix projectionMatrix) {
		cube = Loader.loadToVAO(new ModelData(VERTICES, 3));
		shader = new SkyboxShader();
		shader.bind();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.unbind();
	}
	
	public void render(CameraRC cam, SkyboxRC sky) {
		if (shader.isRecompiled()) {
			shader.bind();
			shader.connectTextureUnits();
			shader.loadProjectionMatrix(BerylMath.getProjectionMatrix());
			shader.unbind();
			shader.setRecompiled(false);
		}
		shader.bind();
		shader.loadRotation(sky.getRotation());
		shader.loadViewMatrix(cam.getPos(),cam.getRot());
		shader.loadFogColor(sky.getColor());
		shader.loadFogLimits(sky.getLower(), sky.getUpper());
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures(LookupTable.getTexture(sky.getCubeMap1()).getTextureID(),LookupTable.getTexture(sky.getCubeMap2()).getTextureID(), sky.getFactor());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.unbind();
	}
	
	private void bindTextures(int tex1, int tex2, float factor) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, tex1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, tex2);
		shader.loadBlendFactor(factor);
	}
	
	public void setProjectionMatrix(BerylMatrix projectionMatrix) {
		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.unbind();
	}
	
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};
	
}
