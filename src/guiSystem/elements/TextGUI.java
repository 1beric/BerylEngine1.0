package guiSystem.elements;

import models.data.Entity;
import guiSystem.RectStyle;
import settings.Constants;
import tools.io.BerylDisplay;
import tools.math.BerylVector;

public class TextGUI extends Mesh2RC {

	private static final String DEFAULT_FONT = (String) Constants.get("DEFAULT_FONT");
	
	private String text;
	private float fontSize;
	private String font;
	private boolean updated;
	private boolean scaleOnHeight;
	
	public TextGUI(
			String text, 
			float fontSize, 
			String font, 
			BerylVector pos, 
			float maxLineLength, 
			String posType,
			RectStyle rectStyle, 
			Mesh2RC parent,
			Entity entity) {
		super(pos, new BerylVector(maxLineLength, 1f), posType, "percent", null, entity);
		this.setParent(parent);
		this.setPos(pos, posType);
		this.setScale(new BerylVector(maxLineLength, 1f), "percent");
		this.text 		= text;
		this.updated 	= true;
		this.fontSize 	= fontSize;
		this.font 		= font;
		this.scaleOnHeight = true;
		this.setOriginPoint(rectStyle);
		this.setFromParentPoint(rectStyle);
		parent.getTexts().add(this);
	}
	
	public TextGUI(
			String text,
			float fontSize,
			String font,
			BerylVector pos,
			float maxLineLength,
			String posType,
			Mesh2RC parent,
			Entity entity) {
		super(pos, new BerylVector(maxLineLength, 1f), posType, "percent", null, entity);
		this.setParent(parent);
		this.setPos(pos, posType);
		this.setScale(new BerylVector(maxLineLength, 1f), "percent");
		this.text 		= text;
		this.updated 	= true;
		this.fontSize 	= fontSize;
		this.font 		= font;
		this.scaleOnHeight = true;
		this.setOriginPoint(RectStyle.CC);
		this.setFromParentPoint(RectStyle.CC);
		parent.getTexts().add(this);
	}
	
	public TextGUI(
			String text, 
			float fontSize, 
			BerylVector pos, 
			float maxLineLength, 
			String posType,
			Mesh2RC parent, 
			Entity entity) {
		super(pos, new BerylVector(maxLineLength, 1f), posType, "percent", null, entity);
		this.setParent(parent);
		this.setPos(pos, posType);
		this.setScale(new BerylVector(maxLineLength, 1f), "percent");
		this.text 		= text;
		this.updated 	= true;
		this.fontSize 	= fontSize;
		this.font 		= DEFAULT_FONT;
		this.scaleOnHeight = true;
		this.setOriginPoint(RectStyle.CC);
		this.setFromParentPoint(RectStyle.CC);
		parent.getTexts().add(this);
	}
	
	public TextGUI(
			String text, 
			float fontSize, 
			BerylVector pos, 
			String posType,
			Mesh2RC parent, 
			Entity entity) {
		super(pos, new BerylVector(0.5f, 1f), posType, "percent", null, entity);
		this.setParent(parent);
		this.setPos(pos, posType);
		this.setScale(new BerylVector(0.5f, 1f), "percent");
		this.text 		= text;
		this.updated 	= true;
		this.fontSize 	= fontSize;
		this.font 		= DEFAULT_FONT;
		this.scaleOnHeight = true;
		this.setOriginPoint(RectStyle.CC);
		this.setFromParentPoint(RectStyle.CC);
		parent.getTexts().add(this);
	}

	@Override
	public BerylVector calcScreenPos() {
		BerylVector screenPos = new BerylVector();
		switch (getPosStorageType()) {
		case PARENT_PIXELS:
			screenPos = getParent().calcScreenPos(getFromParentPoint());
			screenPos.x = (screenPos.x + 1) / 2;
			screenPos.y = (1 - screenPos.y) / 2;
			screenPos.x += getPos().x / BerylDisplay.WIDTH;
			screenPos.y += getPos().y / BerylDisplay.HEIGHT;
			return screenPos;
		case PIXELS:
			screenPos.x  = getPos().x / BerylDisplay.WIDTH;
			screenPos.y  = getPos().y / BerylDisplay.HEIGHT;
			return screenPos;
		case PARENT_PERCENT:
			screenPos = getParent().calcScreenPos(getFromParentPoint());
			screenPos.x = (screenPos.x + 1) / 2;
			screenPos.y = (1 - screenPos.y) / 2;
			BerylVector parentScale = getParent().calcScreenScale();
			screenPos.x += getPos().x * parentScale.x / 2f;
			screenPos.y -= getPos().y * parentScale.y / 2f;
			return screenPos;
		case PERCENT:
			screenPos.x = getPos().x;
			screenPos.y = getPos().y;
			return screenPos;
		case SCREEN:
			return getPos();
		}
		return null;
	}

	@Override
	public void setOriginPoint(RectStyle s) {
		super.setOriginPoint(s);
		setFromParentPoint(s);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
		this.updated = true;
	}

	/**
	 * @return the fontSize
	 */
	public float getFontSize() {
		return (scaleOnHeight) ? fontSize * calcScreenScale().y : fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize) {
		updated = true;
		this.fontSize = fontSize;
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @return the maxLineLength
	 */
	public float getMaxLineLength() {
		return calcScreenScale().x;
	}

	/**
	 * @return the centered
	 */
	public boolean isCentered() {
		return getRectStyle() == RectStyle.CC ||
				getRectStyle() == RectStyle.TC ||
				getRectStyle() == RectStyle.BC;
	}

	/**
	 * @return the centeredVertically
	 */
	public boolean isCenteredVertically() {
		return getRectStyle() == RectStyle.CC ||
				getRectStyle() == RectStyle.CL ||
				getRectStyle() == RectStyle.CR;
	}

	/**
	 * @return the updated
	 */
	public boolean isUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public void setScale(BerylVector scale) {
		super.setScale(scale);
		updated = true;
	}

	/**
	 * @return the scaleOnHeight
	 */
	public boolean isScaleOnHeight() {
		return scaleOnHeight;
	}

	/**
	 * @param scaleOnHeight the scaleOnHeight to set
	 */
	public void setScaleOnHeight(boolean scaleOnHeight) {
		this.scaleOnHeight = scaleOnHeight;
	}

	


}

