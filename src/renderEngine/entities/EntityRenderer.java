package renderEngine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.components.renderable.CameraRC;
import models.components.renderable.LightRC;
import models.components.renderable.Mesh3RC;
import models.components.renderable.Transform3D;
import models.data.Material3D;
import models.data.ModelData;
import models.data.ModelDataType;
import renderEngine.BerylGL;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;
import tools.math.BerylMath;
import tools.math.BerylMatrix;
import tools.math.BerylVector;

public class EntityRenderer {

	private EntityShader shader;
	private boolean batched;
	
	public EntityRenderer(BerylMatrix projectionMatrix) {
		shader = new EntityShader();
		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.unbind();
		this.batched = true;
		
		
		
		
	}

	public void render(List<Mesh3RC> meshes, LightRC light, CameraRC cam) {
		if (shader.isRecompiled()) {
			shader.bind();
			shader.loadColor(BerylVector.one(3));
			shader.loadProjectionMatrix(BerylMath.getProjectionMatrix());
			shader.unbind();
			shader.setRecompiled(false);
		}

		shader.bind();
		shader.loadLight(light);
		shader.loadViewMatrix(cam.getPos(),cam.getRot());
		
		if (batched)	renderBatched(batchEntities(meshes));
		else			renderList(meshes);
		
		shader.unbind();
	}
	
	private void renderBatched(Map<ModelData,List<Mesh3RC>> batches) {
		for (ModelData data : batches.keySet()) {
			RawModel model = LookupTable.getRawModel(data);
			GL30.glBindVertexArray(model.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			for (Mesh3RC mesh : batches.get(data)) {
				prepareMaterial(mesh.getMat());
				loadModelMatrix(mesh.getEntity().getTransform());
				GL11.glDrawElements(
					GL11.GL_TRIANGLES,
					model.getVertexCount(),
					GL11.GL_UNSIGNED_INT,
					0);
			}
			unbindTexturedModel();
		}
	}
	
	private void renderList(List<Mesh3RC> meshes) {
		for (Mesh3RC mesh : meshes) {
			if (mesh == null || !mesh.isActive() ||
					mesh.getData() == null || 
					mesh.getData().getType() == ModelDataType.NOT_SET) continue;
			int vCount = prepareEntity(mesh);
			GL11.glDrawElements(
					GL11.GL_TRIANGLES,
					vCount,
					GL11.GL_UNSIGNED_INT,
					0);
			unbindTexturedModel();
		}
	}
	
	private Map<ModelData,List<Mesh3RC>> batchEntities(List<Mesh3RC> meshes) {
		Map<ModelData,List<Mesh3RC>> map = new HashMap<>();
		meshes.forEach(mesh->{
			if (mesh != null && mesh.isActive() &&
					mesh.getData() != null && 
					mesh.getData().getType() != ModelDataType.NOT_SET) {
				if (!map.containsKey(mesh.getData())) {
					map.put(mesh.getData(), new ArrayList<>());
				}
				map.get(mesh.getData()).add(mesh);
			}
		});
		return map;
	}
	
	private void prepareMaterial(Material3D material) {
		shader.loadColor(material.getColor());
		shader.loadShineVariables(material.getShineDamper(), material.getReflectivity());
		shader.loadNumberOfRows(material.getNumberOfRows());
		shader.loadOffset(material.getTextureXOffset(), material.getTextureYOffset());
		if (material.isTransparent()) {
			BerylGL.disableCulling();
		}
		shader.loadFakeLightingVariable(material.usesFakeLighting());
		shader.loadTextures(material.getColorTexture(), material.getSpecularAndEmissionMap());
	}
	
	private int prepareEntity(Mesh3RC mesh) {
		RawModel model = LookupTable.getRawModel(mesh.getData());
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		prepareMaterial(mesh.getMat());
		loadModelMatrix(mesh.getEntity().getTransform());
		return model.getVertexCount();
	}
	
	private void unbindTexturedModel() {
		BerylGL.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Transform3D transform) {
		BerylMatrix transformationMatrix = BerylMath.createTransformationMatrix(
				transform.getPos(), 
				transform.getRot(),
				transform.getScale()
			);
		shader.loadTransformationMatrix(transformationMatrix);
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
