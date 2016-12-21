package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 0, 3);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float speed = 0.5f;

	public Camera() {
		super();
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += speed;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

}
