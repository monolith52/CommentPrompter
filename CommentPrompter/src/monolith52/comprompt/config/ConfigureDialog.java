package monolith52.comprompt.config;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ConfigureDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	protected Configure config;
	private final PropertyPanel contentPanel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			Configure config = new Configure(null);
//			config.load();
//			
//			showDialog(config);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ConfigureDialog(Configure config) {
		this.config = config;
		contentPanel = new PropertyPanel(config);
		
		setTitle("設定");
		setSize(400, 360);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(this::onOk);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener((e)->this.dispose());
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void onOk(ActionEvent e) {
		contentPanel.save();
		config.save();
		System.out.println("Configure saved");
		this.dispose();
	}
	
	public static void showDialog(Configure config) {
		ConfigureDialog dialog = new ConfigureDialog(config);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
