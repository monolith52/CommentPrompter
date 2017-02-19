package monolith52.comprompt.animation;

abstract public class AnimationImpl implements Animation{
	protected int fps;
	protected int time;
	protected int currentStep = 0;
	boolean isFinished = false;
	protected Runnable onFinish;
	
	public AnimationImpl(int fps, int time) {
		this.fps = fps;
		this.time = time;
	}

	@Override
	public void step() {
		if (isFinished) return;
		currentStep ++;
		if (currentStep >= maxStep()) {
			isFinished = true;
			onFinish.run();
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
}
