package monolith52.comprompt.view;

import monolith52.comprompt.animation.Animation;

public interface ViewStyle {
	public String getId();
	public void setCommentView(CommentView view);
	
	public int getX(Entry entry, int index, int size);
	public int getY(Entry entry, int index, int size);
	public Animation getSlideAnimation(Entry entry);
	public Animation getEntryAnimation(Entry entry);
}
