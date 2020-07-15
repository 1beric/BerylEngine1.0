package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public class Transform3D extends Transform {
	
	public Transform3D(Entity entity) {
		super(entity);
		setPos(BerylVector.zero());
		setRot(BerylVector.zero());
		setScale(BerylVector.one(3));
	}
	
	public Transform3D(BerylVector pos, BerylVector rot, BerylVector scale, Entity entity) {
		super(entity);
		setPos(pos);
		setRot(rot);
		setScale(scale);
	}
	
	public BerylVector forwardDeg() {
		return BerylVector.posZ();
	}
	
	public BerylVector upDeg() {
		return BerylVector.posY();
	}
	
	public BerylVector rightDeg() {
		return BerylVector.negX();
	}
	
}
