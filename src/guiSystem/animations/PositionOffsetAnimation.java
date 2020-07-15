package guiSystem.animations;

import java.util.ArrayList;
import java.util.List;

import models.components.renderable.Transform;
import tools.BerylTime;
import tools.math.BerylVector;

public class PositionOffsetAnimation extends Animation {

	private BerylVector offset;
	private List<Transform> transforms;
	
	public PositionOffsetAnimation(float seconds, BerylVector offset, Transform transform) {
		super(seconds);
		transforms = new ArrayList<>();
		transforms.add(transform);
		this.offset = offset.copy();
	}

	public PositionOffsetAnimation(float seconds, BerylVector offset) {
		super(seconds);
		transforms = new ArrayList<>();
		this.offset = offset.copy();
	}

	@Override
	protected void process(float factor) {
		for (Transform t : transforms) {
			if (isReversed()) t.setPos(t.getPos().add(offset.mult(-BerylTime.getDelta()/getSeconds())));
			else 			  t.setPos(t.getPos().add(offset.mult( BerylTime.getDelta()/getSeconds())));
		}
	}

	@Override
	protected Animation copy() {
		PositionOffsetAnimation copy = new PositionOffsetAnimation(getSeconds(), offset);
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
	 * @return the offset
	 */
	public BerylVector getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(BerylVector offset) {
		this.offset = offset.copy();
	}

	public void addTransform(Transform t) {
		transforms.add(t);
	}

}
