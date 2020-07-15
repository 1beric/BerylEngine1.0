package renderEngine.models;

import java.nio.ByteBuffer;

/**
 * class only used to pass data for loading a cube map currently
 * @author Brandon Erickson
 *
 */
public class TextureData {

	private int width;
	private int height;
	private ByteBuffer buffer;
	
	public TextureData(int width, int height, ByteBuffer buffer) {
		super();
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return the buffer
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}
	
	
}