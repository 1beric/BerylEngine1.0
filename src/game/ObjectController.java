package game;

import guiSystem.elements.Slider;
import guiSystem.elements.VectorStack;
import meshCreation.CubeBuilder;
import meshCreation.PlaneBuilder;
import models.components.renderable.Mesh3RC;
import models.components.updatable.BerylUC;
import models.components.updatable.PhysicsUC;
import models.data.Entity;
import models.data.Material3D;

import org.lwjgl.glfw.GLFW;

import guiSystem.RectStyle;
import guiSystem.animations.ChainAnimation;
import guiSystem.animations.ColorAnimation;
import guiSystem.animations.PositionAnimation;
import renderEngine.models.LookupTable;
import renderEngine.models.Texture;
import tools.io.BerylKeyboard;
import tools.math.BerylVector;

public class ObjectController extends BerylUC {
	public ObjectController(Entity entity) {
		super(entity);
	}

	private static final int LENGTH = 20;
	private static final float FORCE = 500;

	private PhysicsUC phys;
	
	private VectorStack velocityElement;
	private int framesPassed;
	
	@Override
	public void onInit() {
		
		
		getEntity().getTransform().setScale(new BerylVector(10,10,10));
		Mesh3RC mesh = new Mesh3RC(getEntity());
		mesh.setData(PlaneBuilder.buildPlane().getData());
		Material3D mat = new Material3D("Plane Material");
		mat.setColor(new BerylVector(1,0,0));
		mat.setSpecularAndEmissionMap(Texture.green());
		mat.setShineDamper(1f);
		mat.setReflectivity(.2f);
		mesh.setMat(mat);
		new ChainAnimation(
				new ColorAnimation(3,new BerylVector(1,0,0),new BerylVector(0,1,0),mat),
				new ColorAnimation(3,new BerylVector(0,1,0),new BerylVector(1,1,1),mat),
				new ColorAnimation(3,new BerylVector(1,1,1),new BerylVector(1,0,0),mat)
		).play();
		
		phys = (PhysicsUC) getComponent(PhysicsUC.class);
		
		velocityElement = new VectorStack(
				new BerylVector(0,0.4f),
				new BerylVector(0.1f,0.15f),
				"percent", "percent",
				phys.getVelocity(),
				new Entity("velocity", getEntity().getScene()));
		velocityElement.setTransparency(0.5f);
		velocityElement.setColor(BerylVector.zero());
		velocityElement.setOriginPoint(RectStyle.CL);
		velocityElement.setActive(false);
		GUIHandler handler = (GUIHandler)getComponent("GUIs", GUIHandler.class);
		handler.addGUI("scene", velocityElement);
		handler.addTransition("scene", "main", new PositionAnimation(
				GUIHandler.NAVIGATION_TIME, 
				velocityElement.getPos(), 
				velocityElement.getPos().add(new BerylVector(-1,0)), 
				velocityElement.getTransform()));
		
		GUIHandler guiHandler = (GUIHandler) getComponent("GUIs", GUIHandler.class);
		Slider xRot = guiHandler.getSideSlider(0);
		xRot.setBounds(new BerylVector(-180,180));
		xRot.setOnSetValue(rot->this.getEntity().getTransform().getRot().x=rot);
		xRot.setSnap(30f/360f);
		
		Slider yRot = guiHandler.getSideSlider(1);
		yRot.setBounds(new BerylVector(-180,180));
		yRot.setOnSetValue(rot->this.getEntity().getTransform().getRot().y=rot);
		
		Slider zRot = guiHandler.getSideSlider(2);
		zRot.setBounds(new BerylVector(-180,180));
		zRot.setOnSetValue(rot->this.getEntity().getTransform().getRot().z=rot);
		
		Slider scaleX = guiHandler.getSideSlider(3);
		scaleX.setBounds(new BerylVector(0.01f,20.01f));
		scaleX.setOnSetValue(s->this.getEntity().getTransform().getScale().x = s);

		Slider scaleY = guiHandler.getSideSlider(4);
		scaleY.setBounds(new BerylVector(0.01f,20.01f));
		scaleY.setOnSetValue(s->this.getEntity().getTransform().getScale().y = s);
		
		Slider scaleZ = guiHandler.getSideSlider(5);
		scaleZ.setBounds(new BerylVector(0.01f,20.01f));
		scaleZ.setOnSetValue(s->this.getEntity().getTransform().getScale().z = s);
		
		framesPassed = 0;
	}

	@Override
	public void onUpdate() {
		if (BerylKeyboard.isKeyHeld(GLFW.GLFW_KEY_SPACE)) {
			phys.addForce(new BerylVector(0,FORCE,0));
		}
		if (BerylKeyboard.isKeyHeld(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			phys.addForce(new BerylVector(0,-FORCE,0));
		}
	}

	@Override
	public void onLateUpdate() {
		if (framesPassed >= LENGTH) {
			velocityElement.setVector(phys.getVelocity());
			framesPassed = 0;
		}
		framesPassed++;
	}

}
