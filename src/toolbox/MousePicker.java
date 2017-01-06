package toolbox;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import renderEngine.DisplayManager;

public class MousePicker {

	private Vector3f currentRay;

	private Matrix4f projection;
	private Matrix4f view;
	private Camera camera;

	public MousePicker(Camera camera, Matrix4f projection) {
		this.camera = camera;
		this.projection = projection;
		this.view = Maths.createViewMatrix(camera);
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}

	public void update() {
		view = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}

	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);

		return null;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projection, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() - 1;
		float y = (2f * mouseY) / Display.getHeight() - 1;
		return new Vector2f(x, y);
	}

}
