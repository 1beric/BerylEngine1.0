package renderEngine.renderers;

import java.util.List;

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
import renderEngine.models.FrameBuffer.DepthBuffer;
import renderEngine.shaders.GUIShader;
import renderEngine.shaders.SimpleImageShader;
import tools.BerylGL;

public class GUIRenderer {

	private FrameBuffer fbo;
	private GUIShader shader;
	private TextRenderer textRenderer;
	
	private final RawModel MODEL;
	private SimpleImageShader imageShader;
	
	public GUIRenderer() {
		MODEL = Loader.loadToVAO(new ModelData(
				new float[] { -1,  1,  -1, -1,   1,  1,   1, -1 }, 
				2));
		this.shader = new GUIShader();
		this.textRenderer = new TextRenderer();
		
		this.imageShader = new SimpleImageShader();
		this.fbo = new FrameBuffer(DepthBuffer.DEPTH_RENDER_BUFFER, false);
	}
	
	public Texture render(List<Mesh2RC> meshes, Texture[] scene) {
		fbo.bind();
		
		imageShader.bind();
		BerylGL.clear();
		BerylGL.enableBlending();
		BerylGL.disableDepthTest();
		imageShader.loadTexture(scene[0]);
		MODEL.bind();
		MODEL.draw();
		MODEL.unbind();
		scene[0].unbind();
		imageShader.unbind();
		
		shader.bind();
		MODEL.bind();
		meshes.forEach(mesh->renderElement(mesh));
		MODEL.unbind();
		BerylGL.disableBlending();
		BerylGL.enableDepthTest();
		shader.unbind();
		
		fbo.unbind();
		return fbo.getTexture();
	}

	private void renderElement(Mesh2RC gui) {
		if (!renderable(gui)) return;
		prepareMaterial(gui);
		shader.loadTransformation(gui.calcScreenPos(RectStyle.CC),gui.calcScreenScale());
		MODEL.draw();
		if (!gui.getTexts().isEmpty()) {
			shader.unbind();
			MODEL.unbind();
			gui.getTexts().forEach(child->renderText(child));
			shader.bind();
			MODEL.bind();
			BerylGL.enableBlending();
			BerylGL.disableDepthTest();
		}
		gui.getChildren().forEach(child->renderElement(child));
	}
	
	private boolean renderable(Mesh2RC gui) {
		return gui != null && gui.isActive();
	}

	private void renderText(TextGUI text) {
		if (!renderable(text)) return;
		GUIText guiText = new GUIText(text, LookupTable.getFont(text.getFont()));
		RawModel model = LookupTable.getRawModel(text);
		if (model!=null) {
			guiText.setMeshInfo(model);
			textRenderer.render(guiText);
		}
	}
	
	private void prepareMaterial(Mesh2RC gui) {
		shader.loadTransparency(gui.getTransparency());
		shader.loadColor(gui.getColor());
		shader.loadBorder(gui.calcScreenBorderWidth(), gui.getBorderColor(), gui.calcScreenBorderRadius());
		shader.loadTexture(gui.getMat().getColorTexture());
	}
	
	public void cleanUp() {
		shader.cleanUp();
		textRenderer.cleanUp();

		fbo.cleanUp();
		imageShader.cleanUp();
	}

}
