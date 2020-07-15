package renderEngine.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import guiSystem.RectStyles;
import guiSystem.elements.Mesh2RC;
import guiSystem.elements.TextGUI;
import models.data.ModelData;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import tools.math.BerylMath;
import tools.math.BerylVector;
import renderEngine.Loader;
import renderEngine.fontMeshCreator.GUIText;
import renderEngine.fontRendering.FontRenderer;

public class GUIRenderer {

	private final RawModel CC;
	private GUIShader shader;
	private FontRenderer fontRenderer;
	
	public GUIRenderer() {
		CC = Loader.loadToVAO(new ModelData(
				new float[] { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f }, 
				new float[] { 	  0,	1,	   0,	  0,	1,	  1,	1,	   0 }));
		this.shader = new GUIShader();
		this.fontRenderer = new FontRenderer();
	}
	
	public void render(List<Mesh2RC> meshes) {
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
	}

	private void renderElement(Mesh2RC gui) {
		if (gui == null || !gui.isActive()) return;
		BerylVector pos = gui.calcScreenPos(RectStyles.CC);
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
			guiText.setMeshInfo(model.getVaoID(), model.getVertexCount());
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
	}

}
