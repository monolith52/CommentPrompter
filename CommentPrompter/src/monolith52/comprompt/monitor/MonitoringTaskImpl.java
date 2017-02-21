package monolith52.comprompt.monitor;

import java.util.ArrayList;
import java.util.List;

import monolith52.comprompt.view.EntryFoundListener;

abstract public class MonitoringTaskImpl implements MonitoringTask {

	protected List<MonitoringListener> monitoringListeners = new ArrayList<MonitoringListener>();
	protected List<EntryFoundListener> entryFoundListeners = new ArrayList<EntryFoundListener>();

	public void addMonitoringListener(MonitoringListener listener) {
		monitoringListeners.add(listener);
	}
	
	public void addEntryFoundListneer(EntryFoundListener listener) {
		entryFoundListeners.add(listener);
	}

}
