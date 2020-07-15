package tools;

public class BerylFormattingTools {

	public static String format(float value, int decimalPlaces) {
		String str = String.valueOf(value);
		if (decimalPlaces == 0) return String.valueOf((int)value);
		return str.substring(0,Math.min(str.indexOf('.') + decimalPlaces + 1, str.length()));
	}
	
	public static String flatten(String[] s) {
		String out = "";
		for (int i=0; i<s.length; out += s[i++]);
		return out;
	}
}
