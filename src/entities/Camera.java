package entities;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 30, 0);
	private float pitch = 20;
	private float yaw;
	private float roll;
	
	private Player player;
	private float distanceFromPLayer = 50;
	private float angleAroundPlayer = 0;
	
	
	public Camera(Player player) {
		this.player = player;
	}

	public void move() {
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
