package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.Model;
import shaders.StaticShader;
import shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrains.Terrain;

public class MasterRenderer {

	private StaticShader staticShader;
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader;
	private TerrainRenderer terrainRenderer;

	private SkyboxRenderer skyboxRenderer;

	private Map<Model, List<Entity>> entities;
	private List<Terrain> terrains;

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000.0f;

	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;

	public MasterRenderer(Loader loader) {
		enableCulling();

		Matrix4f projection = createProjectionMatrix();

		staticShader = new StaticShader();
		entityRenderer = new EntityRenderer(staticShader, projection);
		terrainShader = new TerrainShader();
		terrainRenderer = new TerrainRenderer(terrainShader, projection);

		entities = new HashMap<Model, List<Entity>>();
		terrains = new ArrayList<Terrain>();

		skyboxRenderer = new SkyboxRenderer(loader, projection, RED, GREEN, BLUE);
	}

	public void render(List<Light> lights, Camera camera) {
		prepare();

		staticShader.start();
		staticShader.loadLights(lights);
		staticShader.loadView(camera);
		staticShader.loadSkyColour(RED, GREEN, BLUE);
		staticShader.loadViewPos(camera);
		entityRenderer.render(entities);
		staticShader.stop();

		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.loadView(camera);
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadViewPos(camera);
		terrainShader.loadShininess(120);
		terrainRenderer.render(terrains);
		terrainShader.stop();

		skyboxRenderer.render(camera);

		entities.clear();
		terrains.clear();
	}

	public void processEntity(Entity entity) {
		Model model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if (batch == null) {
			batch = new ArrayList<Entity>();
			entities.put(model, batch);
		}
		batch.add(entity);
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void cleanUp() {
		staticShader.cleanUp();
		terrainShader.cleanUp();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1.0f);
	}

	private Matrix4f createProjectionMatrix() {
		float aspectRatio = Display.getWidth() / Display.getHeight();
		float y_scale = (float) (1.0f / Math.tan(Math.toRadians(FOV / 2f)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		Matrix4f projection = new Matrix4f();
		projection.m00 = x_scale;
		projection.m11 = y_scale;
		projection.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projection.m23 = -1;
		projection.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projection.m33 = 0;
		return projection;
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
}
