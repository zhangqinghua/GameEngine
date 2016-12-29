package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader";

	private int location_moel;
	private int location_view;
	private int location_projection;

	private int location_lightPosition;
	private int location_lightColour;

	private int location_shineDamper;
	private int location_reflectivity;
	
	private int location_skyColour;
	
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;

	public TerrainShader() {
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
		
		location_skyColour = super.getUniformLocation("skyColour");
		
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
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
		super.loadVertor(location_lightColour, light.getColor());

	}

	public void loadShineVariablee(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVertor(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
	}
}
