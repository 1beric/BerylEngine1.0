#version 400

in vec2 pass_glPosition;
in vec2 texCoords;

out vec4 out_Color;

uniform sampler2D tex;
uniform vec3 color;
uniform float transparency;
uniform vec2 borderWidth;
uniform vec2 borderRadius;
uniform vec3 borderColor;
uniform vec2 guiCenter;
uniform vec2 guiScale;

float checkRadius(vec2,vec2,vec2,float);
float plot(float,float);

void main(void) {

	vec3 finalColor = color;
	float finalTransparency = transparency;

	// border width & radius
	vec2 borderSpan = guiScale/2.0 - borderWidth;
	if (pass_glPosition.x < guiCenter.x - borderSpan.x || pass_glPosition.x > guiCenter.x + borderSpan.x ||
			pass_glPosition.y < guiCenter.y - borderSpan.y || pass_glPosition.y > guiCenter.y + borderSpan.y) {
		finalColor = borderColor;
	}
	if (borderRadius.x > 0) {
		vec2 borderRSpan = guiScale/2.0 - borderRadius;
//		finalTransparency = checkRadius(pass_glPosition, guiCenter, guiScale/2.0 - borderRadius, borderRadius.x);
		if (checkRadius(pass_glPosition, guiCenter, guiScale/2.0 - borderRadius, borderRadius.x) == 0) {
			discard;
		}
		if (borderWidth.x > 0 && checkRadius(pass_glPosition, guiCenter, guiScale/2.0 - borderRadius, borderRadius.x-borderWidth.y)==0) {
			finalColor = borderColor;
		}
	}

	out_Color = vec4(finalColor,finalTransparency) * texture(tex,texCoords);

}

float checkRadius(vec2 glPos, vec2 guiCenter, vec2 span, float radius) {
	if (	glPos.x > guiCenter.x + span.x && glPos.y > guiCenter.y + span.y && distance(glPos.xy, guiCenter+span) > radius ||
			glPos.x < guiCenter.x - span.x && glPos.y < guiCenter.y - span.y && distance(glPos.xy, guiCenter-span) > radius ||
			glPos.x < guiCenter.x - span.x && glPos.y > guiCenter.y + span.y && distance(glPos.xy, vec2(guiCenter.x-span.x,guiCenter.y+span.y)) > radius ||
			glPos.x > guiCenter.x + span.x && glPos.y < guiCenter.y - span.y && distance(glPos.xy, vec2(guiCenter.x+span.x,guiCenter.y-span.y)) > radius) {
		return 0;
	}
	return 1;
//	if (glPos.x > guiPos.x + span.x && glPos.y > guiPos.y + span.y) {
//		return plot(glPos.x,distance(glPos.xy, guiPos+span)-radius);
//	}
//	if (glPos.x < guiPos.x - span.x && glPos.y > guiPos.y + span.y) {
//		return smoothstep(0.0,1.0,distance(glPos.xy, vec2(guiPos.x-span.x,guiPos.y+span.y))-radius);
//	}
//	if (glPos.x < guiPos.x - span.x && glPos.y < guiPos.y - span.y) {
//		return smoothstep(0.0,1.0,distance(glPos.xy, guiPos-span)-radius);
//	}
//	if (glPos.x > guiPos.x + span.x && glPos.y < guiPos.y - span.y) {
//		return smoothstep(0.0,1.0,distance(glPos.xy, vec2(guiPos.x+span.x,guiPos.y-span.y))-radius);
//	}
//	return 1;
}

// Plot a line on Y using a value between 0.0-1.0
float plot(float y, float val){
//  return  smoothstep( val-0.02, val, y) -
//          smoothstep( val, val+0.02, y);
	return smoothstep(val-0.02,val,y);
}



