package monolith52.comprompt.config;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import monolith52.comprompt.Application;
import monolith52.comprompt.monitor.TcpMonitoringTask;

public class ApplicationMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	Application app;
	Configure config;
	JMenuItem tcpServerMenuitem;
	
	public ApplicationMenu(Application app, Configure config) {
		super();
		this.app = app;
		this.config = config;
		config.getApplicationModel().addChangeListener(l -> tcpServerMenuitem.setText(getTcpServerLabel()));
	}
	
	public void init() {
		JMenu menu;
		JMenuItem item;
		
		menu = new JMenu("機能");
		add(menu);
		
		tcpServerMenuitem = new JMenuItem(getTcpServerLabel());
		tcpServerMenuitem.addActionListener((e) -> app.startMonitoringTask(new TcpMonitoringTask(config.getApplicationModel().getTcpServerPort())));
		menu.add(tcpServerMenuitem);

		item = new JMenuItem("設定");
		item.addActionListener((e) -> ConfigureDialog.showDialog(config));
		menu.add(item);
	}
	
	protected String getTcpServerLabel() {
		return String.format("TCPサーバ  %d 番ポート", config.getApplicationModel().getTcpServerPort());
	}
}
