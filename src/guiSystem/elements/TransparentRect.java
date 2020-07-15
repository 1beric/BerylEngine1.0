package guiSystem.elements;

import models.data.Entity;
import tools.math.BerylVector;

public class TransparentRect extends Rect {

	public TransparentRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		setTransparency(0);
		setTargetable(false);
	}

	public TransparentRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, entity);
		setTransparency(0);
		setTargetable(false);
	}

}
