package models;

public class Texture {

	private int id;
	private float shineDamper = 1;
	private float reflectivity = 0;
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public Texture(int id) {
		super();
		this.id = id;
	}

	public Texture(int id, float shineDamper, float reflectivity, boolean hasTransparency) {
		super();
		this.id = id;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.hasTransparency = hasTransparency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
