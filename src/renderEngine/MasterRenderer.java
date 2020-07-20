package renderEngine;

import org.lwjgl.opengl.GL30;

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
	private static FrameBuffer fboScene;
	private static FrameBuffer fboHighlights;
	
	public static void init() {
		projectionMatrix		= BerylMath.getProjectionMatrix();
		entityRenderer 			= new EntityRenderer(projectionMatrix);
		skyboxRenderer 			= new SkyboxRenderer(projectionMatrix);
		guiRenderer 			= new GUIRenderer();
		postProcessor 			= new PostProcessor();
		windowRenderer 			= new WindowRenderer();
		
		fboMS = new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER, true);
		fboScene = new FrameBuffer(DepthBuffer.DEPTH_TEXTURE, false);
		fboHighlights = new FrameBuffer(DepthBuffer.DEPTH_TEXTURE, false);
	}
	
	/**
	 * renders the scene
	 * @param scene
	 */
	public static Texture render(Scene scene) {
		Texture sceneImage = renderScene(scene);
		return guiRenderer.render(scene.getMesh2RCs(), sceneImage);
	}
	
	private static Texture renderScene(Scene scene) {
		fboMS.bind();
		BerylGL.enableDepthTest();
		BerylGL.enableCulling();
		BerylGL.clearColor(scene.getSky().getColor());
		BerylGL.clear();
		skyboxRenderer.render(scene.getCam(),scene.getSky());
		entityRenderer.render(scene.getMesh3RCs(),scene.getLight(),scene.getCam());
		fboMS.unbind();
		fboMS.resolve(GL30.GL_COLOR_ATTACHMENT0, fboScene);
		fboMS.resolve(GL30.GL_COLOR_ATTACHMENT1, fboHighlights);
		return postProcessor.render(new Texture[] { fboScene.getTexture(), fboHighlights.getTexture() }, scene.getPostProcessingEffects());
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
		fboScene.cleanUp();
		fboHighlights.cleanUp();
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
