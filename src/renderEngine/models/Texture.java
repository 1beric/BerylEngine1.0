package renderEngine.models;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

public class Texture {

	private static final Texture WHITE = new Texture("white");
	public static Texture white() {
		return WHITE;
	}
	
	
	
	private String file;
	private int textureID;
	
	public Texture(String file) {
		this.setFile(file);
		
		// GPU bound
		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		// repeat texture
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		// pixelate on stretch/shrink
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = STBImage.stbi_load("res/" + file + ".png", width, height, channels, 0);
		
		if (image != null) {
			image.flip();
			if (channels.get(0) == 4) {
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 
					0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
			} else if (channels.get(0) == 3) {
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width.get(0), height.get(0), 
						0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, image);
			} else {
				System.out.println("error in texture, unknown number of channels");
			}
		} else System.out.println("error in image loading: " + file);
		
		STBImage.stbi_image_free(image);
	}
	
	public Texture(String file, int texID) {
		setFile(file);
		this.textureID = texID;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @return the textureID
	 */
	public int getTextureID() {
		return textureID;
	}

	
	
}