#version 400

in vec3 position;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	// 世界坐标
	vec4 world = model * vec4(position, 1.0);
	// 屏幕坐标
	gl_Position = projection * view * world;
	
}