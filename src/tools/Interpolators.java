package tools;

import java.io.Serializable;

public class Interpolators {
	
	
	public interface Interpolator extends Serializable {
		float handle(float start, float end, float factor);
	}
	
	public static final Interpolator LINEAR = (a,b,f) -> a + f * (b - a);
	public static final Interpolator BEZIER = (a,b,f) -> a + (f * f * (3 - 2 * f)) * (b - a);
	public static final Interpolator PARAMETRIC2 = (a,b,f) -> a + parametric(f,2) * (b - a);
	public static final Interpolator PARAMETRIC5 = (a,b,f) -> a + parametric(f,5) * (b - a);
	public static final Interpolator PARAMETRIC10 = (a,b,f) -> a + parametric(f,10) * (b - a);

	private static float parametric(float f, float a) {
		return ((float)(Math.pow(f, a) / (Math.pow(f, a) + Math.pow(1 - f, a))));
	}
}
