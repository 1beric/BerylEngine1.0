package renderEngine.fontRendering;

import renderEngine.ShaderProgram;
import settings.Constants;
import tools.math.BerylVector;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = (String)Constants.get("FONT_VERTEX");
	private static final String FRAGMENT_FILE = (String)Constants.get("FONT_FRAGMENT");
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("color");
		addUniform("translation");
		addUniform("fontAtlas");
		addUniform("transparency");
	}
	
	public void loadTranslation(BerylVector vec) {
		super.loadVector2(getUniform("translation"), vec);
	}

	public void loadColor(BerylVector color) {
		super.loadVector3(getUniform("color"), color);
	}

	public void loadTransparency(float t) {
		super.loadFloat(getUniform("transparency"), t);
	}
	
	public void bindTextureAtlas() {
		super.loadInt(getUniform("fontAtlas"), 0);
	}
	
	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}


}
