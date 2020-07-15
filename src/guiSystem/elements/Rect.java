package guiSystem.elements;

import models.data.Entity;
import tools.math.BerylVector;

public class Rect extends Mesh2RC {

	public Rect(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
	}
	
	public Rect(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, null, entity);
	}
}
