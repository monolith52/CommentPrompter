package monolith52.comprompt.animation;

public class Easein extends AnimationImpl {

	protected int dx, dy;
	
	public Easein(int fps, int time, int dx, int dy) {
		super(fps, time);
		this.dx = dx;
		this.dy = dy;
	}
	
	public float getCurrent() {
		float c = (float)lastStep() / maxStep();
		return c * c * c;
	}
	
	@Override
	public int getX() {
		return (int)(dx * getCurrent());
	}

	@Override
	public int getY() {
		return (int)(dy * getCurrent());
	}

	@Override
	public int getAlpha() {
		return Math.min(0xFF, currentStep * 0xFF / maxStep());
	}
}
