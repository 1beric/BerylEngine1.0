package guiSystem.elements;

import models.data.Entity;
import tools.math.BerylVector;

public class Button extends Mesh2RC {
	

	public Button(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
	}  
	
	public Button(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, null, entity);
	}

}
