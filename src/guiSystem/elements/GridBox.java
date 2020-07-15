package guiSystem.elements;

import java.util.HashMap;
import java.util.Map;

import models.data.Entity;
import tools.math.BerylVector;

public class GridBox extends Mesh2RC {

	private VBox grid; // VBox of many HBox
	private BerylVector minSize;
	private BerylVector maxSize;
	private BerylVector minElemSize;
	private BerylVector maxElemSize;
	private int rows;
	private int cols;
	
	public GridBox(BerylVector pos, String posType, Entity entity) {
		super(pos, BerylVector.zero(), posType, "pixel", null, entity);
		init();
	}
	
	public GridBox(BerylVector pos, String posType, Mesh2RC parent, Entity entity) {
		super(pos, BerylVector.zero(), posType, "pixel", parent, entity);
		init();
	}
	
	public GridBox(BerylVector pos, String posType, int rows, int cols, Entity entity) {
		super(pos, BerylVector.zero(), posType, "pixel", null, entity);
		init();
		this.rows = rows;
		this.cols = cols;
	}
	
	public GridBox(BerylVector pos, String posType, int rows, int cols, Mesh2RC parent, Entity entity) {
		super(pos, BerylVector.zero(), posType, "pixel", parent, entity);
		init();
		this.rows = rows;
		this.cols = cols;
	}
	
	private void init() {
		Entity e = new Entity("Grid");
		getEntity().getScene().addEntity(e);
		this.grid = new VBox(BerylVector.zero(),"pixel",this,e);
		e.addComponent(grid);
		this.minSize = BerylVector.zero();
		this.maxSize = BerylVector.max(2);
		this.minElemSize = BerylVector.zero();
		this.maxElemSize = BerylVector.max(2);
		this.rows = 0;
		this.cols = 0;
	}

	public boolean add(int row, int col, Mesh2RC gui) {
		// TODO

		return true;
	}
	
	public void add(BerylVector point, Mesh2RC gui) {
		// TODO

	}
	
	public void addNextAvailRow(int col, Mesh2RC gui) {
		// TODO

	}
	
	public void addNextAvailCol(int row, Mesh2RC gui) {
		// TODO

	}
	
	public void remove(Mesh2RC gui) {
		// TODO

	}
	
	public void remove(int row, int col) {
		// TODO

	}
	
	public void remove(BerylVector point) {
		// TODO

	}
	
	public Mesh2RC get(int row, int col) {
		// TODO
		return null;
	}
	
	public Mesh2RC get(BerylVector point) {
		// TODO

		return null;
	}

	private boolean updateScale() {
		// TODO

		return true;
	}
	
	/**
	 * @return the minWidth
	 */
	public BerylVector getMinWidth() {
		return minSize;
	}

	/**
	 * @param minWidth the minWidth to set
	 */
	public void setMinWidth(BerylVector minWidth) {
		this.minSize = minWidth;
	}

	/**
	 * @return the maxWidth
	 */
	public BerylVector getMaxWidth() {
		return maxSize;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setMaxWidth(BerylVector maxWidth) {
		this.maxSize = maxWidth;
	}

	/**
	 * @return the minElemWidth
	 */
	public BerylVector getMinElemWidth() {
		return minElemSize;
	}

	/**
	 * @param minElemWidth the minElemWidth to set
	 */
	public void setMinElemWidth(BerylVector minElemWidth) {
		this.minElemSize = minElemWidth;
	}

	/**
	 * @return the maxElemWidth
	 */
	public BerylVector getMaxElemWidth() {
		return maxElemSize;
	}

	/**
	 * @param maxElemWidth the maxElemWidth to set
	 */
	public void setMaxElemWidth(BerylVector maxElemWidth) {
		this.maxElemSize = maxElemWidth;
	}
	
}
