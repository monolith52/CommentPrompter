package monolith52.comprompt.config;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import monolith52.comprompt.util.ColorUtil;

public class Configure {
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Color DEFAULT_FONT_COLOR = Color.black;
	public static final Color DEFAULT_BG_COLOR = Color.white;
	public enum Type{FONT, COLOR};
	
	protected List<ConfigureChangedListener> listeners = new ArrayList<ConfigureChangedListener>();
	
	String fontName 	= DEFAULT_FONT.getFontName();
	String fontSize 	= String.valueOf(DEFAULT_FONT.getSize());
	String fontStyle 	= String.valueOf(DEFAULT_FONT.getStyle());
	
	String fontColor 	= ColorUtil.toString(DEFAULT_FONT_COLOR);
	String bgColor 		= ColorUtil.toString(DEFAULT_BG_COLOR);
	
	public void addConfigureChangedListener(ConfigureChangedListener listener) {
		listeners.add(listener);
	}
	
	public void setFont(Font selectedFont) {
		fontName = selectedFont.getFontName();
		fontSize = String.valueOf(selectedFont.getSize());
		fontStyle = String.valueOf(selectedFont.getStyle());
		
		listeners.forEach(l -> l.configureChaqnged(this, Type.FONT));
	}
	
	public Font getFont() {
		try {
			int size = Integer.parseInt(fontSize);
			int style = Integer.parseInt(fontStyle);
			return new Font(fontName, style, size);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return DEFAULT_FONT;
		}
	}
	
	public void setFontColor(Color color) {
		fontColor = ColorUtil.toString(color);
		
		listeners.forEach(l -> l.configureChaqnged(this, Type.COLOR));
	}
	
	public Color getFontColor() {
		return ColorUtil.parseColor(fontColor, DEFAULT_FONT_COLOR);
	}
	
	public void setBgColor(Color color) {
		bgColor = ColorUtil.toString(color);
		
		listeners.forEach(l -> l.configureChaqnged(this, Type.COLOR));
	}
	
	public Color getBgColor() {
		return ColorUtil.parseColor(bgColor, DEFAULT_FONT_COLOR);
	}
}
