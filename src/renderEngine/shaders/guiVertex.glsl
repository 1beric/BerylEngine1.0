#version 400

in vec2 position;

out vec2 pass_glPosition;
out vec2 texCoords;

uniform mat4 transformationMatrix;

void main(void){
	gl_Position = transformationMatrix * vec4(position / 2.0, 0.0, 1.0);
	pass_glPosition = gl_Position.xy;
	texCoords = position * 0.5 + 0.5;
	texCoords.y = 1 - texCoords.y;
}
