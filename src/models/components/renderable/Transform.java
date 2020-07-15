package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public abstract class Transform extends BerylRC {


	private BerylVector pos;
	private BerylVector rot;
	private BerylVector scale;
	
	public Transform(Entity entity) {
		super(entity);
	}

	public BerylVector getPos() {
		return pos;
	}
	
	public void setPos(BerylVector pos) {
		this.pos = pos;
	}

	/**
	 * @return the rot
	 */
	public BerylVector getRot() {
		return rot;
	}

	/**
	 * @param rot the rot to set
	 */
	public void setRot(BerylVector rot) {
		this.rot = rot;
	}

	/**
	 * @return the scale
	 */
	public BerylVector getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(BerylVector scale) {
		this.scale = scale;
	}

}
