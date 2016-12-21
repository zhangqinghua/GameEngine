package model;

public class Model {

	private Raw raw;
	private Texture texture;

	public Model(Raw raw, Texture texture) {
		super();
		this.raw = raw;
		this.texture = texture;
	}

	public Raw getRaw() {
		return raw;
	}

	public void setRaw(Raw raw) {
		this.raw = raw;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
