package tools.interfaces;

import models.data.Entity;

@FunctionalInterface
public interface EntityPasser {
	public void handle(Entity entity);
}
