package monolith52.comprompt.view;

import java.awt.Image;

import monolith52.comprompt.animation.Animation;

public class Entry {
	Runnable renderer;
	Image image;
	Animation animation;
	boolean garbage = false;
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	public boolean isGarbage() {
		return garbage;
	}
	public void setGarbage(boolean garbage) {
		this.garbage = garbage;
	}
	public Runnable getRenderer() {
		return renderer;
	}
	public void setRenderer(Runnable renderer) {
		this.renderer = renderer;
	}
	
}
