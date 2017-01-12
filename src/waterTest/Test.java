package waterTest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Player;
import models.Model;
import models.Texture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;

public class Test {
	public static void main(String[] args) {
		// 创建屏幕
		DisplayManager.createDisplay();

		Player player = new Player(new Model(OBJLoader.loadMoadel("person"), new Texture(Loader.loadTexture("playerTexture"))), new Vector3f(0, 0, 0), 0, 0, 0, 1);

		Camera camera = new Camera(player);

		MasterRenderer renderer = new MasterRenderer(new Loader());

		WaterRenderer waterRenderer = new WaterRenderer(camera, renderer.getProjection());

		while (!Display.isCloseRequested()) {
			/*
			 * Logic
			 */
			camera.move();
			player.move();

			/*
			 * Render
			 */
			waterRenderer.render(null);

			// 更新屏幕
			DisplayManager.updateDisplay();
		}
		// 渲染器释放资源
		waterRenderer.cleanUp();
		// 加载器释放资源
		Loader.cleanUp();
		// 关闭屏幕
		DisplayManager.closeDisplay();
	}
}
