package monolith52.comprompt.livetube;

public interface StreamingListener {
	public void streamingDetected(Streaming streaming);
	public void detectionFailed(String msg);
}
