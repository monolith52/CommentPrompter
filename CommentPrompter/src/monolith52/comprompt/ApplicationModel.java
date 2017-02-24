package monolith52.comprompt;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class ApplicationModel {
	public final static Dimension DEFAULT_SIZE = new Dimension(640, 480);
	public final static int DEFAULT_TCP_SERVER_PORT = 52301;
	
	List<ModelChangedListener<ApplicationModel>> listeners = new ArrayList<ModelChangedListener<ApplicationModel>>();
	Application app;
	int tcpServerPort = DEFAULT_TCP_SERVER_PORT;
	
	// ウインドウ操作のたびにモデルの更新を行いたくないので
	// このモデルにはデータを持たせずに直接GUIの操作を行わせる
	// 最初にApplicationの参照を渡しておく必要がある
	public ApplicationModel(Application app) {
		this.app = app;
	}
	
	public void addChangeListener(ModelChangedListener<ApplicationModel> listener) {
		listeners.add(listener);
	}
	
	public void stateChanged() {
		listeners.forEach(l -> l.modelChanged(this));
	}

	public int getWindowWidth() {
		return (app != null) ? app.getWidth() : DEFAULT_SIZE.width;
	}
	public void setWindowWidth(int windowWidth) {
		if (app != null) app.setSize(windowWidth, app.getHeight());
	}
	public int getWindowHeight() {
		return (app != null) ? app.getHeight() : DEFAULT_SIZE.height;
	}
	public void setWindowHeight(int windowHeight) {
		if (app != null) app.setSize(app.getWidth(), windowHeight);
	}

	public int getTcpServerPort() {
		return tcpServerPort;
	}

	public void setTcpServerPort(int tcpServerPort) {
		this.tcpServerPort = tcpServerPort;
	}
}
