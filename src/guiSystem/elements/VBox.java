package guiSystem.elements;

import java.util.HashMap;
import java.util.Map;

import models.data.Entity;
import tools.math.BerylVector;

public class VBox extends Mesh2RC {

	private Map<Integer, Mesh2RC> array;
	private int cols;
	private BerylVector minSize;
	private BerylVector maxSize;
	private float minElemHeight;
	private float maxElemHeight;
	
	public VBox(BerylVector pos, String type, Mesh2RC parent, Entity entity) {
		super(pos, BerylVector.zero(), type, "pixel", parent, entity);
		init();
	}
	
	public VBox(BerylVector pos, String type, Entity entity) {
		super(pos, BerylVector.zero(), type, "pixel", null, entity);
		init();
	}
	
	private void init() {
		array = new HashMap<>();
		this.minSize = BerylVector.zero();
		this.maxSize = BerylVector.max(2);
		this.minElemHeight = 0;
		this.maxElemHeight = Float.MAX_VALUE;
		this.cols = 0;
	}

	public boolean add(int col, Mesh2RC gui) {
		if (array.containsKey(col)) getChildren().remove(array.get(col));
		if (gui.getScale().x > maxSize.x) gui.setScale(new BerylVector(maxSize.x, gui.getScale().y));
		if (gui.getScale().y > maxElemHeight) gui.setScale(new BerylVector(gui.getScale().x, maxElemHeight));
		if (gui.getParent() != null) gui.getParent().getChildren().remove(gui);
		gui.setParent(this);
		gui.setScale(gui.getScale(), "pixel");
		addChild(gui);
		array.put(col, gui);
		if (updateScale()) return true;
		array.remove(col);
		getChildren().remove(array.get(col));
		return false;
	}
	
	public boolean add(BerylVector point, Mesh2RC gui) {
		// TODO

		
		updateScale();
		return true;
	}
	
	public Mesh2RC get(int col) {
		return array.get(col);
	}

	public Mesh2RC get(BerylVector point) {
		return array.get(getCol(point));
	}
	
	public int getCol(Mesh2RC gui) {
		// TODO

		return 0;
	}
	
	public int getCol(BerylVector point) {
		// TODO

		return 0;
	}
	
	private boolean updateScale() {
		BerylVector currentScale = BerylVector.zero();
		for (int col=0;col<cols;col++) {
			Mesh2RC gui = get(col);
			if (gui != null) {
				if (gui.getScale().x > currentScale.x) currentScale.set(gui.getScale().x, currentScale.y);
				currentScale.add(new BerylVector(0,gui.getScale().y));
			} else currentScale.add(new BerylVector(0, minElemHeight));
		}
		if (currentScale.x > maxSize.x || currentScale.y > maxSize.y) return false; 
		setScale(currentScale);
		return true;
	}

	
	
	
	
	
	
	
	
	
	/**
	 * @return the minSize
	 */
	public BerylVector getMinSize() {
		return minSize;
	}

	/**
	 * @param minSize the minSize to set
	 */
	public void setMinSize(BerylVector minSize) {
		this.minSize = minSize;
	}

	/**
	 * @return the maxSize
	 */
	public BerylVector getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(BerylVector maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the minElemHeight
	 */
	public float getMinElemHeight() {
		return minElemHeight;
	}

	/**
	 * @param minElemHeight the minElemHeight to set
	 */
	public void setMinElemHeight(float minElemHeight) {
		this.minElemHeight = minElemHeight;
	}

	/**
	 * @return the maxElemHeight
	 */
	public float getMaxElemHeight() {
		return maxElemHeight;
	}

	/**
	 * @param maxElemHeight the maxElemHeight to set
	 */
	public void setMaxElemHeight(float maxElemHeight) {
		this.maxElemHeight = maxElemHeight;
	}

	
	
	
	
	
	
	
	
	
	
	

}
