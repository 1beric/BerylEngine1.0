package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public class Transform2D extends Transform {

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

}
