package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonitoringTask implements Runnable {
	boolean running = false;
	String url;
	List<CommentFoundListener> commentFoundListeners = new ArrayList<CommentFoundListener>();
	List<MonitoringListener> monitoringListeners = new ArrayList<MonitoringListener>();
	
	public MonitoringTask(String url) {
		this.url = url;
	}
	
	public void addCommentFoundListener(CommentFoundListener listener) {
		commentFoundListeners.add(listener);
	}
	
	public void addMonitoringListener(MonitoringListener listener) {
		monitoringListeners.add(listener);
	}
	
	@Override
	public void run() {
		running = true;
		
		try {
			// 画面表示をクリア
//			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			// IDを取得
			StreamingDetector detector = new StreamingDetector(url);
			Streaming streaming = detector.detect();
			if (!running) return;
			
			monitoringListeners.forEach(l -> l.streamingDetected(streaming));
			
			// コメントの取得
			// すでにGUIスレッドではないのでコメントの取得は別スレッドでstartする必要がない
			CommentMonitor commentMonitor = new CommentMonitor(streaming.getId());
			commentFoundListeners.forEach(commentMonitor::addCommentFoundListener);
			monitoringListeners.forEach(commentMonitor::addMonitoringListeners);
			commentMonitor.run();
			
		} catch (IOException e) {
			monitoringListeners.forEach(l -> l.detectionFailed(e.getMessage()));
			return;
		}
	}

	public void stop() {
		running = false;
	}
}
