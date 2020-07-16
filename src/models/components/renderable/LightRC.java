package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public class LightRC extends BerylRC {
	
	private BerylVector color;
	private BerylVector attenuation;
	private boolean point;
	private BerylVector direction;
	
	public LightRC(Entity entity) {
		super(entity);
		this.color = BerylVector.zero();
		this.attenuation = new BerylVector(1,0,0);
		this.direction = BerylVector.negY().add(BerylVector.negX());
	}
	
	/**
	 * @return the attenuation
	 */
	public BerylVector getAttenuation() {
		return attenuation;
	}
	/**
	 * @param attenuation the attenuation to set
	 */
	public void setAttenuation(BerylVector attenuation) {
		this.attenuation = attenuation;
	}
	/**
	 * @return the pos
	 */
	public BerylVector getPos() {
		return getEntity().getTransform().getPos();
	}
	/**
	 * @param pos the pos to set
	 */
	public void setPos(BerylVector pos) {
		getEntity().getTransform().setPos(pos);
	}
	/**
	 * @return the color
	 */
	public BerylVector getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(BerylVector color) {
		this.color = color;
	}
	/**
	 * @return the point
	 */
	public boolean isPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(boolean point) {
		this.point = point;
	}
	/**
	 * @return the direction
	 */
	public BerylVector getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(BerylVector direction) {
		this.direction = direction;
	}

}
