package monolith52.comprompt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import monolith52.comprompt.animation.Animation;
import monolith52.comprompt.animation.Easein;
import monolith52.comprompt.config.Configure;
import monolith52.comprompt.config.Configure.Type;
import monolith52.comprompt.config.ConfigureChangedListener;
import monolith52.comprompt.livetube.CommentFoundListener;

public class CommentView extends JPanel implements Runnable, CommentFoundListener, ConfigureChangedListener {
	private static final long serialVersionUID = 1L;
	double rad = 0.0d;
	int fps = 60;
	long frameinterval = 1000 * 1000 * 1000 / fps;
	Font font = Configure.DEFAULT_FONT;	// デフォルトフォント
	
	protected boolean isRunnable = false;
	protected List<Comment> comments = Collections.synchronizedList(new ArrayList<Comment>());
	
	@Override
	protected void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setFont(font);
		g.setColor(Color.black);
		for (int i=Math.max(0, comments.size() - 10); i<comments.size(); i++) {
			Comment comment = comments.get(i);
			Animation ani = comment.getAnimation();
			Color color = new Color(0, 0, 0, ani.getAlpha());
			
			g.setColor(color);
			int x = 10 + ani.getX();
			int y = (comments.size()-i)*font.getSize() + ani.getY();
			g.drawString(comment.getText(), x, y);
		}
	}

	@Override
	public void run() {
		isRunnable = true;
		long lasttime, currenttime;
		long sleeptime = 0;
		while (isRunnable) {
			//　描画速度コントロール
			lasttime = System.nanoTime();
			repaint();
			comments.forEach(comment -> comment.getAnimation().step());
			
			currenttime = System.nanoTime();
			sleeptime = Math.max(1, lasttime - currenttime + frameinterval);
			try {Thread.sleep(sleeptime / 1000 / 1000, (int)sleeptime % 1000000);} catch (InterruptedException e) {}
			lasttime = currenttime;
		}
	}

	@Override
	public void commentFound(List<Comment> newComments) {
		newComments.forEach(comment -> System.out.println("New comment found: " + comment.getText()));
		newComments.forEach(comment -> comment.setAnimation(new Easein(fps, 1000, 300)));
		comments.addAll(newComments);
	}

	@Override
	public void configureChaqnged(Configure config, Type type) {
		switch (type) {
		case FONT: 
			font = config.getFont();
			break;
		}
	}

}
