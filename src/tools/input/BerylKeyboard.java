package tools.input;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import tools.BerylDisplay;

public class BerylKeyboard {

	private static List<Integer> keysDown = new ArrayList<>();
	private static List<Integer> keysHeld = new ArrayList<>();
	private static List<Integer> keysUp = new ArrayList<>();
	
	private static BerylInputStream stream;

	public static void init() {
		stream = new BerylInputStream(new byte[0]);
		
		GLFW.glfwSetKeyCallback(BerylDisplay.getWindow(), GLFWKeyCallback.create((long window, int key, int scancode, int action, int mods)->{
			if (action == GLFW.GLFW_RELEASE) {
				keysUp.add(key);
				keysHeld.remove((Integer)key);
			} else if (action == GLFW.GLFW_PRESS) {
				keysDown.add(key);
				keysHeld.add(key);
			}
		}));
		
		
	}

	public static void update() {
		
	}
	
	public static boolean isKeyHeld(int k) {
		return keysHeld.contains(k);
	}
	
	public static boolean isKeyDown(int k) {
		return keysDown.contains(k);
	}
	
	public static boolean isKeyUp(int k) {
		return keysUp.contains(k);
	}
	
	public static BerylInputStream getStream() {
		return stream;
	}
	
	public static void resetKeyboard() {
		keysDown.clear();
		keysUp.clear();
	}
	
}
