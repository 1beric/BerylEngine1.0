package game.components;

import guiSystem.elements.Mesh2RC;
import guiSystem.elements.Rect;
import guiSystem.elements.TextGUI;
import models.components.updatable.BerylUC;
import models.data.Entity;
import guiSystem.RectStyle;
import tools.BerylDisplay;
import tools.BerylTime;
import tools.math.BerylVector;

public class FPSUpdater extends BerylUC {


	private static final int LENGTH = 30;
	
	private Mesh2RC background;
	private TextGUI text;
	private int framesPassed;
	
	public FPSUpdater(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() {
		background = new Rect(BerylVector.zero(), new BerylVector(BerylDisplay.WIDTH*0.1f,BerylDisplay.HEIGHT*0.05f), "percent", "pixel", getEntity());
		background.setTransparency(0.5f);
		background.setColor(new BerylVector(0));
		background.setOriginPoint(RectStyle.TL);
		
		text = new TextGUI("FPS: ", 12, new BerylVector(0.1f, 0), "percent", background, null);
		text.setOriginPoint(RectStyle.CL);
		text.setColor(new BerylVector(1));
		framesPassed = 0;
	}
	
	@Override
	public void onUpdate() {
		if (framesPassed >= LENGTH) {
			String fps = String.valueOf((int)(1/BerylTime.getDelta()));
			text.setText("FPS: " + fps.substring(0, Math.min(fps.length(), 4)));
			framesPassed = 0;
		}
		framesPassed++;
		
		background.setActive(true);
		getEntity().getScene().getMesh2RCs().forEach(mesh->{
			if (mesh.getName().equals("main") && mesh.isActive() || 
					mesh.getName().equals("settings") && mesh.isActive()) {
				background.setActive(false);
			}
		});
	}
	
	@Override
	public void onLateUpdate() { }

}
