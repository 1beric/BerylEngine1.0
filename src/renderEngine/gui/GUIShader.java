package renderEngine.gui;

import renderEngine.ShaderProgram;
import settings.Constants;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class GUIShader extends ShaderProgram {

	private final static String VERTEX_FILE = (String)Constants.get("GUI_VERTEX");
	private final static String FRAGMENT_FILE = (String)Constants.get("GUI_FRAGMENT");

	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("transformationMatrix");
		addUniform("color");
		addUniform("transparency");
		addUniform("borderWidth");
		addUniform("borderRadius");
		addUniform("borderColor");
		addUniform("guiCenter");
		addUniform("guiScale");
		addUniform("guiFlipped");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadTransformation(BerylMatrix matrix){
		super.loadMatrix(getUniform("transformationMatrix"), matrix);
	}
	
	public void loadPosAndScale(BerylVector pos, BerylVector scale) {
		super.loadVector2(getUniform("guiCenter"), pos);
		super.loadVector2(getUniform("guiScale"), scale);
	}
	
	public void loadColor(BerylVector color){
		super.loadVector3(getUniform("color"), color);
	}
	
	public void loadTransparency(float t){
		super.loadFloat(getUniform("transparency"), t);
	}
	
	public void loadBorder(BerylVector width, BerylVector color, BerylVector radius){
		super.loadVector2(getUniform("borderWidth"), width);
		super.loadVector3(getUniform("borderColor"), color);
		super.loadVector2(getUniform("borderRadius"), radius);
	}
	
	public void loadFlipped(boolean flipped) {
		super.loadBoolean(getUniform("guiFlipped"), flipped);
	}

}
