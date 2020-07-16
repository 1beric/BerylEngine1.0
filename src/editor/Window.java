package editor;

import models.components.renderable.Transform2D;
import renderEngine.models.Texture;

public abstract class Window {

	private Texture texture;
	private Transform2D transform;
	private boolean locked;
	
	public Window(Transform2D transform) {
		this.transform = transform;
		this.texture = null;
		this.locked = false;
	}
	
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	/**
	 * @return the transform
	 */
	public Transform2D getTransform() {
		return transform;
	}
	/**
	 * @param transform the transform to set
	 */
	public void setTransform(Transform2D transform) {
		this.transform = transform;
	}
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
}
