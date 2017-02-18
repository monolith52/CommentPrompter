package monolith52.comprompt;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import monolith52.comprompt.livetube.CommentFoundListener;

public class CommentView extends JPanel implements Runnable, CommentFoundListener {
	private static final long serialVersionUID = 1L;
	double rad = 0.0d;
	int fps = 60;
	long frameinterval = 1000 * 1000 * 1000 / fps;
	
	protected boolean isRunnable = false;
	protected List<Comment> comments = new ArrayList<Comment>();
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
				
		g.setColor(Color.black);
		for (int i=0; i<comments.size(); i++) {
			Comment comment = comments.get(i);
			int x = (int) (Math.sin(rad + ((double)i * Math.PI * 2.0d / 20.0d)) * 50 + 50);
			int y = (comments.size()-i)*20;
			g.drawString(comment.getText(), x, (comments.size()-i)*20);
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
			rad += Math.PI / 100;
			
			currenttime = System.nanoTime();
			sleeptime = Math.max(1, lasttime - currenttime + frameinterval);
			try {Thread.sleep(sleeptime / 1000 / 1000, (int)sleeptime % 1000000);} catch (InterruptedException e) {}
			lasttime = currenttime;
		}
	}

	@Override
	public void commentFound(List<Comment> newComments) {
		newComments.forEach(comment -> System.out.println("New comment found: " + comment.getText()));
		comments.addAll(newComments);
	}

}
