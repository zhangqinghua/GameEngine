package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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

		Model grass = new Model(OBJLoader.loadMoadel("grassModel", loader), new Texture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		List<Entity> grassList = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			grassList.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
			grassList.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1f));
			grassList.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.5f));
		}

		Raw raw = OBJLoader.loadMoadel("tree", loader);
		Texture texture = new Texture(loader.loadTexture("tree"));
		texture.setShineDamper(32);
		texture.setReflectivity(0.1f);
		Model model = new Model(raw, texture);
		Entity entity = new Entity(model, new Vector3f(0, 0, -25), 0, 0, 0, 1);
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

		Light sun = new Light(new Vector3f(100, -10, 100), new Vector3f(1, 1, 1));

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack terrainPack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		Terrain terrain = new Terrain(0, -1, loader, terrainPack, blendMap);
		Terrain terrain1 = new Terrain(-1, -1, loader, terrainPack, blendMap);

		Player player = new Player(new Model(OBJLoader.loadMoadel("stanfordBunny", loader), new Texture(loader.loadTexture("white"))), new Vector3f(0, 0, -30), 0, 0, 0, 1);
		while (!Display.isCloseRequested()) {
			// Game logic
			// entity.increasePosition(0.0f, 0.0f, -0.001f);
			// entity.increaseRotation(0.0f, 0.1f, 0.0f);
			camera.move();
			player.move();
			// Render
			for (Entity cube : allCubes) {
				renderer.processEntity(cube);
			}
			for (Entity g : grassList) {
				renderer.processEntity(g);
			}
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain1);
			renderer.render(sun, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
