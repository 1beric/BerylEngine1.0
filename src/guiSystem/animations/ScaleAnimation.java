package guiSystem.animations;


import java.util.ArrayList;
import java.util.List;

import models.components.renderable.Transform;
import models.components.renderable.Transform3D;
import tools.math.BerylVector;

public class ScaleAnimation extends Animation {

	private BerylVector from;
	private BerylVector to;
	private List<Transform> transforms;
	
	public ScaleAnimation(float seconds, BerylVector from, BerylVector to) {
		super(seconds);
		this.from = from.copy();
		this.to = to.copy();
		transforms = new ArrayList<>();
	}
	
	public ScaleAnimation(float seconds, BerylVector from, BerylVector to, Transform transform) {
		super(seconds);
		this.from = from.copy();
		this.to = to.copy();
		transforms = new ArrayList<>();
		transforms.add(transform);
	}

	@Override
	protected void process(float factor) {
		for (Transform t : transforms) {
			BerylVector scale = new BerylVector();
			scale.x = getInterpolator().handle(from.x, to.x, factor);
			scale.y = getInterpolator().handle(from.y, to.y, factor);
			if (t instanceof Transform3D) scale.z = getInterpolator().handle(from.z, to.z, factor);
			t.setScale(scale);
		}
	}

	@Override
	protected Animation copy() {
		ScaleAnimation copy = new ScaleAnimation(getSeconds(), to, from);
		copy.setAutoLoop(isAutoLoop());
		copy.setAutoReverse(isAutoReverse());
		copy.setDelay(getDelay());
		copy.setFirstTime(true);
		for (Transform t : transforms) copy.addTransform(t);
		copy.setInterpolator(getInterpolator());
		copy.setOnFinished(getOnFinished());
		copy.setOnFinishedReverse(getOnFinished());
		return copy;
	}

	/**
	 * @return the valueFrom
	 */
	public BerylVector getValueFrom() {
		return from;
	}

	/**
	 * @param valueFrom the valueFrom to set
	 */
	public void setValueFrom(BerylVector valueFrom) {
		this.from = valueFrom.copy();
	}

	/**
	 * @return the valueTo
	 */
	public BerylVector getValueTo() {
		return to;
	}

	/**
	 * @param valueTo the valueTo to set
	 */
	public void setValueTo(BerylVector valueTo) {
		this.to = valueTo.copy();
	}

	public void addTransform(Transform t) {
		transforms.add(t);
	}
	
}
