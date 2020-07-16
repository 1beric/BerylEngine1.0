package renderEngine.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.components.renderable.CameraRC;
import models.components.renderable.LightRC;
import models.components.renderable.Mesh3RC;
import models.components.renderable.Transform3D;
import models.data.Material3D;
import models.data.ModelData;
import models.data.ModelDataType;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import renderEngine.shaders.EntityShader;
import tools.BerylGL;
import tools.math.BerylMath;
import tools.math.BerylMatrix;

public class EntityRenderer {
	
	private static final boolean BATCH = true;

	private EntityShader shader;
	
	public EntityRenderer(BerylMatrix projectionMatrix) {
		shader = new EntityShader();
		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.unbind();
	}

	public void render(List<Mesh3RC> meshes, LightRC light, CameraRC cam) {
		if (shader.isRecompiled()) {
			setProjectionMatrix(BerylMath.getProjectionMatrix());
			shader.setRecompiled(false);
		}

		shader.bind();
		shader.loadLight(light);
		shader.loadViewMatrix(cam.getPos(),cam.getRot());
		
		if (BATCH)	renderBatched(batchEntities(meshes));
		else		renderList(meshes);
		
		shader.unbind();
	}
	
	private void renderBatched(Map<ModelData, List<Mesh3RC>> batches) {
		for (ModelData data : batches.keySet()) {
			RawModel model = LookupTable.getRawModel(data);
			model.bind();
			for (Mesh3RC mesh : batches.get(data)) {
				prepareMaterial(mesh.getMat());
				loadModelMatrix(mesh.getEntity().getTransform());
				model.draw();
			}
			model.unbind();
		}
	}
	
	private void renderList(List<Mesh3RC> meshes) {
		for (Mesh3RC mesh : meshes) {
			if (renderable(mesh)) continue;
			RawModel model = LookupTable.getRawModel(mesh.getData());
			model.bind();
			prepareMaterial(mesh.getMat());
			loadModelMatrix(mesh.getEntity().getTransform());
			model.draw();
			model.unbind();
		}
	}
	

	private Map<ModelData,List<Mesh3RC>> batchEntities(List<Mesh3RC> meshes) {
		Map<ModelData,List<Mesh3RC>> map = new HashMap<>();
		for (Mesh3RC mesh : meshes) {
			if (!renderable(mesh)) continue;
			if (!map.containsKey(mesh.getData())) map.put(mesh.getData(), new ArrayList<>());
			map.get(mesh.getData()).add(mesh);
		}
		return map;
	}
	
	private void prepareMaterial(Material3D material) {
		shader.loadColor(material.getColor());
		shader.loadShineVariables(material.getShineDamper(), material.getReflectivity());
		shader.loadNumberOfRows(material.getNumberOfRows());
		shader.loadOffset(material.getTextureXOffset(), material.getTextureYOffset());
		if (material.isTransparent()) BerylGL.disableCulling();
		else 						  BerylGL.enableCulling();
		shader.loadFakeLightingVariable(material.usesFakeLighting());
		shader.loadTextures(material.getColorTexture(), material.getSpecularAndEmissionMap());
	}
	
	private void loadModelMatrix(Transform3D transform) {
		BerylMatrix transformationMatrix = BerylMath.createTransformationMatrix(
				transform.getPos(), 
				transform.getRot(),
				transform.getScale()
			);
		shader.loadTransformationMatrix(transformationMatrix);
	}

	private boolean renderable(Mesh3RC mesh) {
		return mesh != null && mesh.isActive() &&
				mesh.getData() != null && 
				mesh.getData().getType() != ModelDataType.NOT_SET;
	}

	public void setProjectionMatrix(BerylMatrix m) {
		shader.bind();
		shader.loadProjectionMatrix(m);
		shader.unbind();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
}
