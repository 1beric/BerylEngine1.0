package tools.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import tools.BerylDisplay;
import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class BerylMouse {

	private static BerylVector currentRay;
	private static BerylVector currentScreenRay;
	
	private static BerylVector camPos;
	private static BerylVector camRot;
	
	private static BerylVector rectSize;
	private static BerylVector rectPos;
	
	private static boolean[] buttonsUp = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private static boolean[] buttonsHeld = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private static boolean[] buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	private static BerylVector glfwMousePos	 = new BerylVector();
	private static BerylVector lastGLFWMousePos = new BerylVector();
	
	private static float scrollAmount = 0;

	
	public static void init(BerylMatrix projectionMatrix, BerylVector pos, BerylVector rot) {
		camPos = pos;
		camRot = rot;
		rectSize = BerylVector.one(2);
		rectPos = BerylVector.zero();
		initMouseCallbacks();
	}


	public static void update() {
		updateMousePos();
		currentScreenRay = BerylMath.getNDC(glfwMousePos.x, BerylDisplay.HEIGHT-glfwMousePos.y).sub(rectPos).div(rectSize);
		currentRay = BerylMath.calculateRay(currentScreenRay, camPos, camRot);
	}
	
	private static void updateMousePos() {
		lastGLFWMousePos.x = glfwMousePos.x;
		lastGLFWMousePos.y = glfwMousePos.y;

		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(BerylDisplay.getWindow(), xBuffer, yBuffer);
		glfwMousePos.x = (float)xBuffer.get(0);
		glfwMousePos.y = (float)yBuffer.get(0);
	}
	
	public static void setPosAndRot(BerylVector pos, BerylVector rot) {
		camPos = pos;
		camRot = rot;
	}
	
	
	public static float getDX() {
		return (glfwMousePos.x - lastGLFWMousePos.x) / rectSize.x ;
	}
	
	public static float getDY() {
		return (glfwMousePos.y - lastGLFWMousePos.y) / rectSize.y;
	}
	
	public static float getScrollAmount() {
		return scrollAmount;
	}
	
	public static boolean getButtonDown(int i) {
		return buttonsDown[i];
	}
	
	public static boolean getButtonHeld(int i) {
		return buttonsHeld[i];
	}
	
	public static boolean getButtonUp(int i) {
		return buttonsUp[i];
	}
	
	public static BerylVector getRay() {
		return currentRay;
	}
	
	public static BerylVector getScreenRay() {
		return currentScreenRay;
	}
	
	private static void initMouseCallbacks() {
		
		GLFW.glfwSetMouseButtonCallback(BerylDisplay.getWindow(), GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			if (action == GLFW.GLFW_RELEASE) {
				buttonsUp[button] = true;
				buttonsHeld[button] = false;
			} else if (action == GLFW.GLFW_PRESS) {
				buttonsDown[button] = true;
				buttonsHeld[button] = true;
			}
		}));
		
		GLFW.glfwSetScrollCallback(BerylDisplay.getWindow(), GLFWScrollCallback.create((window, xoffset, yoffset) -> {
			scrollAmount = (float) yoffset;
		}));
		
	}
	
	
	public static void resetMouse() {
		for (int i=0; i<GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
			buttonsUp[i] = false;
			buttonsDown[i] = false;
		}
		scrollAmount = 0;
	}


	/**
	 * @return the screenSize
	 */
	public static BerylVector getRectSize() {
		return rectSize;
	}


	/**
	 * @param screenSize the screenSize to set
	 */
	public static void setRectSize(BerylVector screenSize) {
		BerylMouse.rectSize = screenSize;
	}


	/**
	 * @return the screenPos
	 */
	public static BerylVector getRectPos() {
		return rectPos;
	}


	/**
	 * @param screenPos the screenPos to set
	 */
	public static void setRectPos(BerylVector screenPos) {
		BerylMouse.rectPos = screenPos;
	}
	
}
