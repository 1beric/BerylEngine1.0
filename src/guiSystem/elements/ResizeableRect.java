package guiSystem.elements;

import models.data.Entity;
import guiSystem.RectStyle;
import tools.io.BerylMouse;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class ResizeableRect extends Rect {
	
	private static final float GRABBABLE_PIXELS = 10;
	
	private boolean trackingMouse;
	private boolean maintainAspectRatio;
	private BerylVector widthBounds;
	private BerylVector heightBounds;

	public ResizeableRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		init();
	}

	public ResizeableRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, entity);
		init();
	}
	
	private void init() {
		trackingMouse = false;
		maintainAspectRatio = false;
		widthBounds = new BerylVector(Float.MIN_VALUE, Float.MAX_VALUE);
		heightBounds = new BerylVector(Float.MIN_VALUE, Float.MAX_VALUE);
	}
	
	@Override
	public void hover() {
		if (BerylMouse.getButtonDown(0)) mouseDown();
		else if (BerylMouse.getButtonHeld(0)) updateCursor();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
		else if (!isHovering()) {
			mouseEnter();
			setHovering(true);
		}
		getChildren().forEach(child->child.unHover());
	}
	
	@Override
	public void unHover() {
		super.unHover();
		if (BerylMouse.getButtonHeld(0)) updateCursor();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
	}
	
	private void mouseDown() {
		if (trackingMouse) return;
		trackingMouse = true;
	}
	
	private void updateCursor() {
		if (!trackingMouse) return;
		if (maintainAspectRatio) {
			// updateBoth
		} else {
			RectStyle cursorPoint = getMousePoint();
//			cursor.getPos().x = BerylMath.clamp((MouseModule.getScreenRay().x-calcScreenPos().x)/calcScreenScale().x, -.5f, .5f);
		}
	}
	
	private void mouseUp() {
		if(!trackingMouse) return;
		trackingMouse = false;
	}
	
	private RectStyle getMousePoint() {
		BerylVector point = BerylMouse.getScreenRay();
		BerylVector span  = calcScreenScale().mult(0.5f).add(-GRABBABLE_PIXELS);
		BerylVector pos   = calcScreenPos(RectStyle.CC);
		if (!contains(point)) return RectStyle.CC;
		if (point.x > pos.x + span.x && point.y > pos.y + span.y) return RectStyle.TR;
		else if (point.x > pos.x + span.x && point.y < pos.y - span.y) return RectStyle.BR;
		else if (point.x > pos.x + span.x) return RectStyle.CR;
		else if (point.x < pos.x - span.x && point.y > pos.y + span.y) return RectStyle.TL;
		else if (point.x < pos.x - span.x && point.y < pos.y - span.y) return RectStyle.BL;
		else if (point.x < pos.x - span.x) return RectStyle.CL;
		else if (point.y > pos.y + span.y) return RectStyle.TC;
		else if (point.y < pos.y - span.y) return RectStyle.BC;
		else return RectStyle.CC;
	}

}
