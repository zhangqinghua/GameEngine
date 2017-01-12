#version 400

in vec3 position;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	// ��������
	vec4 world = model * vec4(position, 1.0);
	// ��Ļ����
	gl_Position = projection * view * world;
	
}