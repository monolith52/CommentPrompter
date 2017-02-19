package monolith52.comprompt.animation;

public interface Animation {
	public void step();
	public int getX();
	public int getY();
	public int getAlpha();
	public void setOnFinish(Runnable onFinish);
}
