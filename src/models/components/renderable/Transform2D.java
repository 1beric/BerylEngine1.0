package models.components.renderable;

import guiSystem.RectStyle;
import models.data.Entity;
import tools.math.BerylVector;

public class Transform2D extends Transform {
	
	private boolean byFullWindow;
	private Transform2D parentTransform;
	

	public Transform2D(Entity entity) {
		super(entity);
		this.setPos(BerylVector.zero());
		this.setScale(BerylVector.one(2));
	}
	
	public Transform2D(BerylVector pos, BerylVector scale, Entity entity) {
		super(entity);
		this.setPos(pos);
		this.setScale(scale);
	}
	
	private BerylVector calcScreenPos() {
		
		
		
		return getPos();
	}
	
	public BerylVector calcScreenPos(RectStyle style) {
		
		
		
		return calcScreenPos();
	}
	
	public BerylVector calcScreenScale() {
		
		
		
		return getScale();
	}
	

}
