#version 400 core

in vec2 position;

out vec2 textureCoords;
out vec4 clipSpace;
out vec3 toCameraVector;
out vec3 fromLightPosition;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

const float tiling = 4.0;

void main(void) {
	vec4 worldPosition = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
	clipSpace = projectionMatrix * viewMatrix * worldPosition;
	gl_Position = clipSpace;
	textureCoords = vec2(position.x/2.0 + 0.5, position.y/2.0 + 0.5) * tiling;
	
	toCameraVector = cameraPosition - vec3(worldPosition.xyz);
	fromLightPosition = worldPosition.xyz - lightPosition;
}