package monolith52.comprompt;

import java.awt.Dimension;

public class ApplicationModel {
	public final static Dimension DEFAULT_SIZE = new Dimension(640, 480);
	
	Application app;
	
	// ウインドウ操作のたびにモデルの更新を行いたくないので
	// このモデルにはデータを持たせずに直接GUIの操作を行わせる
	// 最初にApplicationの参照を渡しておく必要がある
	public ApplicationModel(Application app) {
		this.app = app;
	}
	
	public int getWindowWidth() {
		return app.getWidth();
	}
	public void setWindowWidth(int windowWidth) {
		app.setSize(windowWidth, app.getHeight());
	}
	public int getWindowHeight() {
		return app.getHeight();
	}
	public void setWindowHeight(int windowHeight) {
		app.setSize(app.getWidth(), windowHeight);
	}
}
