package shaders;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import models.Texture;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader";

	private int location_moel;
	private int location_view;
	private int location_projection;

	private int location_skyColour;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_moel = super.getUniformLocation("model");
		location_view = super.getUniformLocation("view");
		location_projection = super.getUniformLocation("projection");

		location_skyColour = super.getUniformLocation("skyColour");
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_moel, matrix);

	}

	public void loadView(Camera camera) {
		super.loadMatrix(location_view, Maths.createViewMatrix(camera));
	}

	public void loadProjection(Matrix4f matrix) {
		super.loadMatrix(location_projection, matrix);
	}

	public void loadLights(List<Light> lights) {
		for (int i = 0; i < lights.size(); i++) {
			System.out.println(i);
			super.loadFloat(super.getUniformLocation("lights[" + i + "].isExist"), 0.5f);
			
			super.loadVertor(super.getUniformLocation("lights[" + i + "].position"), lights.get(i).getPosition());
			super.loadVertor(super.getUniformLocation("lights[" + i + "].color"), lights.get(i).getColor());

			super.loadFloat(super.getUniformLocation("lights[" + i + "].constant"), lights.get(i).getConstant());
			super.loadFloat(super.getUniformLocation("lights[" + i + "].linear"), lights.get(i).getLinear());
			super.loadFloat(super.getUniformLocation("lights[" + i + "].quadratic"), lights.get(i).getQuadratic());

			super.loadVertor(super.getUniformLocation("lights[" + i + "].direction"), lights.get(i).getDirection());
			super.loadFloat(super.getUniformLocation("lights[" + i + "].cutOff"), lights.get(i).getCutOff());
		}
	}

	public void loadMaterial(Texture material) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getDiffuseId());
		super.loadInt(super.getUniformLocation("material.diffuse"), 0);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getSpecularId());
		super.loadInt(super.getUniformLocation("material.specular"), 1);

		super.loadFloat(super.getUniformLocation("material.shininess"), material.getShininess());

		super.loadInt(super.getUniformLocation("material.numberOfRows"), material.getNumberOfRows());
		// super.loadInt(super.getUniformLocation("material.offset"), "");

		super.loadBoolean(super.getUniformLocation("material.hasTransparency"), material.isHasTransparency());
		super.loadBoolean(super.getUniformLocation("material.useFakeLighting"), material.isUseFakeLighting());
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVertor(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadOffset(Vector2f offset) {
		super.load2DVertor(super.getUniformLocation("material.offset"), offset);
	}
}
