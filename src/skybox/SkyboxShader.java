package skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import renderEngine.DisplayManager;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/skybox/skybox.vs";
	private static final String FRAGMENT_FILE = "src/skybox/skybox.frag";

	private static final float ROTATE_SPEED = 1f;
	private float rotation = 0;

	private int location_projection;
	private int location_view;
	private int location_fogColor;

	private int location_dayCubeMap;
	private int location_nightCubeMap;
	private int location_blendFactor;

	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projection = super.getUniformLocation("projection");
		location_view = super.getUniformLocation("view");
		location_fogColor = super.getUniformLocation("fogColor");

		location_blendFactor = super.getUniformLocation("blendFactor");
		location_dayCubeMap = super.getUniformLocation("dayCubeMap");
		location_nightCubeMap = super.getUniformLocation("nightCubeMap");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadView(Camera camera) {
		Matrix4f view = Maths.createViewMatrix(camera);
		view.m30 = 0;
		view.m31 = 0;
		view.m32 = 0;
		rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), view, view);
		super.loadMatrix(location_view, view);
	}

	public void loadProjection(Matrix4f matrix) {
		super.loadMatrix(location_projection, matrix);
	}

	public void loadFog(float r, float g, float b) {
		super.loadVertor(location_fogColor, new Vector3f(r, g, b));
	}

	public void loadBlendFactor(float blendFactor) {
		super.loadFloat(location_blendFactor, blendFactor);
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_dayCubeMap, 0);
		super.loadInt(location_nightCubeMap, 1);
	}

}
