package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.Model;
import models.Raw;
import models.Texture;
import shaders.StaticShader;
import toolbox.Maths;

public class EntityRenderer {

	private StaticShader shader;

	public EntityRenderer(StaticShader shader, Matrix4f projection) {
		this.shader = shader;

		shader.start();
		shader.loadProjection(projection);
		shader.stop();
	}

	public void render(Map<Model, List<Entity>> entities) {
		for (Model model : entities.keySet()) {
			prepareModel(model);
			for (Entity entity : entities.get(model)) {
				loadRaw(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRaw().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
	}

	private void prepareModel(Model model) {
		Raw raw = model.getRaw();
		Texture texture = model.getTexture();

		shader.loadNumberOfRows(texture.getNumberOfRows());

		if (texture.isHasTransparency()) {
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());

		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());

	}

	private void unbindModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void loadRaw(Entity entity) {
		Matrix4f modelMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformation(modelMatrix);
		shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}

}
