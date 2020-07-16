package renderEngine.shaders;

import tools.math.BerylMatrix;

public class WindowShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/renderEngine/shaders/windowVertex.glsl";
	private final static String FRAGMENT_FILE = "src/renderEngine/shaders/simpleImageFragment.glsl";
	

	public WindowShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void addAllUniforms() {
		addUniform("transformationMatrix");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadTransformationMatrix(BerylMatrix m) {
		loadMatrix(getUniform("transformationMatrix"), m);
	}
	
}
