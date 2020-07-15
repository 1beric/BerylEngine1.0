package models.components.renderable;

import models.data.Entity;
import tools.math.BerylVector;

public class CameraRC extends BerylRC {

	public CameraRC(Entity entity) {
		super(entity);
		entity.getTransform().setPos(new BerylVector(0,50,-150));
		entity.getTransform().setRot(new BerylVector(50,0,0));
	}
	
	
	
	public BerylVector getPos() {
		return getEntity().getTransform().getPos();
	}
	
	public BerylVector getRot() {
		return getEntity().getTransform().getRot();
	}
	
	public void invertPitch() {
		getRot().x *= -1;
	}
	/**
	 * @return the pitch
	 */
	public float getPitch() {
		return getRot().x;
	}
	/**
	 * 
	 */
	public void setPitch(float pitch) {
		getRot().x = pitch;
	}
	/**
	 * @return the yaw
	 */
	public float getYaw() {
		return getRot().y;
	}
	/**
	 * 
	 */
	public void setYaw(float yaw) {
		getRot().y = yaw;
	}
	/**
	 * @return the roll
	 */
	public float getRoll() {
		return getRot().z;
	}

}
