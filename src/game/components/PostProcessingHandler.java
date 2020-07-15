package game.components;

import java.util.Random;

import guiSystem.animations.Animation;
import guiSystem.animations.CustomAnimation;
import models.components.updatable.BerylUC;
import models.data.Entity;
import renderEngine.postProcessing.brightness.BrightnessEffect;

public class PostProcessingHandler extends BerylUC {

	public PostProcessingHandler(Entity entity) {
		super(entity);
	}

	@Override
	public void onInit() {
		BrightnessEffect brightnessEffect = (BrightnessEffect) getComponent(BrightnessEffect.class);
		Animation brightnessShift = new CustomAnimation(.01f, f->{});
		brightnessShift.setAutoLoop(true);
		Random r = new Random();
		brightnessShift.setOnFinished(()->{
			brightnessEffect.setBrightness(brightnessEffect.getBrightness() + (r.nextFloat() - 0.5f)/50f);
		});
		brightnessShift.play();
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void onLateUpdate() {
		
	}

}
