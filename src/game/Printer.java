package game;


import models.components.updatable.BerylUC;
import models.data.Entity;
import tools.io.BerylKeyboard;

public class Printer extends BerylUC {

	public Printer(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() { }
	
	@Override
	public void onUpdate() {
		String str = BerylKeyboard.getStream().stringify();
		if (str.length() > 0) System.out.print(str);
	}

	@Override
	public void onLateUpdate() { }

}
