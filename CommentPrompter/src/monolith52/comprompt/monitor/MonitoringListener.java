package monolith52.comprompt.monitor;

public interface MonitoringListener {
	public void monitoringStarted(String msg);
	public void monitoringFailed(String msg);
}
