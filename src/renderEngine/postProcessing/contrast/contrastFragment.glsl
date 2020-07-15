#version 400

in vec2 texCoords;

out vec4 out_Color;

uniform sampler2D tex;
uniform float contrast;

void main(void) {

	out_Color = texture(tex,texCoords);
	out_Color.rgb = (out_Color.rgb - 0.5) * (1.0 + contrast) + 0.5;

}
