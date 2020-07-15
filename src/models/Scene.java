package models;

import java.util.ArrayList;
import java.util.List;

import game.components.CameraController;
import game.components.Debugger;
import game.components.FPSUpdater;
import game.components.GUIHandler;
import game.components.ObjectController;
import game.components.SkyAnimator;
import guiSystem.elements.Mesh2RC;
import models.components.BerylComponent;
import models.components.renderable.BerylRC;
import models.components.renderable.CameraRC;
import models.components.renderable.LightRC;
import models.components.renderable.Mesh3RC;
import models.components.renderable.SkyboxRC;
import models.components.updatable.BerylUC;
import models.components.updatable.PhysicsUC;
import models.data.Entity;
import renderEngine.postProcessing.PostProcessingEffect;
import renderEngine.postProcessing.bloom.BloomEffect;
import renderEngine.postProcessing.bloom.BrightCutoffEffect;
import renderEngine.postProcessing.brightness.BrightnessEffect;
import renderEngine.postProcessing.contrast.ContrastEffect;
import renderEngine.postProcessing.gaussianBlur.CompoundGaussianBlur;
import renderEngine.postProcessing.gaussianBlur.GaussianBlur;
import renderEngine.postProcessing.gaussianBlur.HorizontalGaussianBlur;
import tools.input.BerylMouse;
import tools.interfaces.Updatable;
import tools.math.BerylVector;

public class Scene implements Updatable {

	// TODO

	
	private List<Entity> entities;
	private List<BerylUC> uComponents;
	private List<BerylRC> rComponents;
	
	public Scene() {
		entities 		= new ArrayList<>();
		uComponents 	= new ArrayList<>();
		rComponents 	= new ArrayList<>();
	}
	
	@Override
	public void onInit() {
		uComponents.forEach(component->component.onInit());
	}

	@Override
	public void onUpdate() {
		if (getCam() != null) BerylMouse.setPosAndRot(getCam().getPos(), getCam().getRot());
		
		uComponents.forEach(component->component.onUpdate());
	}

	@Override
	public void onLateUpdate() {
		uComponents.forEach(component->component.onLateUpdate());
	}
	

	
	
	
	
	
	
	
	
	
	
	
	public boolean addComponent(BerylComponent component) {
		if (component instanceof BerylRC) rComponents.add((BerylRC)component);
		if (component instanceof BerylUC) uComponents.add((BerylUC)component);
		return true;
	}

	public boolean addEntity(Entity entity) {
		entities.add(entity);
		entity.setScene(this);
		return true;
	}
	
	public Entity getEntity(String name) {
		for (Entity entity : entities) {
			if (entity.getName().equals(name)) return entity;
		}
		return null;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * @return the meshes
	 */
	public List<Mesh3RC> getMesh3RCs() {
		List<Mesh3RC> meshes = new ArrayList<>();
		rComponents.forEach(rc->{
			if (rc instanceof Mesh3RC) meshes.add((Mesh3RC)rc);
		});
		return meshes;
	}

	
	/**
	 * when adding Mesh2RCs, be sure to only add the root to the entity.
	 * @return
	 */
	public List<Mesh2RC> getMesh2RCs() {
		List<Mesh2RC> guis = new ArrayList<>();
		rComponents.forEach(rc->{
			if (rc instanceof Mesh2RC) guis.add((Mesh2RC)rc);
		});
		return guis;
	}

	/**
	 * @return the sky
	 */
	public SkyboxRC getSky() {
		for (BerylRC rc : rComponents) {
			if (rc instanceof SkyboxRC) return (SkyboxRC)rc;
		}
		return null;
	}

	/**
	 * @return the light
	 */
	public LightRC getLight() {
		for (BerylRC rc : rComponents) {
			if (rc instanceof LightRC) return (LightRC)rc;
		}
		return null;
	}

	/**
	 * @return the cam
	 */
	public CameraRC getCam() {
		for (BerylRC rc : rComponents) {
			if (rc instanceof CameraRC) return (CameraRC)rc;
		}
		return null;
	}
	
	/**
	 * @return the PostProcessingEffects
	 */
	public List<PostProcessingEffect> getPostProcessingEffects() {
		List<PostProcessingEffect> ppes = new ArrayList<>();
		for (BerylRC rc : rComponents) {
			if (rc instanceof PostProcessingEffect) ppes.add((PostProcessingEffect)rc);
		}
		return ppes;
	}

	/**
	 * Currently uses this method to initialize the scene, eventually the scene should parse a .bscn file
	 * and initialize entities from there.
	 * @param sceneFile
	 */
	public void initFromFile(String sceneFile) {
		new CameraController(new Entity("Cam", this));
		
		new FPSUpdater(new Entity("FPS Element", this));
		
		new SkyAnimator(new Entity("Skybox", this));
		
		new LightRC(new BerylVector(-1,-1,-1), BerylVector.one(3), new Entity("Light", this));
		
		new GUIHandler(new Entity("GUIs", this));
		
		new Debugger(new Entity("Debug Element", this));
		
		Entity ppes = new Entity("Post Processing Effects", this);
		// none currently
		
		Entity obj = new Entity("Object", this);
		new ObjectController(obj);
		new PhysicsUC(obj);
		
	}

}
