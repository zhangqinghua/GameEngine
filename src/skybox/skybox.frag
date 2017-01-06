#version 400

in vec3 TexCoords;
out vec4 color;

uniform samplerCube dayCubeMap;
uniform samplerCube nightCubeMap;
uniform float blendFactor;
uniform vec3 fogColor;

const float lowerLimit = 0;
const float upperLimit = 30;

void main() {
	vec4 dayColor = texture(dayCubeMap, TexCoords);
	vec4 nightColor = texture(nightCubeMap, TexCoords);
	vec4 finalColor = mix(dayColor, nightColor, blendFactor);
	
	float factor = (TexCoords.y - lowerLimit) / (upperLimit - lowerLimit);
	factor = clamp(factor, 0.0, 1.0);
	
	color = mix(vec4(fogColor,1.0), finalColor, factor);
}