package monolith52.comprompt;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class CommentViewModel {
	List<ModelChangedListener<CommentViewModel> > listeners = new ArrayList<ModelChangedListener<CommentViewModel> >();
	
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Color DEFAULT_FONT_COLOR = Color.black;
	public static final Color DEFAULT_BG_COLOR = Color.white;

	Font font 		= DEFAULT_FONT;
	Color fontColor = DEFAULT_FONT_COLOR;
	Color bgColor 	= DEFAULT_BG_COLOR;
	
	public void addChangeListener(ModelChangedListener<CommentViewModel> listener) {
		listeners.add(listener);
	}
	
	public void stateChanged() {
		listeners.forEach(l -> l.modelChanged(this));
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		stateChanged();
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		stateChanged();
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		stateChanged();
	}
}
