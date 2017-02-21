package monolith52.comprompt.monitor;

import monolith52.comprompt.view.EntryFoundListener;

public interface MonitoringTask extends Runnable {
	public void addMonitoringListener(MonitoringListener listener);
	public void addEntryFoundListneer(EntryFoundListener listener);
	public void stop();
}
