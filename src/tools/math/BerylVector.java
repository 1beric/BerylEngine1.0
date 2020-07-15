package tools.math;

public class BerylVector {

	
	public float x;
	public float y;
	public float z;
	public float w;
	
	public BerylVector() {
		init();
	}
	
	public BerylVector(float set) {
		fill(set, 4);
	}
	
	public BerylVector(float x, float y) {
		init();
		this.x = x;
		this.y = y;
	}
	
	public BerylVector(float x, float y, float z) {
		init();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BerylVector(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public BerylVector(BerylVector toCopy) {
		x = toCopy.x;
		y = toCopy.y;
		z = toCopy.z;
		w = toCopy.w;
	}
		
	private void init() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	
	private void fill(float f, int amt) {
		switch (amt) {
		case 4:
			w = f;
		case 3:
			z = f;
		case 2:
			y = f;
		case 1:
			x = f;
		case 0:
			break;
		default:
			System.out.println("INVALID BERYLVECTOR SIZE: " + amt);
		}
	}
	

	
	public float magnitude() {
		return (float) Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	public float squaredMagnitude() {
		return x*x+y*y+z*z+w*w;
	}
	
	public BerylVector normalize() {
		BerylVector copy = copy();
		float length = magnitude();
		if (length == 0) return copy;
		copy.x /= length;
		copy.y /= length;
		copy.z /= length;
		copy.w /= length;
		return copy;
	}

	public BerylVector add(BerylVector toAdd) {
		BerylVector copy = copy();
		copy.x += toAdd.x;
		copy.y += toAdd.y;
		copy.z += toAdd.z;
		copy.w += toAdd.w;
		return copy;
	}
	
	public BerylVector add(float toAdd) {
		BerylVector copy = copy();
		copy.x += toAdd;
		copy.y += toAdd;
		copy.z += toAdd;
		copy.w += toAdd;
		return copy;
	}
	
	public BerylVector mult(BerylVector toMult) {
		BerylVector copy = copy();
		copy.x *= toMult.x;
		copy.y *= toMult.y;
		copy.z *= toMult.z;
		copy.w *= toMult.w;
		return copy;
	}
	
	public BerylVector mult(float toMult) {
		BerylVector copy = copy();
		copy.x *= toMult;
		copy.y *= toMult;
		copy.z *= toMult;
		copy.w *= toMult;
		return copy;
	}
	
	public float dot(BerylVector toDot) {
		return this.x*toDot.x + this.y*toDot.y + this.z*toDot.z + this.w*toDot.w;
	}
	
	public BerylVector cross(BerylVector toDot) {
		return new BerylVector(
				this.y*toDot.z - this.z*toDot.y,
				this.z*toDot.x - this.x*toDot.z,
				this.x*toDot.y - this.y*toDot.x
		);
	}
	
	public BerylVector clampMagnitude(float l, float h) {
		BerylVector copy = copy();
		float mag = magnitude();
		if (mag < l) {
			copy.normalize();
			copy.mult(l);
		} else if (mag > h) {
			copy.normalize();
			copy.mult(h);
		}
		return copy;
	}
	public BerylVector clamp(float l, float h) {
		BerylVector copy = copy();
		copy.x = BerylMath.clamp(x, l, h);
		copy.y = BerylMath.clamp(y, l, h);
		copy.z = BerylMath.clamp(z, l, h);
		copy.w = BerylMath.clamp(w, l, h);
		return copy;
	}
	
	public BerylVector rotate(float angle, BerylVector axis) {
		BerylVector copy = copy();
		axis = axis.normalize();
		float theta = (float) Math.toRadians(angle);
		copy = copy.mult((float)Math.cos(theta));
		copy = copy.add(axis.cross(this).mult((float)Math.sin(theta)));
		copy = copy.add(axis.mult(axis.dot(this)*(1-(float)Math.cos(theta))));
		return copy;
	}
	
	public BerylVector copy() {
		return new BerylVector(this);
	}
	
	public float[] toFloatArray3() {
		return new float[] {x,y,z};
	}
	
	public float[] toFloatArray2() {
		return new float[] {x,y};
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;	
	}
	
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public boolean isZero() {
		return x==0 && y==0 && z==0 && w==0;
	}
	
	public float x() { 
		return x;
	}
	public float y() {
		return y;
	}
	public float z() {
		return z;
	}
	public float w() {
		return w;
	}
	
	public float r() {
		return x;
	}
	public float g() {
		return y;
	}
	public float b() {
		return z;
	}
	public float a() {
		return w;
	}
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "," + z + "," + w + "]";
	}
	
	public static BerylVector posX() {
		return new BerylVector(1,0,0);
	}
	public static BerylVector negX() {
		return new BerylVector(-1,0,0);
	}
	public static BerylVector posY() {
		return new BerylVector(0,1,0);
	}
	public static BerylVector negY() {
		return new BerylVector(0,-1,0);
	}
	public static BerylVector posZ() {
		return new BerylVector(0,0,1);
	}
	public static BerylVector negZ() {
		return new BerylVector(0,0,-1);
	}
	
	public static BerylVector zero() {
		return new BerylVector(0);
	}
	
	public static BerylVector one(int amt) {
		BerylVector out = new BerylVector();
		out.fill(1, amt);
		return out;
	}
	
	public static BerylVector min(int amt) {
		BerylVector out = new BerylVector();
		out.fill(Float.MIN_VALUE, amt);
		return out;
	}
	
	public static BerylVector max(int amt) {
		BerylVector out = new BerylVector();
		out.fill(Float.MAX_VALUE, amt);
		return out;
	}
}
