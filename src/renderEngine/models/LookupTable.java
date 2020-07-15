package renderEngine.models;

import java.util.HashMap;
import java.util.Map;

import renderEngine.fontMeshCreator.FontType;
import tools.BerylFormattingTools;

public class LookupTable {

	private static Map<Object, RawModel> rawModels = new HashMap<Object, RawModel>();
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, FontType> fonts = new HashMap<String, FontType>();
	
	public static void putRawModel(Object o, RawModel model) {
		rawModels.put(o, model);
	}

	public static void putTexture(String s, Texture id) {
		textures.put(s, id);
	}
	
	public static void putTexture(String[] s, Texture id) {
		textures.put(BerylFormattingTools.flatten(s), id);
	}
	
	public static void putFont(String s, FontType font) {
		fonts.put(s, font);
	}
	
	public static RawModel getRawModel(Object o) {
		return rawModels.get(o);
	}
	
	public static Texture getTexture(String s) {
		return textures.get(s);
	}
	
	public static Texture getTexture(String[] s) {
		return textures.get(BerylFormattingTools.flatten(s));
	}
	
	public static FontType getFont(String s) {
		return fonts.get(s);
	}
	
	public static boolean containsRawModel(Object o) {
		return rawModels.containsKey(o);
	}
	
	public static boolean containsTexture(String s) {
		return textures.containsKey(s);
	}
	
	public static boolean containsFont(String s) {
		return fonts.containsKey(s);
	}
	

	
}
