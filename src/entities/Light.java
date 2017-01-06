package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;

	// constant, linear, quadratic
	private Vector3f attenuation;

	private Vector3f direction;
	private float cutOff;

	public Light() {
		this.position = new Vector3f(0, 0, 0);
		this.color = new Vector3f(1, 1, 1);

		this.attenuation = new Vector3f(0, 0, 0);

		this.direction = new Vector3f(1, 1, 0);
		this.cutOff = 0;
	}

	/**
	 * Directional Light
	 * @param position
	 * @param colour
	 */
	public Light(Vector3f position, Vector3f color) {
		this();
		this.position = position;
		this.color = color;
	}

	/**
	 * Point Light
	 * @param position
	 * @param colour
	 * @param constant
	 * @param linear
	 * @param quadratic
	 */
	public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
		this(position, color);
		this.attenuation = attenuation;
	}

	/**
	 * Spot Light
	 * @param position
	 * @param colour
	 * @param direction
	 * @param cutOff
	 * @param constant
	 * @param linear
	 * @param quadratic
	 */
	public Light(Vector3f position, Vector3f color, Vector3f attenuation, Vector3f direction, float cutOff) {
		this(position, color, attenuation);
		this.direction = direction;
		this.cutOff = cutOff;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}

	public float getCutOff() {
		return cutOff;
	}

	public void setCutOff(float cutOff) {
		this.cutOff = cutOff;
	}

}
