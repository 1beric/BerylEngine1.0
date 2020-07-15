package guiSystem.elements;

import guiSystem.RectStyles;
import models.data.Entity;
import renderEngine.BerylDisplay;
import tools.input.BerylMouse;
import tools.math.BerylVector;

public class ParentPosController extends Rect {

	private boolean trackingMouse;
	
	public ParentPosController(Mesh2RC parent, Entity entity) {
		super(BerylVector.zero(), new BerylVector(1,.1f), "percent", "percent", parent, entity);
		setOriginPoint(RectStyles.TC);
		setFromParentPoint(RectStyles.TC);
		setTransparency(0);
		trackingMouse = false;
	}
	
	public ParentPosController(float height, Mesh2RC parent, Entity entity) {
		super(BerylVector.zero(), new BerylVector(1,height), "percent", "percent", parent, entity);
		setOriginPoint(RectStyles.TC);
		setFromParentPoint(RectStyles.TC);
		setTransparency(0);
		trackingMouse = false;
	}
	
	@Override
	public void hover() {
		if (BerylMouse.getButtonDown(0)) 	mouseDown();
		else if (BerylMouse.getButtonHeld(0)) updateParentPos();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
		else if (!isHovering()) {
			mouseEnter();
			setHovering(true);
		}
	}
	
	@Override
	public void unHover() {
		super.unHover();
		if (BerylMouse.getButtonHeld(0)) updateParentPos();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
	}
	
	@Override
	protected boolean isTargeting(BerylVector point) {
		if (trackingMouse) return true;
		return super.isTargeting(point);
	}

	private void mouseDown() {
		if (trackingMouse) return;
		trackingMouse = true;
	}
	
	private void mouseUp() {
		if(!trackingMouse) return;
		trackingMouse = false;
		updateParentPos();
	}
	
	private void updateParentPos() {
		if (!trackingMouse) return;
		getParent().getPos().x += BerylMouse.getDX()/(float)BerylDisplay.WIDTH;
		getParent().getPos().y += BerylMouse.getDY()/(float)BerylDisplay.HEIGHT;
		getParent().orderToFront();
	}

}
