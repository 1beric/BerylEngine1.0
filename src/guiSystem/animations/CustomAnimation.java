package guiSystem.animations;

import java.io.Serializable;

public class CustomAnimation extends Animation {
	
	public interface ProcessHandler extends Serializable {
		void handle(float factor);
	}

	private ProcessHandler customProcess;
	
	public CustomAnimation(float seconds, ProcessHandler process) {
		super(seconds);
		customProcess = process;
	}

	@Override
	protected void process(float factor) {
		customProcess.handle(factor);
	}

	@Override
	protected Animation copy() {
		Animation copy = new CustomAnimation(getSeconds(), customProcess);
		copy.setAutoLoop(isAutoLoop());
		copy.setAutoReverse(isAutoReverse());
		copy.setDelay(getDelay());
		copy.setFirstTime(true);
		copy.setInterpolator(getInterpolator());
		copy.setOnFinished(getOnFinished());
		copy.setOnFinishedReverse(getOnFinished());
		return copy;
	}

}
