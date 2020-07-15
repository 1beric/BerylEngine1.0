package game.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import guiSystem.animations.Animation;
import guiSystem.animations.ColorAnimation;
import guiSystem.animations.PositionAnimation;
import models.components.renderable.Mesh3RC;
import models.data.Entity;
import models.data.Material3D;
import renderEngine.models.LookupTable;
import settings.Constants;
import tools.math.BerylVector;

public class Terrain extends Entity {

	private static final int MAX_LEVELS = (int)Constants.get("TERRAIN_LEVELS"); 
	private static final float SIZE = (float)Constants.get("TERRAIN_SIZE");
	private static final float HEIGHT = (float)Constants.get("TERRAIN_HEIGHT");
	private static final float HEIGHT_OFFSET = (float)Constants.get("TERRAIN_HEIGHT_OFFSET");
	private static final float HOVER_ANIM_LENGTH = (float)Constants.get("HOVER_ANIM_LENGTH");
	private static final BerylVector COLOR = (BerylVector)Constants.get("TERRAIN_COLOR");
	private static final BerylVector SELECTED_COLOR = (BerylVector)Constants.get("TERRAIN_SELECTED_COLOR");
	private static final String TILE_OBJ = (String)Constants.get("TERRAIN_TILE");
	private static final String SPAWNED_OBJECT = (String)Constants.get("TABLE");
	
	private int level;
	private Terrain parent;
	private Terrain[] nextLevel;

	private float width;
	private boolean highlighted;
	
	private Animation[] anims;

	public Terrain(int level, Terrain parent, BerylVector pos) {
		super("Terrain ["+pos.x+","+pos.y+"]");
		getTransform().setPos(new BerylVector(pos.x,HEIGHT,pos.z));
		this.level = level;
		this.parent = parent;
		if (level < MAX_LEVELS) {
			float off = (float) (SIZE / Math.pow(2, level+1));
			nextLevel = new Terrain[] {
				new Terrain(level+1,this,new BerylVector(pos.x+off,pos.y,pos.z+off)),
				new Terrain(level+1,this,new BerylVector(pos.x+off,pos.y,pos.z-off)),
				new Terrain(level+1,this,new BerylVector(pos.x-off,pos.y,pos.z-off)),
				new Terrain(level+1,this,new BerylVector(pos.x-off,pos.y,pos.z+off))
			};
		} else nextLevel = new Terrain[0];
		
		Mesh3RC mesh = new Mesh3RC(this);
		mesh.setData(LookupTable.getRawModel(TILE_OBJ).getData());
		mesh.setMat(new Material3D(getName() + " mat",COLOR));
		
		this.width = (float) (SIZE / Math.pow(2,level-1));
//		if (level == MAX_LEVELS) this.anims = new Animation[] {
//				new PositionAnimation(HOVER_ANIM_LENGTH, new float[] {pos.x,HEIGHT,pos.z}, new float[] {pos.x,HEIGHT-HEIGHT_OFFSET,pos.z}, getTransform()),
//				new ColorAnimation(HOVER_ANIM_LENGTH, COLOR, SELECTED_COLOR, mesh)
//		};
//		else this.anims = new Animation[0];
	}
	
	public List<Terrain> getChildren() {
		List<Terrain> soFar = new ArrayList<>();
		if (level == MAX_LEVELS) soFar.add(this);
		else {
			soFar.addAll(nextLevel[0].getChildren());
			soFar.addAll(nextLevel[1].getChildren());
			soFar.addAll(nextLevel[2].getChildren());
			soFar.addAll(nextLevel[3].getChildren());
		}
		return soFar;
	}
	
	public boolean containsPoint(BerylVector point) {
		return  (getPos().x + width/2 >= point.x) &&
				(getPos().x - width/2 <= point.x) &&
				(getPos().z + width/2 >= point.z) &&
				(getPos().z - width/2 <= point.z);
	}
	
//	public void testHighlight(BerylVector selectedPos, int selectedLevel) {
//		if (level == selectedLevel) highlight();
//		else if (level != MAX_LEVELS) for (Terrain child : nextLevel) {
//			if (child.containsPoint(selectedPos)) child.testHighlight(selectedPos, selectedLevel);
//		}
//	}
//	
//	public void highlight() {
//		if (level == MAX_LEVELS) {
//			if (ableToHighlight()) return;
//			highlighted = true;
//			for (int i=0; i<anims.length; i++) anims[i].play();
//		} else getChildren().forEach(child->child.highlight());
//	}
//	
//	private boolean ableToHighlight() {
//		return entity != null || highlighted;
//	}
//	
//	public void testUnhighlight(BerylVector selectedPos, int selectedLevel) {
//		if (level == selectedLevel) {
//			if (!containsPoint(selectedPos)) unhighlight();
//		} else for (Terrain child : nextLevel) child.testUnhighlight(selectedPos, selectedLevel);
//	}
//	
//	public void unhighlight() {
//		if (level == MAX_LEVELS) {
//			if (!highlighted) return;
//			highlighted = false;
//			for (int i=0; i<anims.length; i++) anims[i].playReversed();
//		} else getChildren().forEach(child->child.unhighlight());
//	}
//	
//	public void testLeftClicked(BerylVector selectedPos, int selectedLevel) {
//		if (!containsPoint(selectedPos)) return;
//		if (level == selectedLevel) leftClicked();
//		else if (level != MAX_LEVELS) for (Terrain child : nextLevel) child.testLeftClicked(selectedPos, selectedLevel);
//	}
//	
//	public void leftClicked() {
//		if (level == MAX_LEVELS) {
//			if (entity != null) return;
//			adder.handle(entity = new GameObject(
//					new BerylVector(getPos().x, HEIGHT+2, getPos().z),
//					new BerylVector(0,0,0),
//					.7f, 
//					new Material3D("spawned [" + getPos().x + "," + getPos().y + "]", new BerylVector(new Random().nextFloat(),new Random().nextFloat(),new Random().nextFloat())),
//					LookupTable.getRawModel(SPAWNED_OBJECT).getData()
//			));
//		} else getChildren().forEach(child->child.leftClicked());
//	}
//	
//	public void testRightClicked(BerylVector selectedPos, int selectedLevel) {
//		if (!containsPoint(selectedPos)) return;
//		if (level == selectedLevel) rightClicked();
//		else if (level != MAX_LEVELS) for (Terrain child : nextLevel) child.testRightClicked(selectedPos, selectedLevel);
//	}
//	
//	public void rightClicked() {
//		if (level == MAX_LEVELS) {
//			if (entity == null) return;
//			remover.handle(entity);
//			entity = null;
//		} else getChildren().forEach(child->child.rightClicked()); 
//	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @return the parent
	 */
	public Terrain getParent() {
		return parent;
	}
	/**
	 * @return the nextLevel
	 */
	public Terrain[] getNextLevel() {
		return nextLevel;
	}
	
	public BerylVector getPos() {
		return getTransform().getPos();
	}

}
