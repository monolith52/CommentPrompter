package monolith52.comprompt.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import monolith52.comprompt.ModelChangedListener;

public class CommentViewModel {
	List<ModelChangedListener<CommentViewModel> > listeners = new ArrayList<ModelChangedListener<CommentViewModel> >();
	
	static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 32);
	static final Color DEFAULT_FONT_COLOR = Color.black;
	static final Color DEFAULT_BG_COLOR = Color.white;
	static final ViewStyle DEFAULT_VIEW_STYLE = ViewStyleFactory.getInstanceFor(DefaultBottomViewStyle.ID);
	static final boolean DEFAULT_ANTIALIAS = true;

	Font font 		= DEFAULT_FONT;
	Color fontColor = DEFAULT_FONT_COLOR;
	Color bgColor 	= DEFAULT_BG_COLOR;
	ViewStyle viewStyle = DEFAULT_VIEW_STYLE;
	boolean antialias = DEFAULT_ANTIALIAS;
	
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
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public ViewStyle getViewStyle() {
		return viewStyle;
	}

	public void setViewStyle(ViewStyle viewStyle) {
		this.viewStyle = viewStyle;
	}

	public boolean isAntialias() {
		return antialias;
	}

	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
	}
}
