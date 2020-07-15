package game;
import java.io.File;

import org.lwjgl.Version;

import guiSystem.animations.Animation;
import guiSystem.elements.Mesh2RC;
import guiSystem.elements.TextGUI;
import models.Scene;
import models.data.ModelData;
import renderEngine.BerylDisplay;
import renderEngine.BerylGL;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.fontMeshCreator.FontType;
import renderEngine.fontMeshCreator.GUIText;
import renderEngine.fontMeshCreator.TextMeshData;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import renderEngine.models.Texture;
import settings.Constants;
import settings.PreloadLookupTable;
import tools.BerylTime;
import tools.input.BerylInputSystem;
import tools.math.BerylVector;

public class BerylApplication {

	public static void main(String[] args) {
		new BerylApplication().run();
	}

	private static final String SCENE_FILE = "src/game/basic.bscn";
	
	private Scene scene;
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();
		close();
		
	}

	/**
	 * Initialize the applicaiton and create the display.
	 */
	private void init() {
		// Create the display.
		BerylDisplay.createDisplay();
		// Initialize the openGL context
		BerylGL.init();
		// Load constants to use across files
		Constants.loadConstants("src/settings/constants.txt");
		// Pre-load .objs to the LookupTable
		PreloadLookupTable.preload("src/settings/preloads.txt");
		// Initialize the time unit
		BerylTime.init();
		// Initialize the MasterRenderer
		MasterRenderer.init();
		// Initialize the Input System
		BerylInputSystem.init();
		// Initialize the FrameBuffer
		// Construct the scene and initialize it from a .bscn file
		scene = new Scene();
		scene.initFromFile(SCENE_FILE);
		scene.onInit();
	}

	private void loop() {
		// Run the frame-loop until the user has requested to close the window
		while (BerylDisplay.isOpen()) {
			logicUpdate();
			renderUpdate();
		}
	}
	
	private void logicUpdate() {
		BerylInputSystem.update();
		BerylTime.updateDelta();
		scene.onUpdate();
		scene.onLateUpdate();
		Animation.updateAnimations();
		scene.getMesh2RCs().forEach(mesh->reloadText(mesh));
	}

	private void renderUpdate() {
		renderByFBO();
		BerylDisplay.updateDisplay();
		BerylGL.glErrorHandling();
	}
	
	
	private void renderByFBO() {
		Texture tex = MasterRenderer.render(scene);
		
		
		// final render
		MasterRenderer.render(tex, BerylVector.zero(), BerylVector.one(2));
	}

	private void close() {
		MasterRenderer.cleanUp();
		Loader.cleanUp();
		scene.getPostProcessingEffects().forEach(effect->effect.cleanUp());
		BerylDisplay.closeDisplay();
		System.exit(0);
	}
	
	
	private void reloadText(Mesh2RC gui) {
		if (gui == null || !gui.isActive()) return;
		gui.getChildren().forEach(child->reloadText(child));
		gui.getTexts().forEach(child->reloadText(child));
		if (!(gui instanceof TextGUI)) return;
		TextGUI text = (TextGUI) gui;
		if (text.isUpdated()) {
			if (LookupTable.containsRawModel(text)) Loader.unloadVAO(LookupTable.getRawModel(text).getVaoID());
			if (!LookupTable.containsFont(text.getFont())) 
				LookupTable.putFont(text.getFont(), new FontType(Loader.loadTexture(text.getFont()).getTextureID(), new File("res/" + text.getFont() + ".fnt")));
			FontType font = LookupTable.getFont(text.getFont());
			GUIText guiText = new GUIText(text, font);
			TextMeshData data = font.loadText(guiText);
			RawModel model = Loader.loadToVAO(new ModelData(data.getVertexPositions(), data.getTextureCoords()));
			LookupTable.putRawModel(text, model);
			text.setUpdated(false);
		}
	}
	
}
