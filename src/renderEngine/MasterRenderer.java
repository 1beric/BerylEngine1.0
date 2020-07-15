package renderEngine;

import org.lwjgl.opengl.GL11;

import models.Scene;
import renderEngine.entities.EntityRenderer;
import renderEngine.gameView.GameViewRenderer;
import renderEngine.gui.GUIRenderer;
import renderEngine.models.FrameBuffer;
import renderEngine.models.Texture;
import renderEngine.models.FrameBuffer.DepthBuffer;
import renderEngine.postProcessing.PostProcessor;
import renderEngine.skybox.SkyboxRenderer;
import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class MasterRenderer {

	private static EntityRenderer entityRenderer;
	private static GUIRenderer guiRenderer;
	private static SkyboxRenderer skyboxRenderer;
	private static GameViewRenderer gameViewRenderer;
	private static PostProcessor postProcessor;
	private static BerylMatrix projectionMatrix;
	
	private static FrameBuffer fboMS;
	private static FrameBuffer fbo;
	
	public static void init() {
		projectionMatrix		= BerylMath.getProjectionMatrix();
		entityRenderer 			= new EntityRenderer(projectionMatrix);
		skyboxRenderer 			= new SkyboxRenderer(projectionMatrix);
		guiRenderer 			= new GUIRenderer();
		postProcessor 			= new PostProcessor();
		gameViewRenderer 		= new GameViewRenderer();
		
		fboMS = new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER);
		fbo = new FrameBuffer(DepthBuffer.DEPTH_TEXTURE);
	}
	
	/**
	 * renders the scene
	 * @param scene
	 */
	public static Texture render(Scene scene) {
		
		fboMS.bind();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glClearColor(scene.getSky().getColor().x,scene.getSky().getColor().y,scene.getSky().getColor().z,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		
		skyboxRenderer.render(scene.getCam(),scene.getSky());
		entityRenderer.render(scene.getMesh3RCs(),scene.getLight(),scene.getCam());
		fboMS.unbind();
		
		fboMS.resolve(fbo);
		
		Texture tex = postProcessor.render(fbo.getTexture(), scene.getPostProcessingEffects());
		
		fbo.bind();
		gameViewRenderer.render(tex);
		guiRenderer.render(scene.getMesh2RCs());
		fbo.unbind();
		
		return fbo.getTexture();
	}
	
	/**
	 * used for redering framebuffer to the gameView
	 */
	public static void render(Texture tex, BerylVector pos, BerylVector scale) {
		gameViewRenderer.render(tex);
	}

	public static void cleanUp() {
		entityRenderer.cleanUp();
		guiRenderer.cleanUp();
		gameViewRenderer.cleanUp();
		fboMS.cleanUp();
		fbo.cleanUp();
	}

	public static BerylMatrix getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public static void resetProjectionMatrix() {
		projectionMatrix= BerylMath.getProjectionMatrix();
		entityRenderer.setProjectionMatrix(projectionMatrix);
		skyboxRenderer.setProjectionMatrix(projectionMatrix);
		
	}

//	private void setTextUpdated(Mesh2RC gui) {
//		gui.getChildren().forEach(child->setTextUpdated(child));
//		gui.getTexts().forEach(child->setTextUpdated(child));
//		if (!(gui instanceof TextGUI)) return;
//		((TextGUI) gui).setUpdated(true);
//	}
	
}
