#version 400 core

in vec2 texCoords;

out vec4 out_Color;

uniform sampler2D colorTexture;
uniform float brightness;

void main(void){
	
	out_Color = texture(colorTexture,texCoords) * brightness;

//	float brightness = (color.r * 0.2126) + (color.g * 0.7152) + (color.b * 0.0722);

}
