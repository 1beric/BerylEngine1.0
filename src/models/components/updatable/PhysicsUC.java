package models.components.updatable;

import models.data.Entity;
import tools.BerylTime;
import tools.math.BerylVector;

public class PhysicsUC extends BerylUC {

	private static final float CUTOFF = .01f;

	private float airFriction = .95f;
	private float gravity = .95f;
	private boolean useGravity = false;
	
	private BerylVector acceleration;
	private BerylVector velocity;
	private float maxVelocity;
	
	
	public PhysicsUC(Entity entity) {
		super(entity);
		acceleration = BerylVector.zero();
		velocity 	 = BerylVector.zero();
		maxVelocity  = Float.MAX_VALUE;
	}

	@Override
	public void onInit() {
		acceleration = BerylVector.zero();
		velocity 	 = BerylVector.zero();
		maxVelocity  = Float.MAX_VALUE;
	}

	@Override
	public void onUpdate() {
		if (useGravity) addForce(new BerylVector(0,-gravity,0));
		velocity = velocity.add(acceleration.mult(BerylTime.getDelta() * 0.5f));
		velocity = velocity.mult(airFriction);
		velocity = velocity.clampMagnitude(0, maxVelocity);
		if (velocity.magnitude() < CUTOFF) velocity.mult(0);
		BerylVector pos = getEntity().getTransform().getPos();
		pos = pos.add(velocity.mult(BerylTime.getDelta()));
		velocity = velocity.add(acceleration.mult(BerylTime.getDelta() * 0.5f));
		resetAcceleration();
	}

	@Override
	public void onLateUpdate() { }


	public void resetAcceleration() {
		acceleration = new BerylVector(0);
	}
	
	public void addForce(BerylVector force) {
		acceleration = acceleration.add(force);
	}

	/**
	 * @return the acceleration
	 */
	public BerylVector getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(BerylVector acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the velocity
	 */
	public BerylVector getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(BerylVector velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the maxVelocity
	 */
	public float getMaxVelocity() {
		return maxVelocity;
	}

	/**
	 * @param maxVelocity the maxVelocity to set
	 */
	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	
}
