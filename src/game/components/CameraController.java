package game.components;

import org.lwjgl.glfw.GLFW;

import guiSystem.elements.Mesh2RC;
import models.Scene;
import models.components.renderable.CameraRC;
import models.components.updatable.BerylUC;
import models.data.Entity;
import settings.Constants;
import tools.MouseHoverState;
import tools.input.BerylKeyboard;
import tools.input.BerylMouse;
import tools.BerylTime;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class CameraController extends BerylUC {

	public CameraController(Entity entity) {
		super(entity);
	}

	private static final float MIN_ZOOM = (float)Constants.get("CAMERA_MIN_ZOOM");
	private static final float MAX_ZOOM = (float)Constants.get("CAMERA_MAX_ZOOM");
	private static final float MIN_PITCH = (float)Constants.get("CAMERA_MIN_PITCH");
	private static final float MAX_PITCH = (float)Constants.get("CAMERA_MAX_PITCH");
	private static final float MOVE_SPEED = (float)Constants.get("CAMERA_MOVE_SPEED");
	
	private CameraRC cam;
	
	private float distFromCenter;
	private BerylVector centerPoint;
	private boolean limitPitch;
	private boolean limitDist;
	
	private MouseHoverState mouseState;
	
	@Override
	public void onInit() {
		mouseState = MouseHoverState.GUI;
		cam = new CameraRC(getEntity());
		getEntity().addComponent(cam);
		
		distFromCenter = 50;
		centerPoint = new BerylVector(0);
		limitPitch = false;
		limitDist = true;
	}

	@Override
	public void onUpdate() {
		updateState(getEntity().getScene());
		if (mouseState == MouseHoverState.WORLD) move();
	}

	@Override
	public void onLateUpdate() { }

	private void updateState(Scene scene) {
		if (!BerylMouse.getButtonHeld(2)) {
			for (Mesh2RC mesh : scene.getMesh2RCs()) {
				if (mesh.isActive() && mesh.contains(BerylMouse.getScreenRay())) {
					mouseState = MouseHoverState.GUI;
					return;
				}
			}
			mouseState = MouseHoverState.WORLD;
		}		
	}
	
	public void move() {
		if (BerylKeyboard.isKeyDown(GLFW.GLFW_KEY_PERIOD)) resetCamera();
		calcCenterPoint();
		
		calculateAngleAroundPlayer();
		calculatePitch();
		calculateZoom();
		
		float hDist = calcHorizontal();
		float vDist = calcVertical();
		calcCamPos(hDist, vDist);
	}

	private void resetCamera() {
		distFromCenter = 50;
		centerPoint.mult(0);
		cam.setYaw(0);
		cam.setPitch(50);
	}
	
	private void calcCamPos(float hDist, float vDist) {
		float dx = (float) (hDist * Math.sin(Math.toRadians(180 - cam.getYaw())));
		float dz = (float) (hDist * Math.cos(Math.toRadians(180 - cam.getYaw())));
		cam.getPos().x = centerPoint.x - dx;
		cam.getPos().y = centerPoint.y + vDist;
		cam.getPos().z = centerPoint.z - dz;
	}
	
	private float calcHorizontal() {
		return (float) (distFromCenter * Math.cos(Math.toRadians(cam.getPitch())));
	}
	
	private float calcVertical() {
		return (float) (distFromCenter * Math.sin(Math.toRadians(cam.getPitch())));
	}
	
	private void calculateZoom() {
		float zoomLevel = BerylMouse.getScrollAmount() * 4f;
		distFromCenter -= zoomLevel;
		if (limitDist) distFromCenter = BerylMath.clamp(distFromCenter, MIN_ZOOM, MAX_ZOOM);
	}
	
	private void calculatePitch() {
		if (BerylMouse.getButtonHeld(2) && !BerylKeyboard.isKeyHeld(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			float pitchChange = BerylMouse.getDY() * 0.1f;
			cam.setPitch(cam.getPitch()+pitchChange);
			if (limitPitch) cam.setPitch(BerylMath.clamp(cam.getPitch(), MIN_PITCH, MAX_PITCH));
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if (BerylMouse.getButtonHeld(2) && !BerylKeyboard.isKeyHeld(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			float angleChange = BerylMouse.getDX() * 0.3f;
			cam.setYaw(cam.getYaw()+angleChange);
		}
	}
	
	private void calcCenterPoint() {
		if (BerylMouse.getButtonHeld(2) && BerylKeyboard.isKeyHeld(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			BerylVector forward = BerylMath.calculateRay(new BerylVector(0), cam.getPos(), cam.getRot());
			BerylVector right = new BerylVector(forward.z,0,-forward.x);
			BerylVector up = right.cross(forward);
			forward.normalize();
			right.normalize();
			up.normalize();
			float horizontal = BerylMouse.getDX() * MOVE_SPEED * BerylTime.getDelta();
			float vertical   = -BerylMouse.getDY() * MOVE_SPEED * BerylTime.getDelta();
			
			centerPoint = centerPoint.add(right.mult(horizontal));
			centerPoint = centerPoint.add(up.mult(vertical));
			
		}
	}
	
}
