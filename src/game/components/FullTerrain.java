package game.components;

import java.util.List;

import guiSystem.elements.Mesh2RC;
import models.Scene;
import models.components.BerylComponent;
import models.components.updatable.BerylUC;
import models.data.Entity;
import settings.Constants;
import tools.MouseHoverState;
import tools.input.BerylKeyboard;
import tools.input.BerylMouse;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class FullTerrain extends BerylUC {

	public FullTerrain(Entity entity) {
		super(entity);
	}

	private static final int MAX_LEVELS = (int)Constants.get("TERRAIN_LEVELS"); 

	private List<Terrain> smallestPieces;
	private Terrain topLevelTerrain;
	private int currentLevel;
	private MouseHoverState mouseState;
	private MouseHoverState prevMouseState;
	
	@Override
	public void onInit() {
		this.topLevelTerrain = new Terrain(1, null, new BerylVector(0,(float)Constants.get("TERRAIN_HEIGHT"),0));
		this.smallestPieces = topLevelTerrain.getChildren();
		this.smallestPieces.forEach(piece->{
//			getEntity().getScene().addEntity(piece);
//			piece.setAddEntity(e->getEntity().getScene().addEntity(e));
//			piece.setRemoveEntity(e->getEntity().getScene().removeEntity(e));
		});
		currentLevel = MAX_LEVELS;
		mouseState	 	= MouseHoverState.GUI;
		prevMouseState 	= MouseHoverState.GUI;
	}

	@Override
	public void onUpdate() {
		// check hover state and process
//		updateState(getEntity().getScene());
//		if (mouseState == MouseHoverState.GUI) {
//			if (prevMouseState == MouseHoverState.WORLD) topLevelTerrain.unhighlight();
//			highlight(BerylMouse.getCurrentTerrainPoint());
//		} else if (mouseState == MouseHoverState.WORLD) {
//			updateLevel();
//			highlight(BerylMouse.getCurrentTerrainPoint());
//			if (BerylMouse.getButtonDown(0)) topLevelTerrain.testLeftClicked(BerylMouse.getCurrentTerrainPoint(),currentLevel);
//			if (BerylMouse.getButtonDown(1)) topLevelTerrain.testRightClicked(BerylMouse.getCurrentTerrainPoint(),currentLevel);
//		}
	}
	
	@Override
	public void onLateUpdate() { }
	
	public List<Terrain> getSmallestPieces() {
		return smallestPieces;
	}
	
	private void highlight(BerylVector selectedPos) {
//		topLevelTerrain.testUnhighlight(selectedPos, currentLevel);
//		topLevelTerrain.testHighlight(selectedPos, currentLevel);
	}

	private void updateState(Scene scene) {
//		if (!BerylMouse.getButtonHeld(2)) {
//			prevMouseState = mouseState;
//			for (GUIElement gui : scene.getMesh2RCs()) if (gui.isActive() && gui.contains(BerylMouse.getScreenRay())) {
//				mouseState = MouseHoverState.GUI;
//				return;
//			}
//			mouseState = MouseHoverState.WORLD;
//		}
	}
	
	private void updateLevel() {
//		if (BerylKeyboard.isKeyDown(Keyboard.KEY_1)) currentLevel = 1;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_2)) currentLevel = 2;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_3)) currentLevel = 3;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_4)) currentLevel = 4;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_5)) currentLevel = 5;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_6)) currentLevel = 6;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_7)) currentLevel = 7;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_8)) currentLevel = 8;
//		else if (BerylKeyboard.isKeyDown(Keyboard.KEY_9)) currentLevel = 9;
//		currentLevel = (int) BerylMath.clamp(currentLevel,1f,MAX_LEVELS);
	}

}
