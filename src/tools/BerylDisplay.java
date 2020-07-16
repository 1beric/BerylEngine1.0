package tools;

import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import meshCreation.Loader;
import renderEngine.models.TextureData;
import tools.input.BerylInputSystem;

public class BerylDisplay {

	public static final int WIDTH   = 2560;
	public static final int HEIGHT  = 1600;
	public static final String NAME = "Beryl Editor";
	public static final float ASPECT = WIDTH / (float) HEIGHT;
	
	
	private static long window;
	private static boolean closeRequested;
	private static int width = WIDTH;
	private static int height = HEIGHT;
	private static int vpWidth = WIDTH;
	private static int vpHeight = HEIGHT;


	/**
	 * Create the display. This is a static class because we 
	 * only ever want one window to be displayed at a time.
	 */
	public static void createDisplay() {
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. GLFW functions will not work before doing this.
		if (!GLFW.glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // keep window hidden until its set up
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // allow window to be resized

		// Create the window
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, NAME, MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Center the window
		centerWindow();
		
		// Set the icon
		setIcon(Loader.decodeTextureFile("res/crystalIcon.png"));

		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(window);
		
		// Enable v-sync
		GLFW.glfwSwapInterval(1);

		// Make the window visible
		GLFW.glfwShowWindow(window);
	}
	
	/**
	 * This method updates the display, and is generally called at
	 * the end of the frame-loop.
	 */
	public static void updateDisplay() {
		GLFW.glfwSwapBuffers(window); // swap the color buffers
		BerylInputSystem.reset();
		// Poll for window events.
		GLFW.glfwPollEvents();
	}
	
	public static void closeDisplay() {
		// Free the window callbacks and destroy the window
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public static void requestClose() {
		closeRequested = true;
	}
	
	public static boolean isOpen() {
		return !GLFW.glfwWindowShouldClose(window) && !closeRequested;
	}
	
	public static long getWindow() {
		return window;
	}
	
	/**
	 * Sets the window icon using GLFW Bindings
	 * @param tex
	 */
	public static void setIcon(TextureData tex) {
        GLFWImage image = GLFWImage.malloc();
        Buffer imagebf = GLFWImage.malloc(1);
        image.set(tex.getWidth(), tex.getHeight(), tex.getBuffer());
        imagebf.put(0, image);
        GLFW.glfwSetWindowIcon(window, imagebf);
	}
	
	/**
	 * Centers the window on the primary display.
	 */
	public static void centerWindow() {
		// Get the thread's stack and push a new frame
		// We use a try-with, because MemoryStack specifies
		// that it should not be manually closed, but done
		// through a try-with.
		try ( MemoryStack stack = MemoryStack.stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // mallocs an int*
			IntBuffer pHeight = stack.mallocInt(1); // mallocs an int*

			// Get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			// Center the window
			GLFW.glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically
	}

	/**
	 * @return the width
	 */
	public static int getScreenWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public static void setScreenWidth(int width) {
		BerylDisplay.width = width;
	}

	/**
	 * @return the height
	 */
	public static int getScreenHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public static void setScreenHeight(int height) {
		BerylDisplay.height = height;
	}

	/**
	 * @return the vpWidth
	 */
	public static int getVPWidth() {
		return vpWidth;
	}

	/**
	 * @param vpWidth the vpWidth to set
	 */
	public static void setVpWidth(int vpWidth) {
		BerylDisplay.vpWidth = vpWidth;
	}

	/**
	 * @return the vpHeight
	 */
	public static int getVPHeight() {
		return vpHeight;
	}

	/**
	 * @param vpHeight the vpHeight to set
	 */
	public static void setVpHeight(int vpHeight) {
		BerylDisplay.vpHeight = vpHeight;
	}
	
	
	
}