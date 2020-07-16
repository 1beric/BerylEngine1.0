package guiSystem.elements;

import java.util.ArrayList;
import java.util.List;

import models.components.renderable.BerylRC;
import models.components.renderable.Transform2D;
import models.data.Entity;
import models.data.Material2D;
import guiSystem.RectStyle;
import tools.interfaces.MouseEventHandler;
import tools.io.BerylDisplay;
import tools.io.BerylMouse;
import tools.math.BerylMath;
import tools.math.BerylVector;

/**
 * stores data in screenCoords, but takes input as pixelCoords
 * @author Brandon Erickson
 *
 */
public abstract class Mesh2RC extends BerylRC {

	public enum StorageType {
		PIXELS, 
		PERCENT, 
		PARENT_PIXELS,
		PARENT_PERCENT,
		SCREEN // possibly unused
	}
	
	private Mesh2RC parent;
	private List<Mesh2RC> children;
	private List<TextGUI> texts;
	
	private String name;
	
	private Transform2D transform;
	private Material2D mat;
	
	private MouseEventHandler onMouseEntered;
	private MouseEventHandler onMouseExited; 
	private MouseEventHandler onMouseClicked;
	private boolean hovering;
	private boolean targetable;
	private boolean passThrough;
	
	private StorageType posStorageType;
	private StorageType scaleStorageType;
	private RectStyle originPoint;
	private RectStyle fromParentPoint;
	 
	public Mesh2RC(BerylVector pos, BerylVector scale, String posType, String scaleType, Mesh2RC parent, Entity entity) {
		super(entity);
		
		this.setName("name");
		this.parent = parent;
		
		transform = new Transform2D(pos, scale, null);
		
		this.setPos(pos, posType);
		this.setScale(scale, scaleType);
		this.mat = new Material2D(); // default mat
		
		this.originPoint 	= RectStyle.TL;
		this.fromParentPoint= (parent != null) ? parent.getRectStyle() : RectStyle.TL;
		this.onMouseClicked = () -> {};
		this.onMouseEntered = () -> {};
		this.onMouseExited  = () -> {};
		this.targetable 	= true;
		this.passThrough	= false;
		
		this.children 	= new ArrayList<>();
		this.texts 		= new ArrayList<>();
		
		if (parent != null) parent.addChild(this);
	}

	@Override
	public String toString() {
		return "\n\t" + this.getClass().getSimpleName() + ": " + name + 
				"\n\tPos: " + calcScreenPos(RectStyle.CC) +
				"\n\tScale: " + calcScreenScale() +
				"\n\tActive: " + isActive() +
				"\n\tTransparency: " + getTransparency();
	}
	
	public void update() {
		BerylVector screenPoint = BerylMouse.getScreenRay();
		if (isTargeting(screenPoint)) hover();
		else 						  unHover();
		for (Mesh2RC child : new ArrayList<>(children)) child.update();
	}
	
	/**
	 * takes into effect the targetable and passThrough fields
	 * @param point
	 * @return
	 */
	protected boolean isTargeting(BerylVector point) {
		if (this.contains(point)) {
			if (!targetable) return false;
			for (Mesh2RC child : children) {
				if (child.isTargeting(point) && !child.passThrough) return false;
			}
			return true;
		}
		return false;
	}
	
	
	public void mouseEnter() {
		onMouseEntered.handle();
	}
	
	public void mouseExit() {
		onMouseExited.handle();
	}
	
	public void mouseClick() {
		onMouseClicked.handle();
	}
	
	public void hover() {
		if (BerylMouse.getButtonDown(0)) mouseClick();
		else if (!hovering) {
			mouseEnter();
			hovering = true;
		}
	}
	
	public void unHover() {
		if (hovering) mouseExit();
		hovering = false;
	}
	
	public boolean contains(BerylVector point) {
		BerylVector scale = calcScreenScale();
		BerylVector pos   = calcScreenPos(RectStyle.CC);
		return (point.x < pos.x + scale.x/2) &&
				(point.x > pos.x - scale.x/2) && 
				(point.y < pos.y + scale.y/2) &&
				(point.y > pos.y - scale.y/2);
	}
	
	public Mesh2RC findGUI(BerylVector screenPos) {
		Mesh2RC next;
		for (Mesh2RC child : children) {
			next = child.findGUI(screenPos);
			if (next != null) return next;
		}
		if (contains(screenPos) && targetable) return this;
		return null;
	}
	
	/*
	 * SCREEN SPACE
	 * CALCULATIONS
	 * 
	 */
	
	public BerylVector calcScreenPos() {
		BerylVector screenPos = new BerylVector();
		switch (posStorageType) {
		case PARENT_PIXELS:
			screenPos = parent.calcScreenPos(fromParentPoint);
			screenPos.x += getPos().x / BerylDisplay.WIDTH;
			screenPos.y += getPos().y / BerylDisplay.HEIGHT;
			return screenPos;
		case PIXELS:
			screenPos.x  = (getPos().x / BerylDisplay.WIDTH) * 2 - 1;
			screenPos.y  = 1 - (getPos().y / BerylDisplay.HEIGHT) * 2;
			return screenPos;
		case PARENT_PERCENT:
			screenPos = parent.calcScreenPos(fromParentPoint);
			BerylVector parentScale = parent.calcScreenScale();
			screenPos.x += getPos().x * parentScale.x;
			screenPos.y -= getPos().y * parentScale.y;
			return screenPos;
		case PERCENT:
			screenPos.x = getPos().x * 2 - 1;
			screenPos.y = 1 - getPos().y * 2;
			return screenPos;
		case SCREEN:
			return getPos();
		}
		return null;
	}
	
	public BerylVector calcScreenPos(RectStyle placement) {
		return BerylMath.translatePosition(
				calcScreenPos(),
				calcScreenScale().mult(0.5f),
				originPoint,
				placement);
	}
	
	public BerylVector calcScreenScale() {
		return calcScreenScale(getScale());
	}
	
	public BerylVector calcScreenScale(BerylVector scale) {
		BerylVector screenScale = new BerylVector();
		switch (scaleStorageType) {
		case PARENT_PIXELS:
		case PIXELS:
			screenScale.x = scale.x / BerylDisplay.WIDTH * 2;
			screenScale.y = scale.y / BerylDisplay.HEIGHT * 2;
			return screenScale;
		case PARENT_PERCENT:
			screenScale = parent.calcScreenScale();
			screenScale.x *= scale.x;
			screenScale.y *= scale.y;
			return screenScale;
		case PERCENT:
			screenScale.x = scale.x * 2;
			screenScale.y = scale.y * 2;
			return screenScale;
		case SCREEN:
			return scale;
		}
		return null;
	}
	
	public BerylVector calcScreenBorderWidth() {
		return new BerylVector(
				mat.getBorderWidth() / BerylDisplay.WIDTH,
				mat.getBorderWidth() / BerylDisplay.HEIGHT
		);
	}
	
	public BerylVector calcScreenBorderRadius() {
		BerylVector scale = calcScreenScale();
		return new BerylVector(
				mat.getBorderRadius()/100f * scale.x,
				mat.getBorderRadius()/100f * scale.x
		);
	}
	
	public void orderToFront() {
		if (parent == null) return;
		parent.getChildren().remove(this);
		parent.getChildren().add(this);
	}
	
	
	/*
	 * 
	 * GETTERS
	 *    &
	 * SETTERS
	 * 
	 * 
	 */
	
	/**
	 * @return the pos
	 */
	public BerylVector getPos() {
		return transform.getPos();
	}

	/**
	 * @return the parent
	 */
	public Mesh2RC getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Mesh2RC parent) {
		this.parent = parent;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(BerylVector pos) {
		transform.setPos(pos);
	}
	
	/**
	 * @param pos the pos to set
	 */
	public void setPos(BerylVector pos, String type) {
		setPos(pos);
		if (parent == null && type.equals("pixel")) {
			posStorageType = StorageType.PIXELS;
		} else if (parent == null && type.equals("percent")) {
			posStorageType = StorageType.PERCENT;
		} else if (parent != null && type.equals("pixel")) {
			posStorageType = StorageType.PARENT_PIXELS;
		} else if (parent != null && type.equals("percent")) {
			posStorageType = StorageType.PARENT_PERCENT;
		} else {
			System.out.println(type + " IS NOT SUPPORTED POS STORAGE STYLE.");
		}
	}

	/**
	 * @return the scale
	 */
	public BerylVector getScale() {
		return transform.getScale();
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(BerylVector scale) {
		transform.setScale(scale);
		if (this.texts != null) this.texts.forEach(text->text.setUpdated(true));
	}
	
	/**
	 * @param scale the scale to set
	 */
	public void setScale(BerylVector scale, String type) {
		setScale(scale);
		if (parent == null && type.equals("pixel")) {
			scaleStorageType = StorageType.PIXELS;
		} else if (parent == null && type.equals("percent")) {
			scaleStorageType = StorageType.PERCENT;
		} else if (parent != null && type.equals("pixel")) {
			scaleStorageType = StorageType.PARENT_PIXELS;
		} else if (parent != null && type.equals("percent")) {
			scaleStorageType = StorageType.PARENT_PERCENT;
		} else {
			System.out.println(type + " IS NOT SUPPORTED POS STORAGE STYLE.");
		}
	}
	
	/**
	 * @return the color
	 */
	public BerylVector getColor() {
		return mat.getColor();
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(BerylVector color) {
		this.mat.setColor(color);
	}

	/**
	 * @return the transparency
	 */
	public float getTransparency() {
		return mat.getTransparency();
	}

	/**
	 * @param transparency the transparency to set
	 */
	public void setTransparency(float transparency) {
		this.mat.setTransparency(transparency);
	}

	/**
	 * @return the borderWidth
	 */
	public float getBorderWidth() {
		return mat.getBorderWidth();
	}

	/**
	 * @param borderWidth the borderWidth to set
	 */
	public void setBorderWidth(float borderWidth) {
		this.mat.setBorderWidth(borderWidth);;
		}

	/**
	 * @return the borderColor
	 */
	public BerylVector getBorderColor() {
		return mat.getBorderColor();
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(BerylVector borderColor) {
		this.mat.setBorderColor(borderColor);;
	}

	/**
	 * @return the onMouseEntered
	 */
	public MouseEventHandler getOnMouseEntered() {
		return onMouseEntered;
	}

	/**
	 * @param onMouseEntered the onMouseEntered to set
	 */
	public void setOnMouseEntered(MouseEventHandler onMouseEntered) {
		this.onMouseEntered = onMouseEntered;
	}

	/**
	 * @return the onMouseExited
	 */
	public MouseEventHandler getOnMouseExited() {
		return onMouseExited;
	}

	/**
	 * @param onMouseExited the onMouseExited to set
	 */
	public void setOnMouseExited(MouseEventHandler onMouseExited) {
		this.onMouseExited = onMouseExited;
	}

	/**
	 * @return the onMouseClicked
	 */
	public MouseEventHandler getOnMouseClicked() {
		return onMouseClicked;
	}

	/**
	 * @param onMouseClicked the onMouseClicked to set
	 */
	public void setOnMouseClicked(MouseEventHandler onMouseClicked) {
		this.onMouseClicked = onMouseClicked;
	}
	
	public List<Mesh2RC> getChildren() {
		return children;
	}

	public RectStyle getRectStyle() {
		return originPoint;
	}

	public void setOriginPoint(String s) {
		originPoint = RectStyle.valueOf(s);
	}
	
	public void setOriginPoint(RectStyle s) {
		originPoint = s;
	}

	/**
	 * @return the texts
	 */
	public List<TextGUI> getTexts() {
		return texts;
	}

	/**
	 * @param texts the texts to set
	 */
	public void setTexts(List<TextGUI> texts) {
		this.texts = texts;
	}
	
	public void addText(TextGUI text) {
		texts.add(text);
	}

	/**
	 * @return the posStorageType
	 */
	public StorageType getPosStorageType() {
		return posStorageType;
	}

	/**
	 * @param posStorageType the posStorageType to set
	 */
	public void setPosStorageType(StorageType posStorageType) {
		this.posStorageType = posStorageType;
	}

	/**
	 * @return the scaleStorageType
	 */
	public StorageType getScaleStorageType() {
		return scaleStorageType;
	}

	/**
	 * @param scaleStorageType the scaleStorageType to set
	 */
	public void setScaleStorageType(StorageType scaleStorageType) {
		this.scaleStorageType = scaleStorageType;
	}

	public void addChild(Mesh2RC gui) {
		children.add(gui);
	}

	/**
	 * @return the borderRadius
	 */
	public float getBorderRadius() {
		return mat.getBorderRadius();
	}

	/**
	 * @return the hovering
	 */
	public boolean isHovering() {
		return hovering;
	}

	/**
	 * @param hovering the hovering to set
	 */
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	/**
	 * @param borderRadius the borderRadius to set
	 */
	public void setBorderRadius(float borderRadius) {
		this.mat.setBorderRadius(borderRadius);
	}

	/**
	 * @return the targetable
	 */
	public boolean isTargetable() {
		return targetable;
	}

	/**
	 * @param targetable the targetable to set
	 */
	public void setTargetable(boolean targetable) {
		this.targetable = targetable;
	}

	/**
	 * @return the fromParentPoint
	 */
	public RectStyle getFromParentPoint() {
		return fromParentPoint;
	}

	/**
	 * @param fromParentPoint the fromParentPoint to set
	 */
	public void setFromParentPoint(RectStyle fromParentPoint) {
		this.fromParentPoint = fromParentPoint;
	}

	/**
	 * @return the mat
	 */
	public Material2D getMat() {
		return mat;
	}

	/**
	 * @param mat the mat to set
	 */
	public void setMat(Material2D mat) {
		this.mat = mat;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the transform
	 */
	public Transform2D getTransform() {
		return transform;
	}

	/**
	 * @return the passThrough
	 */
	public boolean isPassThrough() {
		return passThrough;
	}

	/**
	 * @param passThrough the passThrough to set
	 */
	public void setPassThrough(boolean passThrough) {
		this.passThrough = passThrough;
	}
	
}
