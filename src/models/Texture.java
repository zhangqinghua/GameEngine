package models;

public class Texture {
	private int diffuseId;
	private int specularId;

	private float shininess;

	private boolean hasTransparency;
	private boolean useFakeLighting;

	private int numberOfRows;

	private Texture() {
		hasTransparency = false;
		useFakeLighting = false;
		numberOfRows = 1;
		shininess = 32;
	}

	public Texture(int textureId) {
		this();
		this.diffuseId = textureId;
		this.specularId = textureId;
	}

	public Texture(int textureId, int numberOfRows) {
		this(textureId);
		this.numberOfRows = numberOfRows;
	}

	public Texture(int textureId, boolean hasTransparency, boolean useFakeLighting) {
		this(textureId);
		this.hasTransparency = hasTransparency;
		this.useFakeLighting = useFakeLighting;
	}

	public Texture(int textureId, boolean hasTransparency, boolean useFakeLighting, int numberOfRows) {
		this(textureId, hasTransparency, useFakeLighting);
		this.numberOfRows = numberOfRows;
	}

	public int getDiffuseId() {
		return diffuseId;
	}

	public void setDiffuseId(int diffuseId) {
		this.diffuseId = diffuseId;
	}

	public int getSpecularId() {
		return specularId;
	}

	public void setSpecularId(int specularId) {
		this.specularId = specularId;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		this.shininess = shininess;
	}

}
