package guiSystem.animations;


import java.util.ArrayList;
import java.util.List;

import models.data.Material;
import tools.math.BerylVector;

public class ColorAnimation extends Animation {

	private BerylVector from;
	private BerylVector to;
	private List<Material> mats;
	
	public ColorAnimation(float seconds, BerylVector from, BerylVector to) {
		super(seconds);
		this.from = from.copy();
		this.to = to.copy();
		mats = new ArrayList<>();
	}
	
	public ColorAnimation(float seconds, BerylVector from, BerylVector to, Material mat) {
		super(seconds);
		this.from = from.copy();
		this.to = to.copy();
		mats = new ArrayList<>();
		mats.add(mat);
	}
	
	@Override
	protected void process(float factor) {
		for (Material mat : mats) {
			BerylVector col = new BerylVector();
			col.x = getInterpolator().handle(from.x, to.x, factor);
			col.y = getInterpolator().handle(from.y, to.y, factor);
			col.z = getInterpolator().handle(from.z, to.z, factor);
			mat.setColor(col);
		}
	}

	@Override
	protected Animation copy() {
		ColorAnimation copy = new ColorAnimation(getSeconds(), to, from);
		copy.setAutoLoop(isAutoLoop());
		copy.setAutoReverse(isAutoReverse());
		copy.setDelay(getDelay());
		copy.setFirstTime(true);
		for (Material mat : mats) copy.addMaterial(mat);
		copy.setInterpolator(getInterpolator());
		copy.setOnFinished(getOnFinished());
		copy.setOnFinishedReverse(getOnFinished());
		return copy;
	}
	
	public void addMaterial(Material mat) {
		mats.add(mat);
	}

}
