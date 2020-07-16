package game;

import models.Scene;
import models.components.BerylComponent;
import models.components.updatable.BerylUC;
import models.data.Entity;
import tools.io.BerylKeyboard;

public class Recompiler extends BerylUC {

	public Recompiler(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onUpdate() {
//		if (BerylKeyboard.isKeyHeld(Keyboard.KEY_LCONTROL) &&
//				BerylKeyboard.isKeyDown(Keyboard.KEY_C)) {
////			getEntity().getScene().requestRecompile();
//		}		
	}

	@Override
	public void onLateUpdate() {
	}

}
