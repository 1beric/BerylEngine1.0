#version 400

in vec2 texCoords[11];

out vec4 out_Color;

uniform sampler2D tex;

void main(void) {

	out_Color = vec4(0.0);
	out_Color += texture(tex, texCoords[0]) * 0.0093;
	out_Color += texture(tex, texCoords[1]) * 0.028002;
	out_Color += texture(tex, texCoords[2]) * 0.065984;
	out_Color += texture(tex, texCoords[3]) * 0.121703;
	out_Color += texture(tex, texCoords[4]) * 0.175713;
	out_Color += texture(tex, texCoords[5]) * 0.198596;
	out_Color += texture(tex, texCoords[6]) * 0.175713;
    out_Color += texture(tex, texCoords[7]) * 0.121703;
    out_Color += texture(tex, texCoords[8]) * 0.065984;
    out_Color += texture(tex, texCoords[9]) * 0.028002;
    out_Color += texture(tex, texCoords[10]) * 0.0093;

}
