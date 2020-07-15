package renderEngine.postProcessing;

import org.lwjgl.opengl.GL11;

import renderEngine.models.FrameBuffer;
import renderEngine.models.Texture;
import renderEngine.models.FrameBuffer.DepthBuffer;

public class ImageRenderer {

	private FrameBuffer fbo;

	public ImageRenderer(boolean toFBO, float scale) {
		this.fbo = toFBO ? new FrameBuffer(scale, DepthBuffer.DEPTH_RENDER_BUFFER) : null;
	}
	
	public ImageRenderer(boolean toFBO) {
		this.fbo = toFBO ? new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER) : null;
	}

	public Texture render() {
		if (fbo != null) fbo.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		if (fbo != null) {
			fbo.unbind();
			return fbo.getTexture();
		} else return null;
	}

	public void cleanUp() {
		if (fbo != null) fbo.cleanUp();
	}
}
