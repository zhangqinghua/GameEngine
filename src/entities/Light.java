package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;

	private float constant;
	private float linear;
	private float quadratic;

	private Vector3f direction;
	private float cutOff;

	public Light() {
		this.position = new Vector3f(0, 0, 0);
		this.color = new Vector3f(1, 1, 1);

		this.constant = 0;
		this.linear = 0;
		this.quadratic = 0;

		this.direction = new Vector3f(0, 0, 0);
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
	public Light(Vector3f position, Vector3f color, float constant, float linear, float quadratic) {
		this(position, color);
		this.constant = constant;
		this.linear = linear;
		this.quadratic = quadratic;
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
	public Light(Vector3f position, Vector3f color, Vector3f direction, float cutOff, float constant, float linear, float quadratic) {
		this(position, color, constant, linear, quadratic);
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

	public float getConstant() {
		return constant;
	}

	public void setConstant(float constant) {
		this.constant = constant;
	}

	public float getLinear() {
		return linear;
	}

	public void setLinear(float linear) {
		this.linear = linear;
	}

	public float getQuadratic() {
		return quadratic;
	}

	public void setQuadratic(float quadratic) {
		this.quadratic = quadratic;
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
