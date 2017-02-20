package monolith52.comprompt.view;

import java.awt.Image;

import monolith52.comprompt.animation.Animation;

public class Entry {
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
}