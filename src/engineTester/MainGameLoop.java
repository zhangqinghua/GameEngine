package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import model.Texture;
import model.Raw;
import model.Model;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		Raw raw = OBJLoader.loadMoadel("stall", loader);
		Texture texture = new Texture(loader.loadTexture("stallTexture"));
		texture.setShineDamper(32);
		texture.setReflectivity(0.1f);
		Model textureModel = new Model(raw, texture);
		Entity entity = new Entity(textureModel, new Vector3f(0.0f, -5.0f, -20f), 0, 0, 0, 1.0f);
		
		Light light = new Light(new Vector3f(0, 0, 20), new Vector3f(1, 1, 0));
		
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			// Game logic
			// entity.increasePosition(0.0f, 0.0f, -0.001f);
			entity.increaseRotation(0.0f, 0.1f, 0.0f);
			camera.move();

			// Render
			shader.start();
			shader.loadLight(light);
			shader.loadView(camera);
			renderer.render(entity, shader);
			shader.stop();

			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
