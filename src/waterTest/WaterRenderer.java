package waterTest;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.Raw;
import renderEngine.Loader;
import toolbox.Maths;

public class WaterRenderer {
	private WaterShader shader;
	private Camera camera;
	private Raw quad;

	public WaterRenderer(Camera camera, Matrix4f projection) {
		shader = new WaterShader();
		this.camera = camera;
		float[] vertices = { -1, -1, 1, -1, 1, 1, 1, 1, -1, 1, -1, -1 };
		quad = Loader.loadToVAO(vertices, 2);

		shader.start();
		shader.loadProjection(projection);
		shader.stop();
	}

	/**
	 * äÖÈ¾
	 */
	public void render(List<Water> waters) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		
		shader.loadView(camera);

		

		Matrix4f model = Maths.createTransformationMatrix(new Vector3f(0, 0, -10), 0, 0, 0, 60);
		shader.loadModel(model);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());

		GL20.glEnableVertexAttribArray(0);
		
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	/**
	 * Çå³ýÄÚ´æ
	 */
	public void cleanUp() {
		shader.cleanUp();
	}

}
