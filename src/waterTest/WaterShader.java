package waterTest;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

/**
 * 水面着色器程序
 * @author ZhangQinghua
 */

public class WaterShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/waterTest/water.vs";
	private static final String FRAGMENT_FILE = "src/waterTest/water.frag";

	/*
	 * 一致变量
	 */
	private int model;
	private int view;
	private int projection;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);

	}

	@Override
	protected void getAllUniformLocations() {
		model = super.getUniformLocation("model");
		view = super.getUniformLocation("view");
		projection = super.getUniformLocation("projection");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	/**
	 * Load Projection
	 * @param matrix
	 */
	public void loadProjection(Matrix4f matrix) {
		super.loadMatrix(projection, matrix);
	}

	/**
	 * Load View info
	 * @param camera
	 */
	public void loadView(Camera camera) {
		super.loadMatrix(view, Maths.createViewMatrix(camera));
	}

	public void loadModel(Matrix4f matrix) {
		super.loadMatrix(model, matrix);
	}
}
