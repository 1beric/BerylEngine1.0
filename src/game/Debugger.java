package game;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.lwjgl.glfw.GLFW;

import models.Scene;
import models.components.BerylComponent;
import models.components.updatable.BerylUC;
import models.data.Entity;
import tools.BerylTime;
import tools.io.BerylKeyboard;
import tools.io.BerylMouse;

public class Debugger extends BerylUC {

	public Debugger(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() {
		
	}

	@Override
	public void onUpdate() {
//		System.out.println("POS: " + getScene().getCam().getPos());
//		System.out.println("ROT: " + getScene().getCam().getRot());
//		System.out.println();
	}

	@Override
	public void onLateUpdate() {
		if (!BerylKeyboard.isKeyDown(GLFW.GLFW_KEY_L)) return;
		showSceneGets();
		showEditorFields();
//		showGUIHandlerNavs();
		System.out.println();
	}
	
	private void showSceneGets() {
		for (Method method : Scene.class.getDeclaredMethods()) {
			if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
				try {
					System.out.println(method.getName() + "()\t-> " + method.invoke(getScene()));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void showEditorFields() {
		for (Entity e : getScene().getEntities()) {
			for (BerylComponent comp : e.getUCs()) {
				comp.getEditorFields().forEach(f->System.out.println(f));
			}
			for (BerylComponent comp : e.getRCs()) {
				comp.getEditorFields().forEach(f->System.out.println(f));
			}
		}
	}
	
	private void showGUIHandlerNavs() {
		GUIHandler handler = (GUIHandler)getComponent("GUIs", GUIHandler.class);
		System.out.println("main: " + handler.isSetActive("main"));
		System.out.println("settings: " + handler.isSetActive("settings"));
		System.out.println("scene: " + handler.isSetActive("scene"));
		
		handler.getRoots().forEach(r->System.out.println(r.getName() + ": " + r.isActive()));
	}

}
