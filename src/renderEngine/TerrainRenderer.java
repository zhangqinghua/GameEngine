package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.Raw;
import models.Texture;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(TerrainShader shader, Matrix4f projection) {
		this.shader = shader;
		shader.start();
		shader.loadProjection(projection);
		shader.connectTextureUnits();
		shader.stop();
	}

	public void render(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			prepareTerrain(terrain);
			loadRaw(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRaw().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindModel();
		}
	}

	private void prepareTerrain(Terrain terrain) {
		Raw raw = terrain.getRaw();
		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(terrain);
	}
	
	public void bindTextures(Terrain terrain) {
		TerrainTexturePack texturePack = terrain.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getId());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getId());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getId());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getId());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getId());
		
	}

	private void unbindModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void loadRaw(Terrain terrain) {
		Matrix4f modelMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0,
				0, 1.0f);
		shader.loadTransformation(modelMatrix);
	}

}
