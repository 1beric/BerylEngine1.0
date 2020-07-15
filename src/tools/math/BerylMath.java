package tools.math;

import guiSystem.RectStyles;
import renderEngine.BerylDisplay;
public class BerylMath {

	/**
	 * @return the projectionMatrix
	 */
	public static BerylMatrix getProjectionMatrix() {
		return BerylMatrix.perspective(60, BerylDisplay.ASPECT, .1f, 5000);
	}

	public static float barryCentric(BerylVector p1, BerylVector p2, BerylVector p3, BerylVector pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	
	public static BerylMatrix createTransformationMatrix(BerylVector pos, BerylVector rot, BerylVector scale) {
		BerylMatrix matrix = new BerylMatrix();
		matrix = matrix.multiply(BerylMatrix.translate(pos));
		matrix = matrix.multiply(BerylMatrix.rotate(rot.x,1,0,0)); // rotate x
		matrix = matrix.multiply(BerylMatrix.rotate(rot.y,0,1,0)); // rotate y
		matrix = matrix.multiply(BerylMatrix.rotate(rot.z,0,0,1)); // rotate z
		matrix = matrix.multiply(BerylMatrix.scale(scale));
		return matrix;
	}
	
	public static BerylMatrix createTransformationMatrix(BerylVector translation, BerylVector scale) {
		BerylMatrix matrix = new BerylMatrix();
		matrix = matrix.multiply(BerylMatrix.translate(translation));
		matrix = matrix.multiply(BerylMatrix.scale(scale.x, scale.y, 1f));
		return matrix;
	}
	
	public static BerylMatrix createViewMatrix(BerylVector pos, BerylVector rot) {
		BerylMatrix viewMatrix = new BerylMatrix();
		viewMatrix = viewMatrix.multiply(BerylMatrix.rotate(rot.x,1,0,0));
		viewMatrix = viewMatrix.multiply(BerylMatrix.rotate(rot.y,0,1,0));	
		viewMatrix = viewMatrix.multiply(BerylMatrix.rotate(rot.z,0,0,1));
		viewMatrix = viewMatrix.multiply(BerylMatrix.translate(pos.mult(-1)));
		return viewMatrix;
	}

	public static BerylVector findPointAtY(float y, BerylVector origin, BerylVector ray) {
		float t = (y-origin.y)/ray.y;
		return new BerylVector(
				origin.x + t * ray.x,
				y,
				origin.z + t * ray.z);
	}
	
	public static BerylVector calculateRay(BerylVector screenRay, BerylVector pos, BerylVector rot) {
		BerylVector clipCoords = new BerylVector(screenRay.x,screenRay.y,-1f,1f);
		BerylVector eyeCoords = getESC(clipCoords);
		BerylVector worldRay = getWorld(eyeCoords, pos, rot);
		return worldRay;
	}
	
	public static BerylVector getWorld(BerylVector eyeCoords, BerylVector pos, BerylVector rot) {
		BerylMatrix invertedView = createViewMatrix(pos, rot).invert();
		BerylVector worldCoords = invertedView.multiply(eyeCoords);
		BerylVector ray = new BerylVector(worldCoords.x, worldCoords.y, worldCoords.z);
		ray.normalize();
		return ray;
	}
	
	public static BerylVector getESC(BerylVector clipCoords) {
		BerylMatrix invertedProjection = getProjectionMatrix().invert();
		BerylVector eyeCoords = invertedProjection.multiply(clipCoords);
		return new BerylVector(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	public static BerylVector getNDC(float mouseX, float mouseY) {
		float x = (2f*mouseX) / BerylDisplay.WIDTH - 1;
		float y = (2f*mouseY) / BerylDisplay.HEIGHT - 1;
		return new BerylVector(x,y);
	}

	public static float clamp(float x, float l, float h) {
		return Math.max(l, Math.min(h, x));
	}
	
	public static BerylVector translatePosition(BerylVector pos, BerylVector offset, RectStyles from, RectStyles to) {
		BerylVector out = new BerylVector(pos);
		if (from == to) return out;
		switch (from) {
		case BC:
		case BL:
		case BR:
			out.y += offset.y;
			break;
		case TC:
		case TL:
		case TR:
			out.y -= offset.y;
			break;
		default:
			break;
		}
		switch (from) {
		case BL:
		case TL:
		case CL:
			out.x += offset.x;
			break;
		case BR:
		case TR:
		case CR:
			out.x -= offset.x;
			break;
		default:
			break;
		}
		// translate to placement
		switch (to) {
		case BC:
		case BL:
		case BR:
			out.y -= offset.y;
			break;
		case TC:
		case TL:
		case TR:
			out.y += offset.y;
			break;
		default:
			break;
		}
		switch (to) {
		case BL:
		case CL:
		case TL:
			out.x -= offset.x;
			break;
		case BR:
		case CR:
		case TR:
			out.x += offset.x;
			break;
		default:
			break;
		}
		return out;
	}
	
	public static BerylVector hexToRGB(String hex) {
		if (hex.charAt(0)=='#') hex = hex.substring(1);
		String r = hex.substring(0,2);
		String g = hex.substring(2,4);
		String b = hex.substring(4,6);	
		return new BerylVector(Integer.parseInt(r,16)/255f, Integer.parseInt(g,16)/255f, Integer.parseInt(b,16)/255f);
	}
	
	public static BerylVector rgbToHSV(BerylVector rgb) {
//		rgb.clamp(0,1);
//		float delta = Math.max(Math.max(rgb.x, rgb.y), rgb.z) - Math.min(Math.min(rgb.x, rgb.y), rgb.z);
//		
//		
		return null;
	}
	
	public static BerylVector hsvToRGB(BerylVector rgb) {
		
		
		
		
		return null;
	}

}
