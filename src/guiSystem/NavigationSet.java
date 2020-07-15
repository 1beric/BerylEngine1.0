package guiSystem;

import java.util.ArrayList;
import java.util.List;

import guiSystem.elements.Mesh2RC;

public class NavigationSet {

	private List<Mesh2RC> meshes;
	private String name;
	private boolean visible;
	
	public NavigationSet(String name) {
		this.name = name;
		this.meshes = new ArrayList<>();
		this.setVisible(false);
	}
	
	public void addGUI(Mesh2RC gui) {
		meshes.add(gui);
	}
	
	public void removeGUI(Mesh2RC gui) {
		meshes.remove(gui);
	}

	public List<Mesh2RC> getGUIs() {
		return meshes;
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
	 * @return the currentlyActive
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param currentlyActive the currentlyActive to set
	 */
	public void setVisible(boolean currentlyActive) {
		this.visible = currentlyActive;
		meshes.forEach(mesh->mesh.setActive(currentlyActive));
	}
	
	

}
