package monolith52.comprompt.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import monolith52.comprompt.Comment;
import monolith52.comprompt.ModelChangedListener;
import monolith52.comprompt.animation.Animation;
import monolith52.comprompt.animation.Easein;
import monolith52.comprompt.animation.Fadeout;
import monolith52.comprompt.livetube.CommentFoundListener;
import monolith52.comprompt.util.ThreadUtil;

public class CommentView extends JPanel 
		implements Runnable, CommentFoundListener, ModelChangedListener<CommentViewModel> {
	private static final long serialVersionUID = 1L;
	double rad = 0.0d;
	int fps = 60;
	long frameinterval = 1000 * 1000 * 1000 / fps;
	int padding		= 5;
	ViewStyle viewStyle;
	
	CommentViewModel model;
	Font font;
	Color fontColor;
	Color bgColor;

	protected boolean isRunnable = false;
	protected List<Comment> comments = Collections.synchronizedList(new LinkedList<Comment>());
	protected List<Animation> slideAnimations = Collections.synchronizedList(new LinkedList<Animation>());
	
	public CommentView(CommentViewModel model) {
		this.model = model;
		model.addChangeListener(this);
		updateChanges();
		viewStyle = new DefaultBottomViewStyle(this);
	}
	
	public void updateChanges() {
		font 		= model.getFont();
		fontColor 	= model.getFontColor();
		bgColor 	= model.getBgColor();
	}
	
	public Font getFont() {
		return font;
	}
	
	public int getFps() {
		return fps;
	}

	@Override
	protected void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(bgColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setFont(font);
		synchronized (comments) {
			for (int i=comments.size()-1; i>=0; i--) {
				Comment comment = comments.get(i);
				Animation ani = comment.getAnimation();
				Color color = new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), ani.getAlpha());
				
				g.setColor(color);
				int x = viewStyle.getX(comment, i, comments.size());
				int y = viewStyle.getY(comment, i, comments.size());
				
				synchronized (slideAnimations) {
					slideAnimations.forEach(slide -> g.translate(slide.getX(), slide.getY()));
					g.translate(ani.getX(), ani.getY());
					g.drawString(comment.getText(), x, y);
					g.translate(-ani.getX(), -ani.getY());
					slideAnimations.forEach(slide -> g.translate(-slide.getX(), -slide.getY()));
				}
			}
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
			synchronized (comments) {
				comments.forEach(comment -> comment.getAnimation().step());
				comments.removeIf(comment -> comment.isGarbage());
			}
			synchronized (slideAnimations) {
				slideAnimations.forEach(slide -> slide.step());
				slideAnimations.removeIf(slide -> slide.isFinished());
			}
			repaint();
			
			currenttime = System.nanoTime();
			sleeptime = Math.max(1, lasttime - currenttime + frameinterval);
			ThreadUtil.sleep(sleeptime / 1000 / 1000, (int)sleeptime % 1000000);
			lasttime = currenttime;
		}
	}

	@Override
	public void commentFound(List<Comment> newComments) {
		int unitDistance = (font.getSize() + 10);
//		int totalDistance = unitDistance * newComments.size();
		newComments.forEach(comment -> {
			System.out.println("New comment found: " + comment.getText());
			comment.setAnimation(viewStyle.getCommentAnimation(comment));
			synchronized (comments) {
				comments.add(comment);
			}
			synchronized (slideAnimations) {
				slideAnimations.add(viewStyle.getSlideAnimation());
			}
			
			ThreadUtil.sleep(100);
		});
	}
	
	public void reset() {
		synchronized (comments) {
			comments.clear();
		}
	}

	@Override
	public void modelChanged(CommentViewModel model) {
		this.model = model;
		updateChanges();
	}

}
