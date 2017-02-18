package monolith52.comprompt.config;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		item.addActionListener(new FontMenuItemAction());
		menu.add(item);
	}
	
	protected class FontMenuItemAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Font selectedFont = JFontChooser.showDialog(ApplicationMenu.this, "Choose Font", config.getFont());
			if (selectedFont != null) {
				config.setFont(selectedFont);
			}
		}
	}
}
