package renderEngine.models;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import tools.BerylGL;
import tools.io.BerylDisplay;

public class FrameBuffer {

	private static final int SAMPLES = 4;
	
	public enum DepthBuffer {
		NONE, DEPTH_TEXTURE, DEPTH_RENDER_BUFFER
	}
	
	private Texture tex;
	private Texture tex1;
	private Texture depth;
	private int frameBuffer;
	private DepthBuffer dbType;
	
	private int width;
	private int height;
	private boolean multisampleAndMultiTarget;
	
	public FrameBuffer(DepthBuffer dbType, boolean multisample) {
		this.width = BerylDisplay.WIDTH;
		this.height = BerylDisplay.HEIGHT;
		this.dbType = dbType;
		this.multisampleAndMultiTarget = multisample;
		frameBuffer = createFrameBuffer();
		if (multisampleAndMultiTarget) {
			tex = createMultisampleTextureAttachment(GL30.GL_COLOR_ATTACHMENT0);
			tex1 = createMultisampleTextureAttachment(GL30.GL_COLOR_ATTACHMENT1);
		} else tex = createTextureAttachment();
		switch (dbType) {
		case DEPTH_RENDER_BUFFER:
			depth = createDepthBufferAttachment();
			break;
		case DEPTH_TEXTURE:
			depth = createDepthTextureAttachment();
			break;
		case NONE:
			break;
		}
		unbind();
	}

	public FrameBuffer(float scale, DepthBuffer dbType) {
		this.width = (int)(BerylDisplay.WIDTH * scale);
		this.height = (int)(BerylDisplay.HEIGHT * scale);
		this.dbType = dbType;
		frameBuffer = createFrameBuffer();
		tex = createTextureAttachment();
		if (dbType == DepthBuffer.DEPTH_RENDER_BUFFER) depth = createDepthBufferAttachment();
		else if (dbType == DepthBuffer.DEPTH_TEXTURE)  depth = createDepthTextureAttachment();
		unbind();
	}
	
	public void resolve(int readBuffer, FrameBuffer resolveTo) {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, resolveTo.frameBuffer);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.frameBuffer);
		GL11.glReadBuffer(readBuffer);
		GL30.glBlitFramebuffer(0, 0, width, height, 
				0, 0, resolveTo.width, resolveTo.height, 
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, 
				GL11.GL_NEAREST);
		this.unbind();
	}
	
	public void resolve(FrameBuffer resolveTo) {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, resolveTo.frameBuffer);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.frameBuffer);
		GL30.glBlitFramebuffer(0, 0, width, height, 
				0, 0, resolveTo.width, resolveTo.height, 
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, 
				GL11.GL_NEAREST);
		this.unbind();
	}
	
	public void resolve() {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0); // default
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.frameBuffer);
		GL11.glDrawBuffer(GL11.GL_BACK);
		GL30.glBlitFramebuffer(0, 0, width, height, 
				0, 0, BerylDisplay.getVPWidth(), BerylDisplay.getVPWidth(), 
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, 
				GL11.GL_NEAREST);
		this.unbind();
	}
	
	public void determineDrawBuffers() {
		IntBuffer bufs = BufferUtils.createIntBuffer(2);
		bufs.put(GL30.GL_COLOR_ATTACHMENT0);
		if (multisampleAndMultiTarget) bufs.put(GL30.GL_COLOR_ATTACHMENT1);
		bufs.flip();
		GL20.glDrawBuffers(bufs);
	}
	
	public void bind() {
		bindFrameBuffer(frameBuffer);
	}
	
	public void unbind() {
		int status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		if (status != GL30.GL_FRAMEBUFFER_COMPLETE) {
			System.out.println(status);
		}
		BerylGL.glErrorHandling();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		BerylGL.resetViewport();

	}

	public Texture getTexture() {
		return tex;
	}
	
	public Texture getDepthTexture() {
		if (dbType == DepthBuffer.DEPTH_TEXTURE) return depth;
		else return null;
	}

	private void bindFrameBuffer(int frameBuffer){
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		GL11.glViewport(0, 0, width, height);
	}
	
	
	private int createFrameBuffer() {
		int i = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, i);
		determineDrawBuffers();
		return i;
	}
	
	private Texture createTextureAttachment() {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height,
				0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
				texture, 0);
		return new Texture("FBO", texture);
	}
	
	private Texture createMultisampleTextureAttachment(int attachment) {
		int buffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, SAMPLES, GL11.GL_RGBA8, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, attachment,
				GL30.GL_RENDERBUFFER, buffer);
		return new Texture("col", buffer);
	}
	
	private Texture createDepthTextureAttachment() {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
				0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
				texture, 0);
		return new Texture("dTex",texture);
	}

	private Texture createDepthBufferAttachment() {
		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		if (multisampleAndMultiTarget) {
			GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, SAMPLES, GL11.GL_DEPTH_COMPONENT, width, height);
		} else {
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
		}
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
				GL30.GL_RENDERBUFFER, depthBuffer);
		return new Texture("dBuf",depthBuffer);
	}


	public void cleanUp() {//call when closing the game
		GL30.glDeleteFramebuffers(frameBuffer);
		GL11.glDeleteTextures(tex.getTextureID());
		if (multisampleAndMultiTarget) GL11.glDeleteTextures(tex1.getTextureID());
		if (dbType == DepthBuffer.DEPTH_RENDER_BUFFER) 	GL30.glDeleteRenderbuffers(depth.getTextureID());
		if (dbType == DepthBuffer.DEPTH_TEXTURE)		GL30.glDeleteTextures(depth.getTextureID());
	}
	
}
