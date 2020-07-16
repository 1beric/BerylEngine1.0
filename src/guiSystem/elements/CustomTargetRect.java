package guiSystem.elements;

import models.data.Entity;
import guiSystem.RectStyle;
import tools.math.BerylVector;

public class CustomTargetRect extends Rect {

	private BerylVector targetScale;
	
	public CustomTargetRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		targetScale = scale.copy();
	}

	public CustomTargetRect(BerylVector pos, BerylVector scale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, entity);
		targetScale = scale.copy();
	}
	
	public CustomTargetRect(BerylVector pos, BerylVector scale, BerylVector targetScale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		this.targetScale = targetScale;
}

	public CustomTargetRect(BerylVector pos, BerylVector scale, BerylVector targetScale, String posType, String scaleType, Entity entity) {
		super(pos, scale, posType, scaleType, entity);
		this.targetScale = targetScale;
	}
	
	@Override
	public boolean contains(BerylVector point) {
		BerylVector scale = calcScreenScale(targetScale);
		BerylVector pos   = calcScreenPos(RectStyle.CC);
		return  (point.x < pos.x + scale.x/2) &&
				(point.x > pos.x - scale.x/2) &&
				(point.y < pos.y + scale.y/2) &&
				(point.y > pos.y - scale.y/2);
	}

	/**
	 * @return the targetScale
	 */
	public BerylVector getTargetScale() {
		return targetScale;
	}

	/**
	 * @param targetScale the targetScale to set
	 */
	public void setTargetScale(BerylVector targetScale) {
		this.targetScale = targetScale;
	}
	
	
	
}
