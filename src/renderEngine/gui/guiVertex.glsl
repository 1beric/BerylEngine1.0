#version 400

in vec2 position;
in vec2 textureCoords;

out vec2 pass_glPosition;
out vec2 texCoords;

uniform mat4 transformationMatrix;
uniform float guiFlipped;

void main(void){

	gl_Position = transformationMatrix * vec4(position.x, position.y, 0.0, 1.0);
	pass_glPosition = gl_Position.xy;
	texCoords = textureCoords;
	if (guiFlipped > 0.5) {
		texCoords = vec2(textureCoords.x, 1-textureCoords.y);
	}
//	else {
//		texCoords = vec2((position.x*2.0+1.0)/2.0, 1 - (position.y*2.0+1.0)/2.0);
//	}
}
