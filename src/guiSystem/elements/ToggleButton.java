package guiSystem.elements;

import models.data.Entity;
import tools.interfaces.MouseEventHandler;
import tools.math.BerylVector;

public class ToggleButton extends Mesh2RC {

	private MouseEventHandler onToggledOn;
	private MouseEventHandler onToggledOff;
	private boolean toggled;
	
	public ToggleButton(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		init(); 
	}
	
	public ToggleButton(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, null, entity);
		init();
	}
	
	private void init() {
		this.setOnToggledOff(()->{});
		this.setOnToggledOn(()->{});
		this.toggled = false;
	}
	
	@Override
	public void mouseClick() {
		super.mouseClick();
		if (toggled) toggleOff();
		else		 toggleOn();
	}

	public void toggleOn() {
		onToggledOn.handle();
		toggled = true;
	}

	public void toggleOff() {
		onToggledOff.handle();
		toggled = false;
	}
	
	/**
	 * @return the onToggledOn
	 */
	public MouseEventHandler getOnToggledOn() {
		return onToggledOn;
	}

	/**
	 * @param onToggledOn the onToggledOn to set
	 */
	public void setOnToggledOn(MouseEventHandler onToggledOn) {
		this.onToggledOn = onToggledOn;
	}

	/**
	 * @return the onToggledOff
	 */
	public MouseEventHandler getOnToggledOff() {
		return onToggledOff;
	}

	/**
	 * @param onToggledOff the onToggledOff to set
	 */
	public void setOnToggledOff(MouseEventHandler onToggledOff) {
		this.onToggledOff = onToggledOff;
	}


}
