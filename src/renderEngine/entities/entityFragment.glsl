#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform sampler2D specularAndEmissionMap;
uniform float usesSpecularMap;
uniform vec3 color;
uniform vec3 lightColor;
uniform vec3 attenuation;
uniform float shineDamper;
uniform float reflectivity;

const float levels = 3;

void main(void) {

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);

//	float distance = length(toLightVector);
//	float attFactor = attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance);
	vec3 unitLightVector = normalize(toLightVector);
	float nDotl = dot(unitNormal,unitLightVector);
	float brightness = max(nDotl,0.0);
	//float level = floor(brightness * levels); //CEL SHADING
	//brightness = level / levels; //CEL SHADING
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
	float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
	specularFactor = max(specularFactor,0.0);
	float dampedFactor = pow(specularFactor,shineDamper);
	//level = floor(dampedFactor * levels); //CEL SHADING
	//dampedFactor = level / levels; //CEL SHADING
	vec3 totalDiffuse = (brightness * lightColor);// / attFactor;
	vec3 totalSpecular = (dampedFactor * reflectivity * lightColor);// / attFactor;

	totalDiffuse = max(totalDiffuse,0.2);

	vec4 textureColor = texture(modelTexture,pass_textureCoords) * vec4(color,1.0);
	if(textureColor.a<0.5){
		discard;
	}

	if (usesSpecularMap > 0.5) {
		vec4 mapInfo = texture(specularAndEmissionMap, pass_textureCoords);
		totalSpecular *= mapInfo.r;
		if (mapInfo.g > 0.5) {
			totalDiffuse = vec3(1.0);
		}
	}

	out_Color = vec4(totalDiffuse,1.0) * textureColor + vec4(totalSpecular,1.0);

}
