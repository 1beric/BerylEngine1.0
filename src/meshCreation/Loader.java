package meshCreation;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import models.data.ModelData;
import renderEngine.models.RawModel;
import renderEngine.models.Texture;
import renderEngine.models.TextureData;
import tools.BerylFormattingTools;

public class Loader {
	
	private static Set<Integer> vaos = new HashSet<>();
	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();

	public static RawModel loadToVAO(ModelData data) {
		int vaoID;
		switch (data.getType()) {
		case POS_TEXS_NORMS_INDS:
			// currently used with the entity shader
			vaoID = createVAO();
			bindIndicesBuffer(data.getIndices());
			storeDataInAttributeList(0, 3, data.getVertices());
			storeDataInAttributeList(1, 2, data.getTextureCoords());
			storeDataInAttributeList(2, 3, data.getNormals());
			unbindVAO();
			return new RawModel(vaoID, data.getIndices().length, data);
		case POS_TEXS:
			// unused, need to update gui system
			vaoID = createVAO();
			storeDataInAttributeList(0, 2, data.getVertices());
			storeDataInAttributeList(1, 2, data.getTextureCoords());
			unbindVAO();
			return new RawModel(vaoID, data.getVertices().length/2, data);
		case POS_TEXS_NORMS_TANGS_INDS:
			// used with a normal map
			vaoID = createVAO();
			bindIndicesBuffer(data.getIndices());
			storeDataInAttributeList(0, 3, data.getVertices());
			storeDataInAttributeList(1, 2, data.getTextureCoords());
			storeDataInAttributeList(2, 3, data.getNormals());
			storeDataInAttributeList(3, 3, data.getTangents());
			unbindVAO();
			return new RawModel(vaoID, data.getIndices().length, data);
		case POS_DIMS:
			// currently used in the gui system, but will be outdated when textures are implemented.
			vaoID = createVAO();
			storeDataInAttributeList(0, data.getDimensions(), data.getVertices());
			unbindVAO();
			return new RawModel(vaoID, data.getVertices().length/data.getDimensions(), data);
		case NOT_SET:
			return null;
		default:
			return null;
		}
	}
	
	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private static void storeDataInAttributeList(int attrNum, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrNum, coordinateSize, GL11.GL_FLOAT, false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;	
	}
	

	
	public static Texture loadTexture(String file) {
		Texture texture = null;
		texture = new Texture(file);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		textures.add(texture.getTextureID());
		return texture;
	}
	
	public static Texture loadCubeMap(String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
		for (int i=0; i<textureFiles.length; i++) {
			TextureData data = decodeTextureFile("res/" + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, 
					GL11.GL_RGBA, data.getWidth(), data.getHeight(), 
					0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		textures.add(texID);
		return new Texture(BerylFormattingTools.flatten(textureFiles),texID);
	}
	
	
	public static TextureData decodeTextureFile(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(-1);
		}
		return new TextureData(width, height, buffer);
	}
	
	public static void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	public static void unloadVAO(int vao) {
		GL30.glDeleteVertexArrays(vao);
		vaos.remove((Integer)vao);
	}
	
}
