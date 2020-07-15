package renderEngine.gameView;

import renderEngine.ShaderProgram;

public class GameViewShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/gameView/gameViewVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/gameView/gameViewFragment.glsl";
	

	public GameViewShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {

	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
}
