package monolith52.comprompt;

import monolith52.comprompt.animation.Animation;

public class Comment {
	int number;
	String icon;
	String name;
	String datetime;
	String text;
	Animation animation;
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
