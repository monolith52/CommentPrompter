package monolith52.comprompt.animation;

public class Fadeout extends AnimationImpl {

	protected int lifetime;
	
	public Fadeout(int fps, int time, int lifetime) {
		super(fps, time);
		currentStep -= lifetime * fps / 1000;
	}
	
	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getAlpha() {
		return 0xFF - Math.min(0xFF, Math.max(0, currentStep) * 0xFF / maxStep());
	}

}
