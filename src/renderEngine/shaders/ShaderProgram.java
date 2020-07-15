package renderEngine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import tools.math.BerylMatrix;
import tools.math.BerylVector;

public abstract class ShaderProgram {

	private static List<ShaderProgram> shaders = new ArrayList<>();
	public static void recompileShaders() {
		shaders.forEach(shader->shader.recompile());
	}
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	private String vertexFile;
	private String fragmentFile;
	private boolean recompiled;
	
	private Map<String, Integer> uniforms;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
		uniforms = new HashMap<>();
		shaders.add(this);
		init();
		recompiled = false;
	}
	
	private void init() {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		addAllUniforms();
		setRecompiled(true);
	}
	
	public abstract void bindAttributes();
	public abstract void addAllUniforms();
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
		
	public void cleanUp() {
		unbind();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected void bindAttribute(int attr, String variable) {
		GL20.glBindAttribLocation(programID, attr, variable);
	}
	
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}
	
	protected void loadVector4(int location, BerylVector vec) {
		GL20.glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
	}
	
	protected void loadVector3(int location, BerylVector vec) {
		GL20.glUniform3f(location, vec.x, vec.y, vec.z);
	}
	
	protected void loadVector2(int location, BerylVector vec) {
		GL20.glUniform2f(location, vec.x, vec.y);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1 : 0);
	}
	
	protected void loadMatrix(int location, BerylMatrix matrix) {
		matrix.store(matrixBuffer);
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	protected void recompile() {
		cleanUp();
		init();
	}

	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ( (line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException ioe) {
			System.err.println("Could not read file: " + file);
			ioe.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader from " + file);
			System.exit(-1);
		}
		return shaderID;
	}

	/**
	 * @return the recompiled
	 */
	public boolean isRecompiled() {
		return recompiled;
	}

	/**
	 * @param recompiled the recompiled to set
	 */
	public void setRecompiled(boolean recompiled) {
		this.recompiled = recompiled;
	}
	
	protected void addUniform(String uniform) {
		uniforms.put(uniform,getUniformLocation(uniform));
	}
	
	protected int getUniform(String uniform) {
		return uniforms.get(uniform);
	}
	
	public Set<String> getUniforms() {
		return uniforms.keySet();
	}
	
}
