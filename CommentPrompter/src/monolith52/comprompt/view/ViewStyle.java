package monolith52.comprompt.view;

import monolith52.comprompt.Comment;
import monolith52.comprompt.animation.Animation;

public interface ViewStyle {
	public String getId();
	public void setCommentView(CommentView view);
	
	public int getX(Comment comment, int index, int size);
	public int getY(Comment comment, int index, int size);
	public Animation getSlideAnimation();
	public Animation getCommentAnimation(Comment comment);
}
