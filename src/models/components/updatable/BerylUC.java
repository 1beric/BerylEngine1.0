package models.components.updatable;

import models.components.BerylComponent;
import models.data.Entity;
import tools.interfaces.Updatable;

public abstract class BerylUC extends BerylComponent implements Updatable {

	public BerylUC(Entity entity) {
		super(entity);
	}

}
