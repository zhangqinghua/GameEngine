package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.Model;
import models.Raw;
import models.Texture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		Raw raw = OBJLoader.loadMoadel("stall", loader);
		Texture texture = new Texture(loader.loadTexture("stallTexture"));
		texture.setShineDamper(32);
		texture.setReflectivity(0.1f);
		Model model = new Model(raw, texture);

		Light sun = new Light(new Vector3f(0, 0, 20), new Vector3f(1, 1, 0));

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();

		List<Entity> allCubes = new ArrayList<Entity>();
		
		Terrain terrain = new Terrain(0, 0, loader, new Texture(loader.loadTexture("grass")));
		Terrain terrain1 = new Terrain(1, 0, loader, new Texture(loader.loadTexture("tree")));
		

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * 100 - 50;
			float rotX = random.nextFloat() * 180.0f;
			float rotY = random.nextFloat() * 180.0f;
			allCubes.add(new Entity(model, new Vector3f(x, y, z), rotX, rotY, 0.0f, 1.0f));
		}
		while (!Display.isCloseRequested()) {
			// Game logic
			// entity.increasePosition(0.0f, 0.0f, -0.001f);
			// entity.increaseRotation(0.0f, 0.1f, 0.0f);
			camera.move();

			// Render
//			for (Entity entity : allCubes) {
//				renderer.processEntity(entity);
//			}
			renderer.processTerrain(terrain);
//			renderer.processTerrain(terrain1);
			renderer.render(sun, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
