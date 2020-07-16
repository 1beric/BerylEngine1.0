package renderEngine.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.NativeType;

import models.data.ModelData;

public class RawModel {

	
	private int vaoID;
	private int vertexCount;
	private ModelData data;


	public RawModel(int vaoID, int vertexCount, ModelData data) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.data = data;
	}
	
	public void bind() {
		switch (data.getType()) {
		case POS_DIMS:
			GL30.glBindVertexArray(vaoID);
			GL20.glEnableVertexAttribArray(0);
			break;
		case POS_TEXS:
			GL30.glBindVertexArray(vaoID);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			break;
		case POS_TEXS_NORMS_INDS:
			GL30.glBindVertexArray(vaoID);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			break;
		case POS_TEXS_NORMS_TANGS_INDS:
			System.out.println("UNSUPPORTED");
			break;
		case NOT_SET:
		default:
			break;
		}
	}
	
	public void draw() {
		switch (data.getType()) {
		case POS_DIMS:
		case POS_TEXS:
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount);
			break;
		case POS_TEXS_NORMS_INDS:
			GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
			break;
		case POS_TEXS_NORMS_TANGS_INDS:
			System.out.println("UNSUPPORTED");
			break;
		case NOT_SET:
		default:
			break;
		}
	}
	
	public void drawMulti() {
		switch (data.getType()) {
		case POS_DIMS:
		case POS_TEXS:
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount);
			break;
		case POS_TEXS_NORMS_INDS:
			GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
			break;
		case POS_TEXS_NORMS_TANGS_INDS:
			System.out.println("UNSUPPORTED");
			break;
		case NOT_SET:
		default:
			break;
		}
	}
	
	public void draw(@NativeType(value="GLenum")int mode) {
		switch (data.getType()) {
		case POS_DIMS:
		case POS_TEXS:
			GL11.glDrawArrays(mode, 0, vertexCount);
			break;
		case POS_TEXS_NORMS_INDS:
			GL11.glDrawElements(mode, vertexCount, GL11.GL_UNSIGNED_INT, 0);
			break;
		case POS_TEXS_NORMS_TANGS_INDS:
			System.out.println("UNSUPPORTED");
			break;
		case NOT_SET:
		default:
			break;
		}
	}
	
	public void unbind() {
		switch (data.getType()) {
		case POS_DIMS:
			GL20.glEnableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
			break;
		case POS_TEXS:
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
			break;
		case POS_TEXS_NORMS_INDS:
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			break;
		case POS_TEXS_NORMS_TANGS_INDS:
			System.out.println("UNSOPPORTED");
			break;
		case NOT_SET:
		default:
			break;
		}
	}
	
	
	/**
	 * @return the vaoID
	 */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * @return the vertexCount
	 */
	public int getVertexCount() {
		return vertexCount;
	}


	/**
	 * @return the data
	 */
	public ModelData getData() {
		return data;
	}
	
	
	
}
