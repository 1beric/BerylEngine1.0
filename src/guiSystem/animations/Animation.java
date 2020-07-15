package guiSystem.animations;

import java.util.ArrayList;
import java.util.List;

import models.components.renderable.BerylRC;
import tools.BerylTime;
import tools.Interpolators;
import tools.Interpolators.Interpolator;

public abstract class Animation {

	private static final List<Animation> animations = new ArrayList<>();
	
	public static void updateAnimations() {
		animations.forEach(anim->anim.update());
	}
	
	public static void clearAnimations() {
		animations.clear();
	}
	
	public interface EventHandler {
		void handle();
	}
	
	private float seconds; 
	private float delay;
	private Interpolator interpolator;
	private EventHandler onFinished;
	private EventHandler onFinishedReverse;
	
	private boolean firstTime	= true;
	private boolean playing 	= false;
	private float timePlaying 	= 0;
	private float delayTime 	= 0;
	private boolean reversed 	= false;
	private boolean autoLoop 	= false;
	private boolean autoReverse = false;
	
	protected Animation(float seconds) {
		this.seconds 			= seconds;
		this.delay	 			= 0;
		this.interpolator 		= Interpolators.LINEAR;
		this.onFinished 		= () -> {};
		this.onFinishedReverse 	= () -> {};
		animations.add(this);
	}
	
	protected abstract void process(float factor);
	protected abstract Animation copy();

	private void update() {
		if (!this.playing) return;
		if (delayTime < delay) {
			delayTime += BerylTime.getDelta();
			return;
		}
		if (timePlaying > seconds && !reversed) {
			finish();
			return;
		} else if (timePlaying < 0 && reversed) {
			finishReverse();
			return;
		}
		process(timePlaying/seconds);
		
		timePlaying += BerylTime.getDelta() * (reversed ? -1 : 1);
	}
	
	public void play() {
		reversed = false;
		playing = true;
		if (firstTime) timePlaying = 0;
		firstTime = false;
		delayTime = 0;
	}
	
	public void playRestart() {
		reversed = false;
		playing = true;
		timePlaying = 0;
		delayTime = 0;
	}
	
	public void playReversed() {
		reversed = true;
		playing = true;
		if (firstTime) timePlaying = seconds;
		firstTime = false;
		delayTime = 0;
	}
	
	public void playReversedRestart() {
		reversed = true;
		playing = true;
		timePlaying = seconds;
		delayTime = 0;
	}
	
	private void finish() {
		process(1);
		if (autoReverse) {
			reversed = true;
		} else if (autoLoop) {
			timePlaying = 0;
		} else {
			playing = false;
			timePlaying = seconds;
		}
		this.onFinished.handle();
	}
	
	private void finishReverse() {
		process(0);
		if (autoReverse) {
			reversed = false;
		} else if (autoLoop) {
			timePlaying = seconds;			
		} else {
			playing = false;
			timePlaying = 0;
		}
		this.onFinishedReverse.handle();
	}
	
	
	public Animation withDelay(float d) {
		this.delay = d;
		return this;
	}
	
	public Animation withAutoReverse(boolean ar) {
		this.autoReverse = ar;
		return this;
	}
	

	/**
	 * @return the seconds
	 */
	public float getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(float seconds) {
		this.seconds = seconds;
	}

	/**
	 * @return the onFinished
	 */
	public EventHandler getOnFinished() {
		return onFinished;
	}

	/**
	 * @param onFinished the onFinished to set
	 */
	public void setOnFinished(EventHandler onFinished) {
		this.onFinished = onFinished;
	}

	/**
	 * @return the onFinishedReverse
	 */
	public EventHandler getOnFinishedReverse() {
		return onFinishedReverse;
	}

	/**
	 * @param onFinishedReverse the onFinishedReverse to set
	 */
	public void setOnFinishedReverse(EventHandler onFinishedReverse) {
		this.onFinishedReverse = onFinishedReverse;
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

	/**
	 * @return the autoLoop
	 */
	public boolean isAutoLoop() {
		return autoLoop;
	}

	/**
	 * @param autoLoop the autoLoop to set
	 */
	public void setAutoLoop(boolean autoLoop) {
		this.autoLoop = autoLoop;
	}

	/**
	 * @return the autoReverse
	 */
	public boolean isAutoReverse() {
		return autoReverse;
	}

	/**
	 * @param autoReverse the autoReverse to set
	 */
	public void setAutoReverse(boolean autoReverse) {
		this.autoReverse = autoReverse;
	}

	/**
	 * @return the delay
	 */
	public float getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}

	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * @return the reversed
	 */
	public boolean isReversed() {
		return reversed;
	}

	/**
	 * @param reversed the reversed to set
	 */
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	
	
	
}
