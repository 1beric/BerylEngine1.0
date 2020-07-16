package game.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import guiSystem.animations.Animation;
import guiSystem.animations.ChainAnimation;
import guiSystem.animations.ColorAnimation;
import guiSystem.animations.PositionAnimation;
import guiSystem.animations.PositionOffsetAnimation;
import guiSystem.animations.ScaleAnimation;
import guiSystem.animations.TransparencyAnimation;
import guiSystem.elements.Button;
import guiSystem.elements.FullScreenRect;
import guiSystem.elements.Mesh2RC;
import guiSystem.elements.ParentPosController;
import guiSystem.elements.RadioContainer;
import guiSystem.elements.Rect;
import guiSystem.elements.Slider;
import guiSystem.elements.TextGUI;
import guiSystem.elements.ToggleButton;
import meshCreation.Loader;
import models.components.updatable.BerylUC;
import models.data.Entity;
import guiSystem.NavigationSet;
import guiSystem.NavigationSystem;
import guiSystem.RectStyle;
import settings.Constants;
import tools.BerylDisplay;
import tools.editorAnnotations.GetMethod;
import tools.editorAnnotations.SetMethod;
import tools.editorAnnotations.ShowInEditor;
import tools.input.BerylKeyboard;
import tools.interfaces.FloatPasser;
import tools.interfaces.MouseEventHandler;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class GUIHandler extends BerylUC {

	public GUIHandler(Entity entity) {
		super(entity);
	}

	private static final int SCREEN_WIDTH = (int)Constants.get("SCREEN_WIDTH");
	private static final int SCREEN_HEIGHT = (int)Constants.get("SCREEN_HEIGHT");
	public static final float NAVIGATION_TIME = (float)Constants.get("NAVIGATION_TIME");

	private NavigationSystem navSys;
	
	@ShowInEditor(value = "sideSliders")
	private Slider[] sideSliders;
	
	@Override
	public void onInit() {
		initNavSets();
		createHotBar();
		createSideBar();
		createSettings();
		createMainMenu();
		navSys.getNav("main").setVisible(true);
		navSys.getNav("settings").setVisible(false);
		navSys.getNav("scene").setVisible(false);
	}
	
	@Override
	public void onUpdate() {
		
		// change guis
		if (BerylKeyboard.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			if (isSetActive("settings") || isSetActive("scene")) changeToSet("main");
			else if (isSetActive("main")) changeToSet("scene");
		}
		updateGUIs();
	}
	
	@Override
	public void onLateUpdate() { }
	
	public List<Mesh2RC> getRoots() {
		List<Mesh2RC> roots = new ArrayList<>();
		for (NavigationSet set : navSys.getSets()) {
			roots.addAll(set.getGUIs());
		}
		return roots;
	}
	
	private void updateGUIs() {
		for (Mesh2RC gui : getRoots()) {
			if (gui.isActive() && gui.getEntity().isActive()) gui.update(); //mouseHover();
		}
	}

	private void initNavSets() {
		navSys = new NavigationSystem();
		navSys.addNav(new NavigationSet("main"));
		navSys.addNav(new NavigationSet("settings"));
		navSys.addNav(new NavigationSet("scene"));
		navSys.addStateTransition("main", "settings");
		navSys.addStateTransition("main", "scene");
		navSys.addStateTransition("settings", "main");
		navSys.addStateTransition("scene", "main");
	}
	
	private void createMainMenu() {
		Rect background = new FullScreenRect(new Entity("main",getEntity().getScene())); // new BerylVector(0,0), new BerylVector(1,1), "percent", "percent"
		background.setColor(BerylMath.hexToRGB("2B2C28"));
		background.setName("main");
		navSys.getNav("main").addGUI(background);
		TransparencyAnimation transFadeAnim = new TransparencyAnimation(NAVIGATION_TIME, 1, 0);
		transFadeAnim.addMesh(background);
		
		Rect title = new Rect(new BerylVector(-.25f,0.1f), new BerylVector(900,300), "percent", "pixel", background, null);
		title.setColor(BerylMath.hexToRGB("4C2B36"));
		title.setOriginPoint(RectStyle.TC);
		title.setFromParentPoint(RectStyle.TC);
		title.setBorderColor(BerylVector.one(3));
		title.setBorderWidth(2);
		title.setBorderRadius(3f);
		float cycleTime = 3;
		new ChainAnimation(
			new ColorAnimation(cycleTime, BerylMath.hexToRGB("4C2B36"), BerylMath.hexToRGB("2B364C"),title.getMat()),
			new ColorAnimation(cycleTime, BerylMath.hexToRGB("2B364C"), BerylMath.hexToRGB("E59500"),title.getMat()),
			new ColorAnimation(cycleTime, BerylMath.hexToRGB("E59500"), BerylMath.hexToRGB("4C2B36"),title.getMat())
		).play();
		transFadeAnim.addMesh(title);
		navSys.addTransition("main", "settings", new PositionOffsetAnimation(
				NAVIGATION_TIME, 
				new BerylVector(0,-1f), 
				title.getTransform()
		));
		navSys.addTransition("main", "scene", new PositionOffsetAnimation(
				NAVIGATION_TIME, 
				new BerylVector(0,-1f), 
				title.getTransform()
		));
		
		TextGUI titleText = new TextGUI("FACTORY", 11, BerylVector.zero(), "percent", title, null);
		titleText.setColor(BerylVector.one(3));
		transFadeAnim.addMesh(titleText);

		Rect icon = new Rect(new BerylVector(-.05f,0), new BerylVector(1200f, 1200f), "percent", "pixel", background, null);
		icon.setOriginPoint(RectStyle.CR);
		icon.setFromParentPoint(RectStyle.CR);
		icon.getMat().setColorTexture(Loader.loadTexture("crystalFull"));
		transFadeAnim.addMesh(icon);
		new ParentPosController(1f, icon, null);
		
		navSys.addTransition("main", "settings",new PositionOffsetAnimation(
				NAVIGATION_TIME, 
				new BerylVector(1,0f), 
				icon.getTransform()
		));
		navSys.addTransition("main", "scene",new PositionOffsetAnimation(
				NAVIGATION_TIME, 
				new BerylVector(1,0f), 
				icon.getTransform()
		));
		
		MouseEventHandler[] menuHdls = new MouseEventHandler[] {
				()->changeToSet("scene"),			// new game button
				()->changeToSet("scene"),			// load game button
				()->changeToSet("settings"),		// settings button
				()->BerylDisplay.requestClose()		// exit
		};
		String[] menuStrs = new String[] {
				"New Game",
				"Load Game",
				"Settings",
				"Exit"
		};
		
		for (int i=0;i<4;i++) {
			Button btn = new Button(new BerylVector(-.25f, (i-.5f) * .15f), new BerylVector(500,150), "percent", "pixel", background, null);
			btn.setColor(BerylMath.hexToRGB("2B4C36"));
			btn.setBorderRadius(3f);
			btn.setOriginPoint(RectStyle.CC);
			btn.setFromParentPoint(RectStyle.CC);
			Animation hoverColorAnim = new ColorAnimation(
					.1f, 
					btn.getColor(), 
					btn.getColor().mult(1.3f),
					btn.getMat());
			Animation hoverScaleAnim = new ScaleAnimation(
					.1f,
					btn.getScale(),
					btn.getScale().mult(new BerylVector(1.2f,1)),
					btn.getTransform());
			btn.setOnMouseEntered(()->{
				hoverColorAnim.play();
				hoverScaleAnim.play();
			});
			btn.setOnMouseExited(()->{
				hoverColorAnim.playReversed();
				hoverScaleAnim.playReversed();
			});
			btn.setOnMouseClicked(menuHdls[i]);
			transFadeAnim.addMesh(btn);
			navSys.addTransition("main", "settings", new PositionAnimation(
					NAVIGATION_TIME, 
					btn.getPos(),
					btn.getPos().add(new BerylVector(0,1)),
					btn.getTransform()));
			navSys.addTransition("main", "scene", new PositionAnimation(
					NAVIGATION_TIME, 
					btn.getPos(),
					btn.getPos().add(new BerylVector(0,1)),
					btn.getTransform()));
			new ParentPosController(btn, null);
			TextGUI txt = new TextGUI(menuStrs[i], 2, new BerylVector(10,0), "pixel", btn, null);
			txt.setOriginPoint(RectStyle.CC);
			txt.setScaleOnHeight(false);
			txt.setColor(new BerylVector(1,1,1));
			transFadeAnim.addMesh(txt);
		}
		navSys.addTransition("main", "settings", transFadeAnim);
		navSys.addTransition("main", "scene", transFadeAnim);
		
	}
	
	private void createSettings() {
		Rect background = new FullScreenRect(new Entity("settings", getEntity().getScene()));
		background.setColor(BerylMath.hexToRGB("2B2C28"));
		background.setName("settings");
		navSys.getNav("settings").addGUI(background);
		
		TransparencyAnimation transFadeAnim = new TransparencyAnimation(NAVIGATION_TIME, 1, 0);
		
		Rect title = new Rect(new BerylVector(0,.2f), new BerylVector(500,200), "percent", "pixel", background, null);
		title.setColor(BerylMath.hexToRGB("4C2B36"));
		title.setOriginPoint(RectStyle.TC);
		title.setFromParentPoint(RectStyle.TC);
		title.setBorderColor(BerylVector.one(3));
		title.setBorderWidth(2);
		title.setBorderRadius(3f);
		transFadeAnim.addMesh(title);
		float cycleTime = 3;
		new ChainAnimation(
				new ColorAnimation(cycleTime, BerylMath.hexToRGB("4C2B36"), BerylMath.hexToRGB("2B364C"),title.getMat()),
				new ColorAnimation(cycleTime, BerylMath.hexToRGB("2B364C"), BerylMath.hexToRGB("E59500"),title.getMat()),
				new ColorAnimation(cycleTime, BerylMath.hexToRGB("E59500"), BerylMath.hexToRGB("4C2B36"),title.getMat())
		).play();

		TextGUI titleText = new TextGUI("SETTINGS", 4, BerylVector.zero(), "percent", title, null);
		titleText.setColor(BerylVector.one(3));
		titleText.setScaleOnHeight(false); 
		transFadeAnim.addMesh(titleText);
		
		Button mainMenuBtn = new Button(new BerylVector(.05f,.05f), new BerylVector(300,100), "percent", "pixel", background, null);
		transFadeAnim.addMesh(mainMenuBtn);
		mainMenuBtn.setColor(BerylMath.hexToRGB("2B4C36"));
		mainMenuBtn.setOriginPoint(RectStyle.TL);
		mainMenuBtn.setFromParentPoint(RectStyle.TL);
		mainMenuBtn.setBorderRadius(3f);
		Animation hoverColorAnim = new ColorAnimation(
				.1f, 
				mainMenuBtn.getColor(), 
				mainMenuBtn.getColor().mult(1.3f),
				mainMenuBtn.getMat());
		Animation hoverScaleAnim = new ScaleAnimation(
				.1f,
				mainMenuBtn.getScale(),
				mainMenuBtn.getScale().mult(new BerylVector(1.2f,1)),
				mainMenuBtn.getTransform());
		mainMenuBtn.setOnMouseEntered(()->{
			hoverColorAnim.play();
			hoverScaleAnim.play();
		});
		mainMenuBtn.setOnMouseExited(()->{
			hoverColorAnim.playReversed();
			hoverScaleAnim.playReversed();
		});
		mainMenuBtn.setOnMouseClicked(()->changeToSet("main"));
		TextGUI mainMenuText = new TextGUI("Main Menu", 10, new BerylVector(10,0), "pixel", mainMenuBtn, null);
		mainMenuText.setOriginPoint(RectStyle.CL);
		mainMenuText.setColor(new BerylVector(1,1,1));
		transFadeAnim.addMesh(mainMenuText);
		
		
		String[] texts = new String[] {
				"border radius",
				"width",
				"height"
		};
		
		BerylVector[] bounds = new BerylVector[] {
				new BerylVector(0,10),
				new BerylVector(100,900),
				new BerylVector(100,300)
		};
		
		FloatPasser[] setOnValues = new FloatPasser[] {
				f -> title.setBorderRadius(f),
				f -> title.setScale(new BerylVector(f,title.getScale().y)),
				f -> title.setScale(new BerylVector(title.getScale().x,f))
		};
		
		int[] roundTxts = new int[] { 2, 0, 0 };
		
		for (int i=0; i<3; i++) {
			Slider slider = new Slider(new BerylVector(0,(i+1)*.1f), 500, "percent", "pixel", background, null);
			transFadeAnim.addMesh(slider);
			transFadeAnim.addMesh(slider.getChildren().get(0));
			slider.setOriginPoint(RectStyle.CC);
			slider.setFromParentPoint(RectStyle.CC);
			slider.setColor(BerylMath.hexToRGB("FFFFFF"));
			slider.setCursorColor(BerylMath.hexToRGB("E59500"));
			slider.setUpdateOnHold(true);
			slider.setText(texts[i]);
			slider.setBounds(bounds[i]);
			slider.setOnSetValue(setOnValues[i]);
			slider.roundValueText(roundTxts[i]);
			navSys.addTransition("settings", "main", new PositionAnimation(
					NAVIGATION_TIME, 
					slider.getPos(), 
					slider.getPos().add(new BerylVector(0,1)), 
					slider.getTransform()));
		}
		
		navSys.addTransition("settings", "main", transFadeAnim);
	}

	private void createSideBar() {
		Rect background = new Rect(BerylVector.zero(), new BerylVector(SCREEN_WIDTH*.2f,SCREEN_HEIGHT*.6f), 
				"pixel", "pixel", new FullScreenRect(null), new Entity("scene",getEntity().getScene()));
		navSys.getNav("scene").addGUI(background);
		background.setName("sceneSideBar");
		background.setColor(BerylMath.hexToRGB("2B2C28"));
		background.setTransparency(0.7f);
		background.setOriginPoint(RectStyle.CR);
		background.setFromParentPoint(RectStyle.CR);
		Animation bgTransAnim = new TransparencyAnimation(.1f, .7f, 1, background);
		background.setOnMouseEntered(()->bgTransAnim.play());
		background.setOnMouseExited(()->bgTransAnim.playReversed());
		addTransition("scene", "main", new PositionAnimation(
				NAVIGATION_TIME, 
				background.getPos(), 
				background.getPos().add(new BerylVector(SCREEN_WIDTH,0)), 
				background.getTransform()));

		sideSliders = new Slider[10];
		for (int i=0; i<10; i++) {
			float yPos = .05f + .1f * (i-5);
			sideSliders[i] = new Slider(new BerylVector(0f,yPos), background.getScale().x * 0.5f, "percent", "pixel", background, null);
			sideSliders[i].setOriginPoint(RectStyle.CC);
			sideSliders[i].setFromParentPoint(RectStyle.CC);
			sideSliders[i].setCursorColor(BerylMath.hexToRGB("E59500"));
			sideSliders[i].setUpdateOnHold(true);
			sideSliders[i].showCursorValue(false);
			sideSliders[i].setPassThrough(true);
		}
	}

	private void createHotBar() {
		Rect background = new Rect(BerylVector.zero(), new BerylVector(SCREEN_WIDTH*.6f,SCREEN_HEIGHT*.1f),
				"pixel", "pixel", new FullScreenRect(null), new Entity("scene",getEntity().getScene()));
		navSys.getNav("scene").addGUI(background);
		background.setName("sceneHotBar");
		background.setColor(BerylMath.hexToRGB("2B2C28"));
		background.setTransparency(0.7f);
		background.setOriginPoint(RectStyle.BC);
		background.setFromParentPoint(RectStyle.BC);
		Animation bgTransAnim = new TransparencyAnimation(.1f, .7f, 1, background);
		background.setOnMouseEntered(()->bgTransAnim.play());
		background.setOnMouseExited(()->bgTransAnim.playReversed());
		addTransition("scene", "main", new PositionAnimation(
				NAVIGATION_TIME, 
				background.getPos(), 
				background.getPos().add(new BerylVector(0,-SCREEN_HEIGHT)), 
				background.getTransform()));
		
		ToggleButton[] items = new ToggleButton[10];
		RadioContainer radio = new RadioContainer();
		for (int i=0; i<10; i++) {
			items[i] = new ToggleButton(new BerylVector(.05f + .1f * (i-items.length/2), 0f), new BerylVector(.075f,.8f), "percent", "percent", background, null);
			items[i].setColor(BerylMath.hexToRGB("7E6551"));
			items[i].setOriginPoint(RectStyle.CC);
			items[i].setFromParentPoint(RectStyle.CC);
			radio.addToggle(items[i]);
			Animation itemScaleAnim = new ScaleAnimation(.1f,items[i].getScale(),items[i].getScale().mult(1.1f),items[i].getTransform());
			Animation itemColorAnim = new ColorAnimation(.1f,items[i].getColor(),items[i].getColor().mult(1.8f),items[i].getMat());
			items[i].setOnMouseEntered(()->{
				itemScaleAnim.play();
				bgTransAnim.play();
			});
			items[i].setOnMouseExited(()->itemScaleAnim.playReversed());
			items[i].setOnToggledOn(()->itemColorAnim.play());
			items[i].setOnToggledOff(()->itemColorAnim.playReversed());
			int[] pos = new int[] {i};
			items[i].setOnMouseClicked(()->radio.handle(pos[0]));
		}
	}

	public boolean activesContains(BerylVector point) {
		for (Mesh2RC gui : getRoots()) if (gui.isActive() && gui.contains(point)) return true;
		return false;
	}
	
	public void changeToSet(String name) { 
		navSys.switchTo(name);
	}

	public boolean isSetActive(String name) {
		return navSys.isActive(name);
	}
	
	public void addGUI(String set, Mesh2RC gui) {
		navSys.getNav(set).addGUI(gui);
	}
	
	public void addTransition(String from, String to, Animation anim) {
		navSys.addTransition(from, to, anim);
	}

	/**
	 * @return the sideSliders
	 */
	@GetMethod(value = "sideSliders")
	public Slider getSideSlider(int i) {
		return sideSliders[i];
	}

	/**
	 * @param sideSliders the sideSliders to set
	 */
	@SetMethod(value = "sideSliders")
	public void setSideSlider(Slider slider, int i) {
		this.sideSliders[i] = slider;
	}
	
}
