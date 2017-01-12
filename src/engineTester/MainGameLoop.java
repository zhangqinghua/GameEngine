package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.Model;
import models.Texture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(loader);

		// *********TERRAIN TEXTURE STUFF*************
		List<Terrain> terrains = new ArrayList<Terrain>();
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack terrainPack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0, -1, loader, terrainPack, blendMap, "heightmap");
		terrains.add(terrain);

		//		Model grass = new Model(OBJLoader.loadMoadel("grassModel", loader), new Texture(loader.loadTexture("grassTexture")));
		//		grass.getTexture().setHasTransparency(true);
		//		grass.getTexture().setUseFakeLighting(true);

		//		Model flower = new Model(OBJLoader.loadMoadel("grassModel", loader), new Texture(loader.loadTexture("flower")));
		//		flower.getTexture().setHasTransparency(true);
		//		flower.getTexture().setUseFakeLighting(true);

		//		Model fern = new Model(OBJLoader.loadMoadel("fern", loader), new Texture(loader.loadTexture("fern")));
		//		fern.getTexture().setNumberOfRows(2);
		//		fern.getTexture().setHasTransparency(true);

		Model bobble = new Model(OBJLoader.loadMoadel("pine"), new Texture(Loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);

		Model lamp = new Model(OBJLoader.loadMoadel("lamp"), new Texture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);

		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 400; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 800;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(bobble, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 2f));
			}
		}

		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
		lights.add(new Light(new Vector3f(0, 10, -0), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));

		entities.add(new Entity(lamp, new Vector3f(185, terrain.getHeightOfTerrain(185, -293), -293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, terrain.getHeightOfTerrain(370, -300), -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, terrain.getHeightOfTerrain(293, -305), -305), 0, 0, 0, 1));

		Player player = new Player(new Model(OBJLoader.loadMoadel("person"), new Texture(loader.loadTexture("playerTexture"))), new Vector3f(0, 0, 0), 0, 0, 0, 1);
		entities.add(player);

		Camera camera = new Camera(player);

		MousePicker picker = new MousePicker(camera, renderer.getProjection());

		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		//		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.05f), new Vector2f(0.25f, 0.25f));
		//		guis.add(gui);
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		//*******************Water render set-up***********************
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjection(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(0, 0, 20);
		waters.add(water);

		GuiTexture reflection = new GuiTexture(buffers.getReflectionTexture(), new Vector2f(-0.5f, 0.8f), new Vector2f(0.25f, 0.25f));
		GuiTexture refraction = new GuiTexture(buffers.getRefractionTexture(), new Vector2f(0.5f, 0.8f), new Vector2f(0.25f, 0.25f));
		guis.add(reflection);
		guis.add(refraction);
		while (!Display.isCloseRequested()) {
			// Game logic
			camera.move();
			player.move(terrain);
			picker.update();

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			// Render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScence(entities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 1f));
			camera.getPosition().y += distance;
			camera.invertPitch();

			// Render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScence(entities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			buffers.unbindCurrentFrameBuffer();
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);

			// Render to scence
			renderer.renderScence(entities, terrains, lights, camera, new Vector4f(0, -1, 0, 15));
			waterRenderer.render(waters, camera, lights.get(0));
			//			guiRenderer.render(guis);

			DisplayManager.updateDisplay();
		}
		buffers.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
