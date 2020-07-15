/**
 * 
 */
package guiSystem.elements;

import models.data.Entity;
import guiSystem.RectStyles;
import tools.BerylFormattingTools;
import tools.math.BerylVector;

/**
 * @author Brandon Erickson
 *
 */
public class FloatTextGUI extends TextGUI {

	private float value;
	private int round;
	
	/**
	 * @param text
	 * @param fontSize
	 * @param font
	 * @param pos
	 * @param maxLineLength
	 * @param posType
	 * @param rectStyle
	 * @param parent
	 */
	public FloatTextGUI(float value, float fontSize, String font, BerylVector pos, float maxLineLength, String posType,
			RectStyles rectStyle, Mesh2RC parent, Entity entity) {
		super("", fontSize, font, pos, maxLineLength, posType, rectStyle, parent, entity);
		init();
		setValue(value);
	}

	/**
	 * @param text
	 * @param fontSize
	 * @param font
	 * @param pos
	 * @param maxLineLength
	 * @param posType
	 * @param parent
	 */
	public FloatTextGUI(float value, float fontSize, String font, BerylVector pos, float maxLineLength, String posType,
			Mesh2RC parent, Entity entity) {
		super("", fontSize, font, pos, maxLineLength, posType, parent, entity);
		init();
		setValue(value);
	}

	/**
	 * @param text
	 * @param fontSize
	 * @param pos
	 * @param maxLineLength
	 * @param posType
	 * @param parent
	 */
	public FloatTextGUI(float value, float fontSize, BerylVector pos, float maxLineLength, String posType,
			Mesh2RC parent, Entity entity) {
		super("", fontSize, pos, maxLineLength, posType, parent, entity);
		init();
		setValue(value);
	}

	/**
	 * @param text
	 * @param fontSize
	 * @param pos
	 * @param posType
	 * @param parent
	 */
	public FloatTextGUI(float value, float fontSize, BerylVector pos, String posType, Mesh2RC parent, Entity entity) {
		super("", fontSize, pos, posType, parent, entity);
		init();
		setValue(value);
	}
	
	private void init() {
		this.value = 0;
		this.round = 2;
		setText(BerylFormattingTools.format(value, round));
	}
	
	public void setValue(float value) {
		this.value = value;
		setText(BerylFormattingTools.format(value, round));
	}
	
	public void setRoundedValue(int round) {
		this.round = round;
	}

}
