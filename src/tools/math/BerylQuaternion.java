package tools.math;

public class BerylQuaternion {

	
	public static BerylQuaternion identity() {
		return new BerylQuaternion();
	}
	
	public float s;
	public BerylVector v;

	public BerylQuaternion(float s, float x, float y, float z) {
		this.s = s;
		this.v = new BerylVector(x,y,z);
	}
	
	public BerylQuaternion(float s, BerylVector v) {
		this.s = s;
		this.v = v.copy();
	}
	
	public BerylQuaternion(BerylQuaternion q) {
		this.s = q.s;
		this.v = q.v.copy();
	}
	
	public BerylQuaternion(BerylVector euler) {
		v = new BerylVector();
		fromEuler(euler);
	}
	
	public BerylQuaternion(float x, float y, float z) {
		v = new BerylVector();
		fromEuler(new BerylVector(x,y,z));
	}
	
	public BerylQuaternion() {
		this.s = 1;
		this.v = BerylVector.zero();
	}
	
	@Override
	public String toString() {
		return "s: " + s + ", v: " + v;
	}
	
	
	public void add(BerylQuaternion toAdd) {
		this.s += toAdd.s;
		this.v.add(toAdd.v);
	}
	
	public BerylQuaternion addStatic(BerylQuaternion toAdd) {
		BerylQuaternion copy = copy();
		copy.add(toAdd);
		return copy;
	}
	
	public void mult(float scalar) {
		this.s *= scalar;
		this.v.mult(scalar);
	}
	
	public BerylQuaternion multStatic(float scalar) {
		BerylQuaternion copy = copy();
		copy.mult(scalar);
		return copy;
	}

	public void mult(BerylQuaternion toMult) {
		if (this.isPure() && toMult.isPure()) {
			s = -this.v.dot(toMult.v);
			v = this.v.cross(toMult.v);
			return;
		} else if (this.isReal() && toMult.isReal()) {
			this.s *= toMult.s;
			this.v.mult(0);
			return;
		}
		this.s = this.s*toMult.s - this.v.dot(toMult.v);
		this.v = toMult.v.mult(this.s)
				.add(this.v.mult(toMult.s))
				.add(this.v.cross(toMult.v));
	}
	
	public BerylQuaternion multStatic(BerylQuaternion toMult) {
		BerylQuaternion copy = copy();
		copy.mult(toMult);
		return copy;
	}
	
	public float dot(BerylQuaternion toDot) {
		return this.s*toDot.s + this.v.dot(toDot.v);
	}
	
	/**
	 * Rotates this Quaternion by an angle around an arbitrary axis.
	 * @param angle
	 * @param axis
	 */
	public void rotate(float angle, BerylVector axis) {
		angle = (float) Math.toRadians(angle);
		BerylQuaternion q = new BerylQuaternion(
				(float) Math.cos(.5*angle),
				axis.normalize()
					.mult((float)Math.sin(.5*angle)));
		BerylQuaternion rotated = (q.multStatic(this.getPure())).multStatic(q.getInverse());
		this.s = rotated.s;
		this.v = rotated.v;
	}
	
	public BerylQuaternion rotateStatic(float angle, BerylVector axis) {
		BerylQuaternion copy = copy();
		copy.rotate(angle, axis);
		return copy;
	}
	
	public BerylQuaternion slerp(BerylQuaternion q, float f) {
		BerylQuaternion copy = copy();
		float theta = (float) Math.acos(copy.dot(q)/(copy.magnitude()*q.magnitude()));
		float sineTheta = (float) Math.sin(theta);
		if (sineTheta == 0) return copy.addStatic((q.addStatic(copy.multStatic(-1))).multStatic(f));
		BerylQuaternion q1 = copy.multStatic((float)(Math.sin((1-f)*theta)/sineTheta));
		BerylQuaternion q2 = copy.multStatic((float)(Math.sin(  f  *theta)/sineTheta));
		return q1.addStatic(q2);
	}
	
	public void normalize() {
		float norm = norm();
		if (norm == 0) return;
		this.s /= norm;
		this.v.mult(1/norm);
	}
	
	public BerylQuaternion normalStatic() {
		BerylQuaternion copy = copy();
		copy.normalize();
		return copy;
	}
	
	public float norm() {
		return (float) Math.sqrt(this.s*this.s + this.v.squaredMagnitude());
	}
	
	public float magnitude() {
		return norm();
	}
	
	public float squaredNorm() {
		return this.s*this.s + this.v.squaredMagnitude();
	}
	
	public float squaredMagnitude() {
		return squaredNorm();
	}
	
	
	public boolean isReal() {
		return v.isZero();
	}
	
	public boolean isPure() {
		return s==0;
	}
	
	public BerylQuaternion getReal() {
		return new BerylQuaternion(this.s,BerylVector.zero());
	}
	
	public BerylQuaternion getPure() {
		return new BerylQuaternion(0,this.v);
	}
	
	public BerylQuaternion getConjugate() {
		return new BerylQuaternion(this.s, this.v.mult(-1));
	}
	
	public BerylQuaternion getInverse() {
		float sqrNorm = squaredNorm();
		if (sqrNorm == 0) return new BerylQuaternion();
		return getConjugate().multStatic(1/sqrNorm);
	}
	
	
	
	public void fromEuler(BerylVector euler) {
		double angle = Math.toRadians(euler.x * 0.5f);
		double sinRoll = Math.sin(angle);
		double cosRoll = Math.cos(angle);
		angle = Math.toRadians(euler.z * 0.5f);
        double sinPitch = Math.sin(angle);
        double cosPitch = Math.cos(angle);
        angle = Math.toRadians(euler.y * 0.5f);
        double sinYaw = Math.sin(angle);
        double cosYaw = Math.cos(angle);

        double cosRollXcosPitch = cosRoll * cosPitch;
        double sinRollXsinPitch = sinRoll * sinPitch;
        double cosRollXsinPitch = cosRoll * sinPitch;
        double sinRollXcosPitch = sinRoll * cosPitch;

        s   = (float) (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw);
        v.x = (float) (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw);
        v.y = (float) (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw);
        v.z = (float) (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw);

        normalize();
	}
	
	public BerylVector toEuler() {
		BerylVector euler = new BerylVector();
		normalize();
		
        float sqS = s * s;
        float sqX = v.x * v.x;
        float sqY = v.y * v.y;
        float sqZ = v.z * v.z;
        float unit = sqX + sqY + sqZ + sqS;
        float test = v.x * v.y + v.z * s;
        if (test > 0.499 * unit) {
        	euler.x = (float) (2 * Math.atan2(v.x, s));
        	euler.z = (float) (Math.PI/2);
        	euler.y = 0;
        } else if (test < -0.499 * unit) {
        	euler.x = (float) (-2 * Math.atan2(v.x, s));
        	euler.z = (float) (-Math.PI/2);
        	euler.y = 0;
        } else {
        	euler.x = (float) Math.atan2(2 * v.y * s - 2 * v.x * v.z, sqX - sqY - sqZ + sqS); // roll or heading 
        	euler.z = (float) Math.asin(2 * test / unit); // pitch or attitude
        	euler.y = (float) Math.atan2(2 * v.x * s - 2 * v.y * v.z, -sqX + sqY - sqZ + sqS); // yaw or bank
        }
        euler.set((float)Math.toDegrees(euler.x), (float)Math.toDegrees(euler.y), (float)Math.toDegrees(euler.z));
		
        return euler;
	}
	
	public BerylQuaternion copy() {
		return new BerylQuaternion(this);
	}
	
	
	
}
