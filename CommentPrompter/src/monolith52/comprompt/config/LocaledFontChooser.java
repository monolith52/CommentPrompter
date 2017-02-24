package monolith52.comprompt.config;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.l2fprod.common.swing.BaseDialog;
import com.l2fprod.common.swing.JFontChooser;

public class LocaledFontChooser {
	
	protected static Font showDialog(Component parent, String title, Font initialFont) {
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
