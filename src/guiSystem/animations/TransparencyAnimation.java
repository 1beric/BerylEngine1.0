package guiSystem.animations;

import java.util.ArrayList;
import java.util.List;

import guiSystem.elements.Mesh2RC;

public class TransparencyAnimation extends Animation {
	
	private float valueFrom;
	private float valueTo;
	private List<Mesh2RC> meshes;
	
	public TransparencyAnimation(float seconds, float from, float to) {
		super(seconds);
		valueFrom = from;
		valueTo = to;
		meshes = new ArrayList<>();
	}
	
	public TransparencyAnimation(float seconds, float from, float to, Mesh2RC mesh) {
		super(seconds);
		valueFrom = from;
		valueTo = to;
		meshes = new ArrayList<>();
		meshes.add(mesh);
	}

	@Override
	protected void process(float factor) {
		for (Mesh2RC mesh : meshes) {
			mesh.getMat().setTransparency(getInterpolator().handle(valueFrom, valueTo, factor));
		}
	}

	@Override
	protected Animation copy() {
		TransparencyAnimation copy = new TransparencyAnimation(getSeconds(), valueTo, valueFrom);
		copy.setAutoLoop(isAutoLoop());
		copy.setAutoReverse(isAutoReverse());
		copy.setDelay(getDelay());
		copy.setFirstTime(true);
		for (Mesh2RC mesh : meshes) copy.addMesh(mesh);
		copy.setInterpolator(getInterpolator());
		copy.setOnFinished(getOnFinished());
		copy.setOnFinishedReverse(getOnFinished());
		return copy;
	}
	
	/**
	 * @return the valueFrom
	 */
	public float getValueFrom() {
		return valueFrom;
	}

	/**
	 * @param valueFrom the valueFrom to set
	 */
	public void setValueFrom(float valueFrom) {
		this.valueFrom = valueFrom;
	}

	/**
	 * @return the valueTo
	 */
	public float getValueTo() {
		return valueTo;
	}

	/**
	 * @param valueTo the valueTo to set
	 */
	public void setValueTo(float valueTo) {
		this.valueTo = valueTo;
	}

	public void addMesh(Mesh2RC mesh) {
		meshes.add(mesh);
	}

}
