#version 400 core

in vec2 texCoords;

out vec4 out_Color;

uniform sampler2D colorTexture;

void main(void){
	
	vec4 color = texture(colorTexture,texCoords);

	float brightness = (color.r * 0.2126) + (color.g * 0.7152) + (color.b * 0.0722);

	if (brightness > 0.7) {
		out_Color = color;
	} else {
		out_Color = vec4(0.0);
	}

}
