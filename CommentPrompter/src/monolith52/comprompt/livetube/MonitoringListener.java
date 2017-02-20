package monolith52.comprompt.livetube;

public interface MonitoringListener {
	public void streamingDetected(Streaming streaming);
	public void detectionFailed(String msg);
	public void monitoringFailed(String msg);
}
