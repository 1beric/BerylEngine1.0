package tools;

import org.lwjgl.glfw.GLFW;

public class BerylTime {
	
	private static double lastFrameTime;
	private static double delta;
	
	public static void init() {
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDelta() {
		double currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getDelta() {
		return (float) delta;
	}
	
	public static double getDeltaAccurate() {
		return delta;
	}
	
	private static double getCurrentTime() {
		return GLFW.glfwGetTime()*1000;
	}

}
