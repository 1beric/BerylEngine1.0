package guiSystem.elements;


import models.data.Entity;
import guiSystem.RectStyle;
import tools.Interpolators;
import tools.Interpolators.Interpolator;
import tools.interfaces.FloatPasser;
import tools.io.BerylMouse;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class Slider extends CustomTargetRect {

	private Rect cursor;
	private boolean trackingMouse;
	private float snapAmount;
	private boolean updateOnHold;
	private BerylVector bounds;
	private Interpolator interpolator;
	private FloatPasser onSetValue;
	private TextGUI aboveText;
	private FloatTextGUI cursorText;
	private FloatTextGUI lowerBoundText;
	private FloatTextGUI upperBoundText;
	
	public Slider(BerylVector pos, float length, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(pos, new BerylVector(length, length / 100f), posType, scaleType, parent, entity);
		init();
	}
	
	public Slider(BerylVector pos, float length, String posType, String scaleType, Entity entity) {
		super(pos, new BerylVector(length, length / 100f), posType, scaleType, null, entity);
		init();
	}
	
	private void init() {
		onSetValue = f -> {};
		bounds = new BerylVector(0,1);
		interpolator = Interpolators.LINEAR;
		
		cursor = new Rect(new BerylVector(0.5f,0), new BerylVector(7.5f,25), "percent", "pixel", this, null);
		cursor.setOriginPoint(RectStyle.CC);
		cursor.setFromParentPoint(RectStyle.CL);
		cursor.setBorderRadius(100f);
		cursor.setTargetable(false);
		
		setTargetScale(new BerylVector(getScale().x,cursor.getScale().y));

		
		aboveText = new TextGUI("", 100, new BerylVector(0,-getScale().y*8), "pixel", this, null);
		aboveText.setColor(BerylVector.one(3));
		aboveText.setOriginPoint(RectStyle.TC);
		
		cursorText = new FloatTextGUI(0.5f, 1, new BerylVector(0,cursor.getScale().y*1.2f), "pixel", cursor, null);
		cursorText.setColor(BerylVector.one(3));
		cursorText.setOriginPoint(RectStyle.BC);
		cursorText.setScaleOnHeight(false); 
		cursorText.setScaleStorageType(StorageType.PERCENT);
		
		lowerBoundText = new FloatTextGUI(0, 1, new BerylVector(-10,0), "pixel", this, null);
		lowerBoundText.setColor(BerylVector.one(3));
		lowerBoundText.setOriginPoint(RectStyle.CR);
		lowerBoundText.setFromParentPoint(RectStyle.CL);
		lowerBoundText.setScaleOnHeight(false);
		lowerBoundText.setScaleStorageType(StorageType.PERCENT);
		
		upperBoundText = new FloatTextGUI(1, 1, new BerylVector(10,0), "pixel", this, null);
		upperBoundText.setColor(BerylVector.one(3));
		upperBoundText.setOriginPoint(RectStyle.CL);
		upperBoundText.setFromParentPoint(RectStyle.CR);
		upperBoundText.setScaleOnHeight(false);
		upperBoundText.setScaleStorageType(StorageType.PERCENT);
		
		this.roundBoundsText(0);
	}
	
	private void updateCursor() {
		if (!trackingMouse) return;
		if (snapAmount > 0) {
			float val = (BerylMouse.getScreenRay().x-calcScreenPos(RectStyle.CL).x)/calcScreenScale().x;
			val -= (val+snapAmount/2) % snapAmount;
			cursor.getPos().x = BerylMath.clamp(val+snapAmount/2,0,1);
		} else {
			cursor.getPos().x = BerylMath.clamp((BerylMouse.getScreenRay().x-calcScreenPos(RectStyle.CL).x)/calcScreenScale().x, 0, 1);
		}
		if (updateOnHold) setValue();
	}

	private void mouseDown() {
		if (trackingMouse) return;
		trackingMouse = true;
	}
	
	private void mouseUp() {
		if(!trackingMouse) return;
		trackingMouse = false;
		setValue();
	}
	
	private void setValue() {
		onSetValue.handle(getCurrentValue());
		cursorText.setValue(getCurrentValue());
	}
	
	public void showBounds(boolean show) {
		upperBoundText.setActive(show);
		lowerBoundText.setActive(show);
	}
	
	public void showCursorValue(boolean show) {
		cursorText.setActive(show);	
	}
	
	public void showAboveText(boolean show) {
		aboveText.setActive(show);	
	}
	
	@Override
	public void hover() {
		if (BerylMouse.getButtonDown(0)) 	mouseDown();
		else if (BerylMouse.getButtonHeld(0)) updateCursor();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
		else if (!isHovering()) {
			mouseEnter();
			setHovering(true);
		}
	}
	
	@Override
	public void unHover() {
		super.unHover();
		if (BerylMouse.getButtonHeld(0)) updateCursor();
		else if (BerylMouse.getButtonUp(0)) mouseUp();
	}
	
	public void setCursorColor(BerylVector color) {
		cursor.setColor(color);
	}
	
	public float getCurrentValue() {
		return interpolator.handle(bounds.x, bounds.y, cursor.getPos().x);
	}
	
	public void setSnap(float snap) {
		snapAmount = snap;
	}
	
	public void setOnSetValue(FloatPasser newOnSetValue) {
		onSetValue = newOnSetValue;
	}

	/**
	 * @return the updateOnHold
	 */
	public boolean isUpdateOnHold() {
		return updateOnHold;
	}

	/**
	 * @param updateOnHold the updateOnHold to set
	 */
	public void setUpdateOnHold(boolean updateOnHold) {
		this.updateOnHold = updateOnHold;
	}
	
	public void setText(String text) {
		this.aboveText.setText(text);
	}

	/**
	 * @return the bounds
	 */
	public BerylVector getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(BerylVector bounds) {
		this.bounds = bounds;
		this.updateBoundsText();
		this.setValue();
	}
	
	public void roundValueText(int digits) {
		cursorText.setRoundedValue(digits);
		this.setValue();
	}
	
	public void roundBoundsText(int digits) {
		lowerBoundText.setRoundedValue(digits);
		upperBoundText.setRoundedValue(digits);
		this.updateBoundsText();
	}

	public void updateBoundsText() {
		this.lowerBoundText.setValue(bounds.x);
		this.upperBoundText.setValue(bounds.y);
	}

	/**
	 * @return the interpolator
	 */
	public Interpolator getInterpolator() {
		return interpolator;
	}

	/**
	 * @param interpolator the interpolator to set
	 */
	public void setInterpolator(Interpolator interpolator) {
		this.interpolator = interpolator;
	}
	
}
