package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.Model;
import models.Raw;
import models.Texture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		MasterRenderer renderer = new MasterRenderer();

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack terrainPack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(-1, -1, loader, terrainPack, blendMap, "heightmap");

		Model grass = new Model(OBJLoader.loadMoadel("fern", loader), new Texture(loader.loadTexture("fern"), 2));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		List<Entity> grassList = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			if (i % 1 == 0) {
				float x = random.nextFloat() * 800 - 800;
				float z = random.nextFloat() * -800;
				float y = terrain.getHeightOfTerrain(x, z);
				grassList.add(new Entity(grass, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 2.9f));
			}

		}
		Raw raw = OBJLoader.loadMoadel("tree", loader);
		Texture texture = new Texture(loader.loadTexture("tree"));
		Model model = new Model(raw, texture);
		// Entity entity = new Entity(model, new Vector3f(0, 0, -25), 0, 0, 0, 1);
		List<Entity> allCubes = new ArrayList<Entity>();
		for (int i = 0; i < 100; i++) {
			float x = random.nextFloat() * 800 - 400;
			float y = 0;
			float z = random.nextFloat() * -600;
			float rotX = 0;
			float rotY = 0;
			allCubes.add(new Entity(model, new Vector3f(x, y, z), rotX, rotY, 0.0f, 1.0f));
			allCubes.add(new Entity(model, new Vector3f(x, y, z), rotX, rotY, 0.0f, 3.0f));
			allCubes.add(new Entity(model, new Vector3f(x, y, z), rotX, rotY, 0.0f, 8.0f));
		}

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		lights.add(sun);
		Player player = new Player(new Model(OBJLoader.loadMoadel("person", loader), new Texture(loader.loadTexture("playerTexture"))), new Vector3f(-300, 0, -300), 0, 0, 0, 1);

		Camera camera = new Camera(player);

		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.05f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		while (!Display.isCloseRequested()) {
			// Game logic
			camera.move();
			player.move(terrain);

			// Render
			for (Entity cube : allCubes) {
				// renderer.processEntity(cube);
			}
			for (Entity g : grassList) {
				renderer.processEntity(g);
			}

			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.render(lights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
