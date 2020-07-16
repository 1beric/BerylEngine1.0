package game;
import java.io.File;

import org.lwjgl.Version;

import editor.Editor;
import guiSystem.animations.Animation;
import guiSystem.elements.Mesh2RC;
import guiSystem.elements.TextGUI;
import meshCreation.Loader;
import meshCreation.fontMeshCreation.FontType;
import meshCreation.fontMeshCreation.GUIText;
import meshCreation.fontMeshCreation.TextMeshData;
import models.Scene;
import models.components.renderable.Transform2D;
import models.data.ModelData;
import renderEngine.MasterRenderer;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import settings.Constants;
import settings.PreloadLookupTable;
import tools.BerylDisplay;
import tools.BerylGL;
import tools.BerylTime;
import tools.input.BerylInputSystem;
import tools.input.BerylMouse;

public class BerylApplication {

	public static void main(String[] args) {
		new BerylApplication().run();
	}

	private static final String SCENE_FILE = "src/game/basic.bscn";
	
	private Scene scene;
	private Editor editor;
	
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
		// Initialize the Editor
		editor = new Editor();
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
		Transform2D t = editor.getGameView().getTransform();
		BerylInputSystem.update(t.getPos(),t.getScale());
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
		editor.getGameView().setTexture(MasterRenderer.render(scene));
		
		
		// final render
		MasterRenderer.render(editor.getGameView());
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
				LookupTable.putFont(text.getFont(), new FontType(Loader.loadTexture(text.getFont()), new File("res/" + text.getFont() + ".fnt")));
			FontType font = LookupTable.getFont(text.getFont());
			GUIText guiText = new GUIText(text, font);
			TextMeshData data = font.loadText(guiText);
			RawModel model = Loader.loadToVAO(new ModelData(data.getVertexPositions(), data.getTextureCoords()));
			LookupTable.putRawModel(text, model);
			text.setUpdated(false);
		}
	}
	
}
