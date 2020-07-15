#version 150

in vec2 texCoords;

out vec4 out_Color;

uniform sampler2D colorTexture;
uniform sampler2D highlightTexture;
uniform float bloom;

void main(void) {

	vec4 sceneColor = texture(colorTexture,texCoords);
	vec4 highlightColor = texture(highlightTexture,texCoords);
	out_Color = sceneColor + highlightColor * bloom;

}
