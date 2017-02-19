package monolith52.comprompt.animation;

public class Easein extends AnimationImpl {

	protected int length;
	
	public Easein(int fps, int time, int length) {
		super(fps, time);
		this.length = length;
	}
	
	public float getCurrent() {
		float c = (float)lastStep() / maxStep();
		return c * c * c;
	}
	
	@Override
	public int getX() {
		return (int)(length * getCurrent());
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getAlpha() {
		return Math.min(0xFF, currentStep * 0xFF / maxStep());
	}
}
