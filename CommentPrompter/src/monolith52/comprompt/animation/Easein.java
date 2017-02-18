package monolith52.comprompt.animation;

public class Easein extends AnimationImpl {

	protected int length;
	protected float easerate;
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAlpha() {
		// TODO Auto-generated method stub
		return Math.min(0xFF, currentStep * 0xFF / maxStep());
	}
}
