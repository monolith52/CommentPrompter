package monolith52.comprompt.view;

import monolith52.comprompt.animation.Animation;
import monolith52.comprompt.animation.Easein;
import monolith52.comprompt.animation.Fadeout;

public class DefaultTopViewStyle extends ViewStyleImpl {

	public final static String ID = "上から下へ";
	@Override
	public String getId() {
		return ID;
	}

	@Override
	public int getX(RenderedEntry entry, int index, int size) {
		return padding;
	}

	@Override
	public int getY(RenderedEntry entry, int index, int size) {
		return (size - index - 1) * entry.getImage().getHeight(null) + padding;
	}

	@Override
	public Animation getSlideAnimation(RenderedEntry entry) {
		return new Easein(view.getFps(), 750, 0, - entry.getImage().getHeight(null));
	}

	@Override
	public Animation getEntryAnimation(RenderedEntry entry) {
		Animation easein = new Easein(view.getFps(), 750, 300, entry.getImage().getHeight(null));
		Animation fadeout = new Fadeout(view.getFps(), 1000, 10000);
		easein.setOnFinish(() -> entry.setAnimation(fadeout));
		fadeout.setOnFinish(() -> entry.setGarbage(true));
		return easein;
	}

}
