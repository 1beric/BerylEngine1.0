package models.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Scene;
import models.components.BerylComponent;
import models.components.renderable.BerylRC;
import models.components.renderable.Transform3D;
import models.components.updatable.BerylUC;

public class Entity {

	private Scene scene;
	
	private String name;
	private Transform3D transform;
	private boolean active;
	
	private Set<BerylUC> uComponents;
	private Set<BerylRC> rComponents;
	
	private Entity parent;
	private List<Entity> children;
	
	public Entity(String name) {
		this.uComponents = new HashSet<>();
		this.rComponents = new HashSet<>();
		this.name = name;
		this.setActive(true);
		this.transform = new Transform3D(this);
		this.rComponents.add(transform);
	}
	
	public Entity(String name, Scene scene, BerylComponent... components) {
		this.uComponents = new HashSet<>();
		this.rComponents = new HashSet<>();
		this.name = name;
		this.setActive(true);
		scene.addEntity(this);
		this.transform = new Transform3D(this);
		this.rComponents.add(transform);
		
		for (BerylComponent component : components) {
			addComponent(component);
		}
	}
	
	@Override
	public String toString() {
		return "Entity: " + name;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Scene getScene() {
		return scene;
	}

	public boolean addComponent(BerylComponent component) {
		if (component instanceof BerylRC) return addComponent((BerylRC)component);
		if (component instanceof BerylUC) return addComponent((BerylUC)component);
		return false;
	}

	public boolean addComponent(BerylRC component) {
		if (!rComponents.add(component)) return false;
		if (scene != null) scene.addComponent(component);
		return true;
	}
	
	public boolean addComponent(BerylUC component) {
		if (!uComponents.add(component)) return false;
		if (scene != null) scene.addComponent(component);
		return true;
	}
	
	public BerylComponent getComponent(Class<? extends BerylComponent> c) {
		for (BerylRC component : rComponents) {
			if (component.getClass().equals(c)) return component;
		}
		for (BerylUC component : uComponents) {
			if (component.getClass().equals(c)) return component;
		}
		return null;
	}
	
	
	
	
	public boolean containsComponent(Class<? extends BerylComponent> c) {
		return getComponent(c) != null;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the transform
	 */
	public Transform3D getTransform() {
		return transform;
	}


	/**
	 * @param transform the transform to set
	 */
	public void setTransform(Transform3D transform) {
		this.transform = transform;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the parent
	 */
	public Entity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Entity parent) {
		if (this.parent != null) this.parent.children.remove(this);
		this.parent = parent;
		if (this.parent != null) this.parent.children.add(this);
	}
	
	public int getChildCount() {
		return children.size();
	}
	
	public Entity getChild(int index) {
		if (index >= children.size()) return null;
		return children.get(index);
	}
	
	public boolean removeChild(int index) {
		if (index >= children.size()) return false;
		children.remove(index);
		return true;
	}
	
	public boolean removeChild(Entity child) {
		return children.remove(child);
	}
	
	/**
	 * @return the uComponents
	 */
	public Set<BerylUC> getUCs() {
		return uComponents;
	}

	/**
	 * @return the rComponents
	 */
	public Set<BerylRC> getRCs() {
		return rComponents;
	}

}
