package guiSystem.elements;

import models.data.Entity;
import guiSystem.RectStyle;
import tools.BerylFormattingTools;
import tools.math.BerylVector;

public class VectorStack extends Mesh2RC {

	private TextGUI x;
	private TextGUI y;
	private TextGUI z;
	private TextGUI w;
	private int round;
	
	public VectorStack(BerylVector pos, BerylVector scale, String posType, String scaleType, BerylVector vector, Mesh2RC parent, Entity entity) {
		super(pos, scale, posType, scaleType, parent, entity);
		init(vector);
	}
	
	public VectorStack(BerylVector pos, BerylVector scale, String posType, String scaleType, BerylVector vector, Entity entity) {
		super(pos, scale, posType, scaleType, null, entity);
		init(vector);
	}
	
	private void init(BerylVector vector) {
		round = 3;
		x = new TextGUI("x: " + BerylFormattingTools.format(vector.x, round), 2.5f, "candara", new BerylVector(.1f, .35f ), .5f, "percent", RectStyle.CL, this, null);
		y = new TextGUI("y: " + BerylFormattingTools.format(vector.y, round), 2.5f, "candara", new BerylVector(.1f, .125f), .5f, "percent", RectStyle.CL, this, null);
		z = new TextGUI("z: " + BerylFormattingTools.format(vector.z, round), 2.5f, "candara", new BerylVector(.1f,-.125f), .5f, "percent", RectStyle.CL, this, null);
		w = new TextGUI("w: " + BerylFormattingTools.format(vector.w, round), 2.5f, "candara", new BerylVector(.1f,-.35f ), .5f, "percent", RectStyle.CL, this, null);
		
		addText(x);
		addText(y);
		addText(z);
		addText(w);
	}
	
	public void setVector(BerylVector vector) {
		x.setText("x: " + BerylFormattingTools.format(vector.x, round));
		y.setText("y: " + BerylFormattingTools.format(vector.y, round));
		z.setText("z: " + BerylFormattingTools.format(vector.z, round));
		w.setText("w: " + BerylFormattingTools.format(vector.w, round));
	}
	
	public void setTextColor(BerylVector color) {
		x.setColor(color);
		y.setColor(color);
		z.setColor(color);
		w.setColor(color);
	}

	/**
	 * @return the round
	 */
	public double getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	
	
}
