#version 330

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform vec3 color;
uniform sampler2D fontAtlas;
uniform float transparency;

const float width = 0.5;
const float edge = 0.08;

void main(void){

	float distance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
	float alpha = transparency - smoothstep(width,width+edge,distance);
	

	out_Color = vec4(color, alpha);

}
