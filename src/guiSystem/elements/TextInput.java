package guiSystem.elements;

import models.data.Entity;
import tools.math.BerylVector;

public class TextInput extends Rect {

	public TextInput(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, entity);
	}

	public TextInput(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
	}

	

}
