package monolith52.comprompt.util;

import java.awt.Color;
import java.util.regex.Pattern;

public class ColorUtil {
	public static final Pattern COLOR_PATTERN = Pattern.compile("[0-9a-fA-F]{6}");
	
	public static Color parseColor(String str) {
		return parseColor(str, null);
	}
	
	public static Color parseColor(String str, Color defColor) {
		if (str == null) return defColor;
		if (!COLOR_PATTERN.matcher(str).matches()) return defColor;
		try {
			return new Color(
					Integer.parseInt(str.substring(0, 2), 16),
					Integer.parseInt(str.substring(2, 4), 16),
					Integer.parseInt(str.substring(4, 6), 16)					);
		} catch (NumberFormatException e) {
			return defColor;
		}
	}
	
	public static String toString(Color color) {
		if (color == null) return null;
		return String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}
}
