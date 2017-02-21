package monolith52.comprompt.view;

import monolith52.comprompt.animation.Animation;

public interface ViewStyle {
	public String getId();
	public void setCommentView(CommentView view);
	
	public int getX(RenderedEntry entry, int index, int size);
	public int getY(RenderedEntry entry, int index, int size);
	public Animation getSlideAnimation(RenderedEntry entry);
	public Animation getEntryAnimation(RenderedEntry entry);
}
