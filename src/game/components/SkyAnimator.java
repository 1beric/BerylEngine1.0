package game.components;

import guiSystem.animations.Animation;
import guiSystem.animations.ChainAnimation;
import guiSystem.animations.CustomAnimation;
import models.Scene;
import models.components.BerylComponent;
import models.components.renderable.SkyboxRC;
import models.components.updatable.BerylUC;
import models.data.Entity;
import settings.Constants;
import tools.BerylTime;
import tools.Interpolators;
import tools.Interpolators.Interpolator;
import tools.math.BerylVector;

public class SkyAnimator extends BerylUC {
	

	private static final BerylVector DAY_COLOR = (BerylVector)Constants.get("DAY_COLOR");
	private static final BerylVector NIGHT_COLOR = (BerylVector)Constants.get("NIGHT_COLOR");
	private static final BerylVector DAY_SUN_COLOR = new BerylVector(1);
	private static final BerylVector NIGHT_SUN_COLOR = new BerylVector(.5f);
	private static final float NIGHT_LENGTH = (float)Constants.get("NIGHT_LENGTH");
	private static final float NTD_LENGTH = (float)Constants.get("NTD_LENGTH");
	private static final float DAY_LENGTH = (float)Constants.get("DAY_LENGTH");
	private static final float DTN_LENGTH = (float)Constants.get("DTN_LENGTH");
	private static String[] DAY_TEXTURES = (String[])Constants.get("SKYBOX_DAY_TEXTURES");
	private static String[] NIGHT_TEXTURES = (String[])Constants.get("SKYBOX_NIGHT_TEXTURES");
	private static final float SKYBOX_LOWER_LIMIT = (float)Constants.get("SKYBOX_LOWER_LIMIT");
	private static final float SKYBOX_UPPER_LIMIT = (float)Constants.get("SKYBOX_UPPER_LIMIT");
	private static final float ROTATION_SPEED = (float)Constants.get("SKYBOX_ROTATION_SPEED");
		
	public SkyAnimator(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() {
		Scene scene = getEntity().getScene();
		new SkyboxRC(DAY_COLOR, DAY_TEXTURES, NIGHT_TEXTURES, SKYBOX_LOWER_LIMIT, SKYBOX_UPPER_LIMIT, getEntity());
		
		Interpolator inter = Interpolators.LINEAR;
		new ChainAnimation(
				new CustomAnimation(DTN_LENGTH, f->{
					scene.getSky().getColor().x = inter.handle(DAY_COLOR.x, NIGHT_COLOR.x, f);
					scene.getSky().getColor().y = inter.handle(DAY_COLOR.y, NIGHT_COLOR.y, f);
					scene.getSky().getColor().z = inter.handle(DAY_COLOR.z, NIGHT_COLOR.z, f);
					scene.getSky().setFactor(f);
					scene.getLight().getColor().x = inter.handle(DAY_SUN_COLOR.x, NIGHT_SUN_COLOR.x, f);
					scene.getLight().getColor().y = inter.handle(DAY_SUN_COLOR.y, NIGHT_SUN_COLOR.y, f);
					scene.getLight().getColor().z = inter.handle(DAY_SUN_COLOR.z, NIGHT_SUN_COLOR.z, f);
				}).withDelay(DAY_LENGTH),
				new CustomAnimation(NTD_LENGTH, f->{
					scene.getSky().getColor().x = inter.handle(NIGHT_COLOR.x, DAY_COLOR.x, f);
					scene.getSky().getColor().y = inter.handle(NIGHT_COLOR.y, DAY_COLOR.y, f);
					scene.getSky().getColor().z = inter.handle(NIGHT_COLOR.z, DAY_COLOR.z, f);
					scene.getSky().setFactor(1-f);
					scene.getLight().getColor().x = inter.handle(NIGHT_SUN_COLOR.x, DAY_SUN_COLOR.x, f);
					scene.getLight().getColor().y = inter.handle(NIGHT_SUN_COLOR.y, DAY_SUN_COLOR.y, f);
					scene.getLight().getColor().z = inter.handle(NIGHT_SUN_COLOR.z, DAY_SUN_COLOR.z, f);
				}).withDelay(NIGHT_LENGTH)
		).play();
		Animation a = new CustomAnimation(1,f->scene.getSky().setRotation(scene.getSky().getRotation() + ROTATION_SPEED * BerylTime.getDelta()));
		a.setAutoLoop(true);
		a.play();
	}

	@Override
	public void onUpdate() { }
	
	@Override
	public void onLateUpdate() { }

}
