package renderEngine;

import editor.GameView;
import models.Scene;
import renderEngine.models.FrameBuffer;
import renderEngine.models.Texture;
import renderEngine.models.FrameBuffer.DepthBuffer;
import renderEngine.postProcessing.PostProcessor;
import renderEngine.renderers.EntityRenderer;
import renderEngine.renderers.GUIRenderer;
import renderEngine.renderers.SkyboxRenderer;
import renderEngine.renderers.WindowRenderer;
import tools.BerylGL;
import tools.math.BerylMath;
import tools.math.BerylMatrix;

/**
 * The default state of GL settings should be
 * <p>
 * 	- depth on <br>
 * 	- culling on <br>
 *  - blend off <br>
 *  </p>
 * @author Brandon Erickson
 *
 */
public class MasterRenderer {

	private static EntityRenderer entityRenderer;
	private static GUIRenderer guiRenderer;
	private static SkyboxRenderer skyboxRenderer;
	private static WindowRenderer windowRenderer;
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
		windowRenderer 			= new WindowRenderer();
		
		fboMS = new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER, true);
		fbo = new FrameBuffer(DepthBuffer.DEPTH_TEXTURE, false);
	}
	
	/**
	 * renders the scene
	 * @param scene
	 */
	public static Texture render(Scene scene) {
		Texture[] sceneImage = renderScene(scene);
		return guiRenderer.render(scene.getMesh2RCs(), sceneImage);
	}
	
	private static Texture[] renderScene(Scene scene) {
		fboMS.bind();
		BerylGL.enableDepthTest();
		BerylGL.enableCulling();
		BerylGL.clearColor(scene.getSky().getColor());
		BerylGL.clear();
		skyboxRenderer.render(scene.getCam(),scene.getSky());
		entityRenderer.render(scene.getMesh3RCs(),scene.getLight(),scene.getCam());
		fboMS.unbind();
		fboMS.resolve(fbo);
		return postProcessor.render(fbo.getTexture(), scene.getPostProcessingEffects());
	}
	
	/**
	 * used for redering framebuffer to the gameView
	 */
	public static void render(GameView gameView) {
		windowRenderer.render(gameView);
	}

	public static void cleanUp() {
		entityRenderer.cleanUp();
		guiRenderer.cleanUp();
		windowRenderer.cleanUp();
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
