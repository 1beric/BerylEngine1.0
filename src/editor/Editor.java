package editor;

import java.util.HashSet;
import java.util.Set;

import models.components.renderable.Transform2D;
import tools.io.BerylMouse;
import tools.math.BerylVector;

public class Editor {

	private Set<Window> windows;
	
	public Editor() {
		windows = new HashSet<>();
		addWindow(new GameView()); // new BerylVector(0,0), new BerylVector(0.5f,0.5f)
	}
	
	public void addWindow(Window window) {
		windows.add(window);
	}
	
	
	public GameView getGameView() {
		for (Window window : windows) {
			if (window instanceof GameView) return (GameView)window;
		}
		return null;
	}

}
