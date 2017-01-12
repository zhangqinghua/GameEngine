package waterTest;

import org.lwjgl.util.vector.Vector3f;

public class Water {
	// 水的位置
	private float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };

	private int VAO;
	// 位置
	private Vector3f position;
	// 颜色
	private Vector3f color;
	// 反射强度
	private float shines = 32f;

	public Water() {

	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
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

	public float getShines() {
		return shines;
	}

	public void setShines(float shines) {
		this.shines = shines;
	}

	public int getVAO() {
		return VAO;
	}

	public void setVAO(int vAO) {
		VAO = vAO;
	}

}
