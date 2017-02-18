package monolith52.comprompt.animation;

abstract public class AnimationImpl implements Animation{
	protected int fps;
	protected int time;
	protected int currentStep = 0;
	
	public AnimationImpl(int fps, int time) {
		this.fps = fps;
		this.time = time;
	}

	@Override
	public void step() {
		currentStep ++;
	}
	
	public int maxStep() {
		return fps * time / 1000;
	}
	
	public int lastStep() {
		return Math.max(0, maxStep() - currentStep);
	}
}
