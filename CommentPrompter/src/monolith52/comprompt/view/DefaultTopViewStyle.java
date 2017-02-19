package monolith52.comprompt.view;

import monolith52.comprompt.Comment;
import monolith52.comprompt.animation.Animation;
import monolith52.comprompt.animation.Easein;
import monolith52.comprompt.animation.Fadeout;

public class DefaultTopViewStyle extends ViewStyleImpl {

	public DefaultTopViewStyle(CommentView view) {
		super(view);
	}

	@Override
	public int getX(Comment comment, int index, int size) {
		return padding;
	}

	@Override
	public int getY(Comment comment, int index, int size) {
		return (size - index) * getUnitHeight();
	}

	@Override
	public Animation getSlideAnimation() {
		return new Easein(view.getFps(), 750, 0, -getUnitHeight());
	}

	@Override
	public Animation getCommentAnimation(Comment comment) {
		Animation easein = new Easein(view.getFps(), 750, 300, getUnitHeight());
		Animation fadeout = new Fadeout(view.getFps(), 1000, 10000);
		easein.setOnFinish(() -> comment.setAnimation(fadeout));
		fadeout.setOnFinish(() -> comment.setGarbage(true));
		return easein;
	}

}
