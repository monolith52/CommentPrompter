package monolith52.comprompt.config;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Configure {
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	public enum Type{FONT};
	
	protected List<ConfigureChangedListener> listeners = new ArrayList<ConfigureChangedListener>();
	
	String fontName;
	String fontSize = "12";
	String fontStyle = "0";
	
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
	
}
