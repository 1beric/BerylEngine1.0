package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public class LightRC extends BerylRC {
	
	private BerylVector color;
	private BerylVector attenuation;
	private boolean point;
	private BerylVector direction;
	
	public LightRC(BerylVector dir, BerylVector color, Entity entity) {
		super(entity);
		this.color = color;
		this.attenuation = new BerylVector(1,0,0);
		this.setPoint(false);
		setDirection(dir);
	}
	public LightRC(BerylVector pos, BerylVector color, BerylVector attenuation, Entity entity) {
		super(entity);
		entity.getTransform().setPos(pos);
		this.color = color;
		this.attenuation = attenuation;
		this.setPoint(true);
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
