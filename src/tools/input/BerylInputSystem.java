package tools.input;

import renderEngine.MasterRenderer;
import tools.math.BerylVector;

public class BerylInputSystem {

	public static void init() {
		BerylKeyboard.init();
		BerylMouse.init(MasterRenderer.getProjectionMatrix(), BerylVector.zero(), BerylVector.zero());
	}
	
	public static void update() {
		BerylKeyboard.update();
		BerylMouse.update();
	}
	
	public static void reset() {
		BerylMouse.resetMouse();
		BerylKeyboard.resetKeyboard();
	}
	
}
