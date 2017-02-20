package monolith52.comprompt.view;

abstract public class ViewStyleImpl implements ViewStyle {
	
	CommentView view;
	int padding = 5;
	
	public void setCommentView(CommentView view) {
		this.view = view;
	}

}
