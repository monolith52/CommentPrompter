package monolith52.comprompt.livetube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import monolith52.comprompt.monitor.MonitoringTaskImpl;
import monolith52.comprompt.view.EntryFoundListener;

public class StreamingTask extends MonitoringTaskImpl {
	boolean running = false;
	String url;
	CommentMonitor commentMonitor;
	Object commentMonitorLock = new Object();
	
	List<EntryFoundListener> entryFoundListeners = new ArrayList<EntryFoundListener>();
	List<StreamingListener> streamingListeners = new ArrayList<StreamingListener>();
	public StreamingTask(String url) {
		this.url = url;
	}
	
	public void addEntryFoundListener(EntryFoundListener listener) {
		entryFoundListeners.add(listener);
	}
	
	public void addStreamingListener(StreamingListener listener) {
		streamingListeners.add(listener);
	}	
	@Override
	public void run() {
		running = true;
		
		try {
			// IDを取得
			StreamingDetector detector = new StreamingDetector(url);
			Streaming streaming = detector.detect();
			if (!running) return;
			
			streamingListeners.forEach(l -> l.streamingDetected(streaming));
			
			// コメントの取得
			synchronized (commentMonitorLock) {
				commentMonitor = new CommentMonitor(streaming.getId());
				if (!running) return;
			}
			entryFoundListeners.forEach(commentMonitor::addEntryFoundListener);
			monitoringListeners.forEach(commentMonitor::addMonitoringListeners);

			// すでにGUIスレッドではないのでコメントの取得は別スレッドでstartする必要がない
			commentMonitor.run();
			
		} catch (IOException e) {
			streamingListeners.forEach(l -> l.detectionFailed(e.getMessage()));
			return;
		}
	}

	public void stop() {
		synchronized (commentMonitorLock) {
			if (commentMonitor != null) {
				commentMonitor.stop();
			}
			running = false;
		}
	}
}
