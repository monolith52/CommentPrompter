package monolith52.comprompt.animation;

abstract public class AnimationImpl implements Animation{
	protected int fps;
	protected int time;
	protected int currentStep = 0;
	boolean finished = false;
	protected Runnable onFinish;
	
	public AnimationImpl(int fps, int time) {
		this.fps = fps;
		this.time = time;
	}

	@Override
	public void step() {
		if (finished) return;
		currentStep ++;
		if (currentStep >= maxStep()) {
			finished = true;
			if (onFinish != null) onFinish.run();
		}
	}
	
	public int maxStep() {
		return fps * time / 1000;
	}
	
	public int lastStep() {
		return Math.max(0, maxStep() - currentStep);
	}
	
	public void setOnFinish(Runnable onFinish) {
		this.onFinish = onFinish;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
