#version 400

in vec2 position;

out vec2 texCoords[11];

uniform float height;

void main(void){

	gl_Position = vec4(position, 0.0, 1.0);
	vec2 centerTexCoords = position * 0.5 + 0.5;

	float pixelSize = 1.0 / height;
	for (int i=-5; i<=5; i++) {
		texCoords[i+5] = centerTexCoords + vec2(0, pixelSize * i);
	}

}
