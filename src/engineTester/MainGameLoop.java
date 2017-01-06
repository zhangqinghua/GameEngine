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
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(loader);

		// *********TERRAIN TEXTURE STUFF*************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack terrainPack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0, -1, loader, terrainPack, blendMap, "heightmap");

		Model grass = new Model(OBJLoader.loadMoadel("grassModel", loader), new Texture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);

		Model flower = new Model(OBJLoader.loadMoadel("grassModel", loader), new Texture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);

		Model fern = new Model(OBJLoader.loadMoadel("fern", loader), new Texture(loader.loadTexture("fern")));
		fern.getTexture().setNumberOfRows(2);
		fern.getTexture().setHasTransparency(true);

		Model bobble = new Model(OBJLoader.loadMoadel("pine", loader), new Texture(loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);

		Model lamp = new Model(OBJLoader.loadMoadel("lamp", loader), new Texture(loader.loadTexture("lamp")));
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
		lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));

		entities.add(new Entity(lamp, new Vector3f(185, terrain.getHeightOfTerrain(185, -293), -293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, terrain.getHeightOfTerrain(370, -300), -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, terrain.getHeightOfTerrain(293, -305), -305), 0, 0, 0, 1));

		Player player = new Player(new Model(OBJLoader.loadMoadel("person", loader), new Texture(loader.loadTexture("playerTexture"))), new Vector3f(370, 17, -300), 0, 0, 0, 1);

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
			entities.forEach(entity -> renderer.processEntity(entity));
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
