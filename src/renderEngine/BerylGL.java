package renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import tools.math.BerylVector;

/**
 * A static class that handles wrapping GL functions for ease of use.
 * @author Brandon Erickson
 *
 */
public class BerylGL {

	private static final BerylVector CLEAR_COLOR = new BerylVector(0,1,1);
	
	/**
	 * Initializes the GL Context on this thread
	 * 
	 */
	public static void init() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		// Set the clear color
		GL11.glClearColor(CLEAR_COLOR.x, CLEAR_COLOR.y, CLEAR_COLOR.z, 0.0f);
		
		GL11.glViewport(0, 0, BerylDisplay.WIDTH, BerylDisplay.HEIGHT);
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		
		
		GLFW.glfwSetWindowSizeCallback(BerylDisplay.getWindow(), (window,screenWidth,screenHeight)->{
			BerylDisplay.setScreenWidth(screenWidth);
			BerylDisplay.setScreenHeight(screenHeight);
			resetViewport();
//			System.out.println(BerylDisplay.getVpHeight() + "," + BerylDisplay.getVpWidth());
		});
	}
	
	public static void glErrorHandling() {
		switch (GL11.glGetError()) {
		case GL11.GL_NO_ERROR:
			return;
		case GL11.GL_STACK_OVERFLOW:
			System.out.println("STACK OVERFLOW");
			return;
		case GL11.GL_INVALID_ENUM:
			System.out.println("GL INVALID ENUM");
			return;
		case GL11.GL_INVALID_OPERATION:
			System.out.println("GL INVALID OPERATION");
			return;
		case GL11.GL_INVALID_VALUE:
			System.out.println("GL INVALID VALUE");
			return;
		case GL11.GL_STACK_UNDERFLOW:
			System.out.println("STACK UNDERFLOW");
			return;
		case GL11.GL_OUT_OF_MEMORY:
			System.out.println("GL OUT OF MEM");
			return;
		}
		System.out.println("ERROR!!");
	}
	
	public static void resetViewport() {
		int aspectWidth = BerylDisplay.getScreenWidth();
		int aspectHeight = (int) (aspectWidth / BerylDisplay.ASPECT);
		if (aspectHeight > BerylDisplay.getScreenHeight()) {
			aspectHeight = BerylDisplay.getScreenHeight();
			aspectWidth = (int)(aspectHeight*BerylDisplay.ASPECT);
		}
		int vpX = (int)((BerylDisplay.getScreenWidth()-aspectWidth)/2f);
		int vpY = (int)((BerylDisplay.getScreenHeight()-aspectHeight)/2f);
		GL11.glViewport(vpX, vpY, aspectWidth, aspectHeight);
		BerylDisplay.setVpWidth(aspectWidth);
		BerylDisplay.setVpHeight(aspectHeight);
		MasterRenderer.resetProjectionMatrix();
	}
	
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

}
