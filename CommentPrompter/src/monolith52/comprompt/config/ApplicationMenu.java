package monolith52.comprompt.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.l2fprod.common.swing.JFontChooser;

public class ApplicationMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	Configure config;
	
	public ApplicationMenu(Configure config) {
		super();
		this.config = config;
	}
	
	public void init() {
		JMenu menu;
		JMenuItem item;
		
		menu = new JMenu("設定");
		add(menu);
		
		item = new JMenuItem("フォント");
		item.addActionListener(new FontItemAction(config::setFont, config::getFont));
		menu.add(item);
		
		item = new JMenuItem("文字色");
		item.addActionListener(new ColorItemAction(config::setFontColor, config::getFontColor));
		menu.add(item);
		
		item = new JMenuItem("背景色");
		item.addActionListener(new ColorItemAction(config::setBgColor, config::getBgColor));
		menu.add(item);
	}
	
	protected class FontItemAction implements ActionListener {
		Consumer<Font> setter;
		Supplier<Font> getter;
		public FontItemAction(Consumer<Font> setter, Supplier<Font> getter) {
			this.setter = setter;
			this.getter = getter;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Font selectedFont = JFontChooser.showDialog(ApplicationMenu.this, "フォントを選択してください", getter.get());
			if (selectedFont != null) {
				setter.accept(selectedFont);
			}
		}
	}
	
	protected class ColorItemAction implements ActionListener {
		Consumer<Color> setter;
		Supplier<Color> getter;
		public ColorItemAction(Consumer<Color> setter, Supplier<Color> getter) {
			this.setter = setter;
			this.getter = getter;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Color selectedColor = JColorChooser.showDialog(ApplicationMenu.this, "", getter.get());
			if (selectedColor != null) {
				setter.accept(selectedColor);
			}
		}
	}
}
