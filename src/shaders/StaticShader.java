package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader";

	private int location_moel;
	private int location_view;
	private int location_projection;

	private int location_lightPosition;
	private int location_lightColour;

	private int location_shineDamper;
	private int location_reflectivity;

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

		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
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

	public void loadLight(Light light) {
		super.loadVertor(location_lightPosition, light.getPosition());
		super.loadVertor(location_lightColour, light.getColour());

	}

	public void loadShineVariablee(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
}
