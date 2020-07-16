#version 400 core

in vec2 position;

out vec2 texCoords;

uniform mat4 transformationMatrix;

void main(void){

	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
	texCoords = position * 0.5 + 0.5;
	
}
