package editor;

import models.components.renderable.Transform2D;
import tools.math.BerylVector;

public class GameView extends Window {

	public GameView(BerylVector centerPos, BerylVector scale) {
		super(new Transform2D(centerPos, scale, null));
	}
	
	public GameView() {
		super(new Transform2D(BerylVector.zero(), BerylVector.one(2), null));
	}


	
	

}
