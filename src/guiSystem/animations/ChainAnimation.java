package guiSystem.animations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChainAnimation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544238314918886045L;
	private List<Animation> animations;
	private boolean playing;
	
	public ChainAnimation(Animation... animations) {
		this.animations = new ArrayList<Animation>();
		for (int i=0;i<animations.length;i++) {
			this.animations.add(animations[i]);
			int[] pos = new int[] {i};
			animations[i].setOnFinished(()->animations[(pos[0]+1) % animations.length].playRestart());
		}
	}
	
	public void add(Animation animation) {
		if (playing) return;
		this.animations.get(this.animations.size()-1).setOnFinished(()->animation.playRestart());
		animation.setOnFinished(()->this.animations.get(0).playRestart());
	}
	
	public void play() {
		this.animations.get(0).playRestart();
		playing = true;
	}

}
