package models.components;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Scene;
import models.data.Entity;
import tools.editorAnnotations.GetMethod;
import tools.editorAnnotations.SetMethod;
import tools.editorAnnotations.ShowInEditor;

public abstract class BerylComponent { 

	private Entity entity;
	private boolean active;
	
	public BerylComponent(Entity entity) {
		this.entity = entity;
		if (this.entity != null) this.entity.addComponent(this);
		active = true;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "( " + entity + " )";
	}

	public BerylComponent getComponent(Class<? extends BerylComponent> c) {
		return getEntity().getComponent(c);
	}
	
	public BerylComponent getComponent(String name, Class<? extends BerylComponent> c) {
		return getScene().getEntity(name).getComponent(c);
	}
	
	public Entity getEntity(String name) {
		return getScene().getEntity(name);
	}
	
	public Scene getScene() {
		return getEntity().getScene();
	}

	/**
	 * @return the entity this component is attached to
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to attach this component to
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return if this is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active if this is active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Set<ShowField> getEditorFields() {
		Map<String, ShowField> fields = new HashMap<>();
		for (Field f : getClass().getDeclaredFields()) {
			ShowInEditor show = f.getAnnotation(ShowInEditor.class);
			if (show != null || Modifier.isPublic(f.getModifiers())) {
				fields.put(f.getName(), new ShowField(f));
			}
		}
		for (Method m : getClass().getMethods()) {
			GetMethod get = m.getAnnotation(GetMethod.class);
			if (get != null) fields.get(get.value()).getter = m;
			SetMethod set = m.getAnnotation(SetMethod.class);
			if (set != null) fields.get(set.value()).setter = m;
		}
		return new HashSet<>(fields.values());
	}
	
	
	
	public class ShowField {
		public Field field;
		public Method getter;
		public Method setter;
		public ShowField(Field field) {
			this.field = field;
		}
		public boolean isFieldPublic() {
			return Modifier.isPublic(field.getModifiers());
		}
		@Override
		public String toString() {
			if (isFieldPublic()) return field.getName();
			return field.getName() + ": " + getter.getName() + "(), " + setter.getName() + "()";
		}
	}
}
