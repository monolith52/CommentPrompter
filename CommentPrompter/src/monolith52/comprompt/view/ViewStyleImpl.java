package monolith52.comprompt.view;

abstract public class ViewStyleImpl implements ViewStyle {
	
	CommentView view;
	int padding = 10;
	
	public void setCommentView(CommentView view) {
		this.view = view;
	}
	
	protected int getUnitHeight() {
		return view.getFont().getSize() + padding;
	}

}
