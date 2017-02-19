package monolith52.comprompt.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.l2fprod.common.swing.BaseDialog;
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
			Font selectedFont = showLocaledFontChooserDialog(ApplicationMenu.this, "フォントを選択してください", getter.get());
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
	
	protected Font showLocaledFontChooserDialog(Component parent, String title, Font initialFont) {
		BaseDialog dialog;
	    Window window = (parent == null?JOptionPane.getRootFrame():SwingUtilities
	      .windowForComponent(parent));
	    if (window instanceof Frame) {
	      dialog = new BaseDialog((Frame)window, title, true);
	    } else {
	      dialog = new BaseDialog((Dialog)window, title, true);
	    }
	    dialog.setDialogMode(BaseDialog.OK_CANCEL_DIALOG);
	    dialog.getBanner().setVisible(false);

	    JFontChooser chooser = new JFontChooser(new LocaledFontChooserModel());
	    chooser.setSelectedFont(initialFont);

	    dialog.getContentPane().setLayout(new BorderLayout());
	    dialog.getContentPane().add("Center", chooser);
	    dialog.pack();
	    dialog.setLocationRelativeTo(parent);

	    if (dialog.ask()) {
	      return chooser.getSelectedFont();
	    } else {
	      return null;
	    }
	}
}
