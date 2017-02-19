package monolith52.comprompt;

public class ApplicationModel {
	Application app;
	
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
