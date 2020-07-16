package renderEngine.renderers;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import guiSystem.RectStyle;
import guiSystem.elements.Mesh2RC;
import guiSystem.elements.TextGUI;
import meshCreation.Loader;
import meshCreation.fontMeshCreation.GUIText;
import models.data.ModelData;
import renderEngine.models.FrameBuffer;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import renderEngine.models.Texture;
import renderEngine.postProcessing.ImageRenderer;
import renderEngine.models.FrameBuffer.DepthBuffer;
import renderEngine.shaders.GUIShader;
import renderEngine.shaders.SimpleImageShader;
import tools.math.BerylMath;
import tools.math.BerylVector;

public class GUIRenderer {

	private FrameBuffer fbo;
	private final RawModel CC;
	private GUIShader shader;
	private FontRenderer fontRenderer;
	
	private final RawModel MODEL;
	private ImageRenderer imageRenderer;
	private SimpleImageShader imageShader;
	
	public GUIRenderer() {
		CC = Loader.loadToVAO(new ModelData(
				new float[] { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f }, 
				new float[] { 	  0,	1,	   0,	  0,	1,	  1,	1,	   0 }));
		MODEL = Loader.loadToVAO(new ModelData(
				new float[] { -1,  1,  -1, -1,   1,  1,   1, -1 }, 
				2));
		this.shader = new GUIShader();
		this.fontRenderer = new FontRenderer();
		
		this.imageRenderer = new ImageRenderer(false);
		this.imageShader = new SimpleImageShader();
		this.fbo = new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER, false);
	}
	
	public Texture render(List<Mesh2RC> meshes, Texture scene) {
		fbo.bind();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(MODEL.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		imageShader.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		scene.bind();
		imageRenderer.render();
		scene.unbind();
		imageShader.unbind();
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		
		shader.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		loadCenterRect();
		shader.loadFlipped(true);
		meshes.forEach(mesh->renderElement(mesh));
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		shader.unbind();
		
		fbo.unbind();
		return fbo.getTexture();
	}

	private void renderElement(Mesh2RC gui) {
		if (gui == null || !gui.isActive()) return;
		BerylVector pos = gui.calcScreenPos(RectStyle.CC);
		BerylVector scale = gui.calcScreenScale();
		shader.loadPosAndScale(pos,scale);
		prepareMaterial(gui);
		shader.loadTransformation(BerylMath.createTransformationMatrix(pos,scale));
		// DRAW CALL ----
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		shader.unbind();
		gui.getTexts().forEach(child->renderText(child));
		shader.bind();
		gui.getChildren().forEach(child->renderElement(child));
	}
	
	private void renderText(TextGUI text) {
		if (text == null || !text.isActive()) return;
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GUIText guiText = new GUIText(text, LookupTable.getFont(text.getFont()));
		RawModel model = LookupTable.getRawModel(text);
		if (model!=null) {
			guiText.setMeshInfo(model);
			fontRenderer.render(guiText);
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		loadCenterRect();
	}
	
	private void prepareMaterial(Mesh2RC gui) {
		shader.loadTransparency(gui.getTransparency());
		shader.loadColor(gui.getColor());
		shader.loadBorder(gui.calcScreenBorderWidth(), gui.getBorderColor(), gui.calcScreenBorderRadius());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		gui.getMat().getColorTexture().bind();
	}
	
	private void loadCenterRect() {
		GL30.glBindVertexArray(CC.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	public void cleanUp() {
		shader.cleanUp();
		fontRenderer.cleanUp();

		fbo.cleanUp();
		imageRenderer.cleanUp();
		imageShader.cleanUp();
	}

}
