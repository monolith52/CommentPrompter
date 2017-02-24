package monolith52.comprompt.config;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import monolith52.comprompt.ApplicationModel;
import monolith52.comprompt.view.CommentViewModel;
import monolith52.comprompt.view.ViewStyleFactory;

public class PropertyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Configure config;
	Font font;
	Color fontColor;
	Color bgColor;
	int TcpServerPort;
	
	private JFormattedTextField tcpServerPortText;
	private JLabel fontLabel;
	private JCheckBox antialiasCheckBox;
	private JComboBox<String> viewStyleComboBox;
	
	public PropertyPanel(Configure config) {
		this.config = config;
		font 		= config.getCommentViewModel().getFont();
		fontColor 	= config.getCommentViewModel().getFontColor();
		bgColor 	= config.getCommentViewModel().getBgColor();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u63CF\u753B\u8A2D\u5B9A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("フォント:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		
		fontLabel = new JLabel("フォント名 スタイル フォントサイズ");
		GridBagConstraints gbc_fontLabel = new GridBagConstraints();
		gbc_fontLabel.anchor = GridBagConstraints.WEST;
		gbc_fontLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fontLabel.gridx = 1;
		gbc_fontLabel.gridy = 0;
		panel_1.add(fontLabel, gbc_fontLabel);
		fontLabel.setBorder(null);
		
		JButton fontButton = new JButton("変更...");
		fontButton.addActionListener(this::changeFont);
		GridBagConstraints gbc_fontButton = new GridBagConstraints();
		gbc_fontButton.anchor = GridBagConstraints.WEST;
		gbc_fontButton.insets = new Insets(0, 0, 5, 0);
		gbc_fontButton.gridx = 2;
		gbc_fontButton.gridy = 0;
		panel_1.add(fontButton, gbc_fontButton);
		
		JLabel lblNewLabel_2 = new JLabel("フォント色:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JButton fontColorButton = new JButton("...");
		fontColorButton.addActionListener(this::changeFontColor);
		GridBagConstraints gbc_fontColorButton = new GridBagConstraints();
		gbc_fontColorButton.gridwidth = 2;
		gbc_fontColorButton.insets = new Insets(0, 0, 5, 0);
		gbc_fontColorButton.anchor = GridBagConstraints.WEST;
		gbc_fontColorButton.gridx = 1;
		gbc_fontColorButton.gridy = 1;
		panel_1.add(fontColorButton, gbc_fontColorButton);
		
		JLabel lblNewLabel_3 = new JLabel("背景色:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 2;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JButton bgColorButton = new JButton("...");
		bgColorButton.addActionListener(this::changeBgColor);
		GridBagConstraints gbc_bgColorButton = new GridBagConstraints();
		gbc_bgColorButton.gridwidth = 2;
		gbc_bgColorButton.insets = new Insets(0, 0, 5, 0);
		gbc_bgColorButton.anchor = GridBagConstraints.WEST;
		gbc_bgColorButton.gridx = 1;
		gbc_bgColorButton.gridy = 2;
		panel_1.add(bgColorButton, gbc_bgColorButton);
		
		JLabel label_1 = new JLabel("描画方向:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 5, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 3;
		panel_1.add(label_1, gbc_label_1);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		panel_1.add(panel, gbc_panel);
		
		viewStyleComboBox = new JComboBox<String>();
		panel.add(viewStyleComboBox);
		String[] keys = new ArrayList<String>(ViewStyleFactory.getDirectory().keySet()).toArray(new String[0]);
		viewStyleComboBox.setModel(new DefaultComboBoxModel<String>(keys));
		viewStyleComboBox.setSelectedIndex(findSelectedViewStyle(config.getCommentViewModel().getViewStyle().getId()));
		
		antialiasCheckBox = new JCheckBox("アンチエイリアス");
		antialiasCheckBox.setSelected(config.getCommentViewModel().isAntialias());
		GridBagConstraints gbc_antialiasCheckBox = new GridBagConstraints();
		gbc_antialiasCheckBox.anchor = GridBagConstraints.WEST;
		gbc_antialiasCheckBox.gridwidth = 3;
		gbc_antialiasCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_antialiasCheckBox.gridx = 0;
		gbc_antialiasCheckBox.gridy = 4;
		panel_1.add(antialiasCheckBox, gbc_antialiasCheckBox);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u6A5F\u80FD\u8A2D\u5B9A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNewLabel = new JLabel("TCPサーバーポート");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 5, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.WEST;
		gbc_panel_4.fill = GridBagConstraints.VERTICAL;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_2.add(panel_4, gbc_panel_4);
		
		tcpServerPortText = new JFormattedTextField();
		tcpServerPortText.setValue( new Integer(config.getApplicationModel().getTcpServerPort()) );
		panel_4.add(tcpServerPortText);
		tcpServerPortText.setColumns(5);

		tcpServerPortText.setFormatterFactory(new PortFormatterFactory());
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(1, Integer.MAX_VALUE));
		add(panel_3);
		
		
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{fontButton, fontColorButton, bgColorButton, viewStyleComboBox, antialiasCheckBox, tcpServerPortText, panel}));

		updateFontLabel();
	}
	
	private int findSelectedViewStyle(String viewStyleId) {
		ComboBoxModel<String> model = viewStyleComboBox.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (model.getElementAt(i).equals(viewStyleId)) return i;
		}
		return 0;
	}

	protected void changeFont(ActionEvent e) {
		Font selectedFont = LocaledFontChooser.showDialog(this, "Select font", font);
		if (selectedFont == null) return;
		font = selectedFont;
		updateFontLabel();
	}
	
	protected void updateFontLabel() {
		String style = "PLAIN";
		if (font.isBold()) {
			style = (font.isItalic() ? "BOLD ITALIC" : "BOLD");
		} else if (font.isItalic()) {
			style = "ITALIC";
		}
		fontLabel.setText(String.format("%s %s %spx", font.getFamily(), style, font.getSize()));
	}
	
	protected void changeFontColor(ActionEvent e) {
		Color selectedColor = JColorChooser.showDialog(this, "Select color", fontColor);
		if (selectedColor == null) return;
		fontColor = selectedColor;
	}
	
	protected void changeBgColor(ActionEvent e) {
		Color selectedColor = JColorChooser.showDialog(this, "Select color", bgColor);
		if (selectedColor == null) return;
		bgColor = selectedColor;
	}

	public void save() {
		CommentViewModel cvm = config.getCommentViewModel();
		cvm.setFont(font);
		cvm.setFontColor(fontColor);
		cvm.setBgColor(bgColor);
		cvm.setViewStyle(ViewStyleFactory.getInstanceFor((String)viewStyleComboBox.getSelectedItem()));
		cvm.setAntialias(antialiasCheckBox.isSelected());
		cvm.stateChanged();
		
		ApplicationModel apm = config.getApplicationModel();
		apm.setTcpServerPort(Integer.parseInt(tcpServerPortText.getText()));
		apm.stateChanged();
	}
	
//	public static void main(String[] args) {
//		Configure config = new Configure(null);
//		config.load();
//		
//		JPanel panel = new PropertyPanel(config);
//		JFrame frame = new JFrame();
//		frame.getContentPane().setLayout(new BorderLayout());
//		frame.getContentPane().add(panel);
//		frame.pack();
//		frame.setSize(new Dimension(400, 400));
//		frame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//	}
	
}
