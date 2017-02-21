package monolith52.comprompt.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import monolith52.comprompt.ModelChangedListener;
import monolith52.comprompt.animation.Animation;
import monolith52.comprompt.util.ThreadUtil;

public class CommentView extends JPanel 
		implements Runnable, EntryFoundListener, ModelChangedListener<CommentViewModel> {
	private static final long serialVersionUID = 1L;
	int fps = 60;
	int padding		= 2;
	ViewStyle viewStyle;
	
	CommentViewModel model;
	Font font;
	Color fontColor;
	Color bgColor;

	protected boolean isRunnable = false;
	protected List<RenderedEntry> renderedEntries = new LinkedList<RenderedEntry>();
	protected List<Animation> slideAnimations = new LinkedList<Animation>();
	
	public CommentView(CommentViewModel model) {
		this.model = model;
		model.addChangeListener(this);
		updateChanges();
	}
	
	public void updateChanges() {
		font 		= model.getFont();
		fontColor 	= model.getFontColor();
		bgColor 	= model.getBgColor();
		model.getViewStyle().setCommentView(this); 
		viewStyle	= model.getViewStyle();
		rerenderAllEntries();
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
		synchronized (renderedEntries) {
			for (int i=renderedEntries.size()-1; i>=0; i--) {
				RenderedEntry re = renderedEntries.get(i);
				Animation ani = re.getAnimation();
				
				
				Color color = new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());
				g.setColor(color);
				synchronized (slideAnimations) {
					slideAnimations.forEach(slide -> g.translate(slide.getX(), slide.getY()));
					g.translate(ani.getX(), ani.getY());
					
					int x = viewStyle.getX(re, i, renderedEntries.size());
					int y = viewStyle.getY(re, i, renderedEntries.size());
//					g.drawString(comment.getText(), x, y);
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ani.getAlpha()));
					g.drawImage(re.getImage(), x, y, null);
					
					g.translate(-ani.getX(), -ani.getY());
					slideAnimations.forEach(slide -> g.translate(-slide.getX(), -slide.getY()));
				}
			}
		}
	}
	
	protected void process() {
		synchronized (renderedEntries) {
			renderedEntries.forEach(entry -> entry.getAnimation().step());
			renderedEntries.removeIf(entry -> entry.isGarbage());
		}
		synchronized (slideAnimations) {
			slideAnimations.forEach(slide -> slide.step());
			slideAnimations.removeIf(slide -> slide.isFinished());
		}

	}

	@Override
	public void run() {
		isRunnable = true;
		long frameinterval, lasttime, currenttime, sleeptime;
		while (isRunnable) {
			frameinterval = 1000 * 1000 * 1000 / fps;
			//　描画速度コントロール
			lasttime = System.nanoTime();
			process();
			repaint();
			
			currenttime = System.nanoTime();
			sleeptime = Math.max(1, lasttime - currenttime + frameinterval);
			ThreadUtil.sleep(sleeptime / 1000 / 1000, (int)sleeptime % 1000000);
			lasttime = currenttime;
		}
	}
	
	protected void rerenderAllEntries() {
		synchronized (renderedEntries) {
			renderedEntries.forEach(entry -> entry.getRenderer().run());
		}
	}

	@Override
	public void entriesFound(List<Entry> newEntries) {
		newEntries.forEach(entry -> {
			System.out.println("New comment found: " + entry.getText());
			RenderedEntry re = new RenderedEntry();
			re.setRenderer(() -> {
				re.setImage(EntryRenderer.render(entry.getText(), font, fontColor, padding));
			});
			re.getRenderer().run();
			re.setAnimation(viewStyle.getEntryAnimation(re));
			synchronized (renderedEntries) {
				renderedEntries.add(re);
			}
			synchronized (slideAnimations) {
				slideAnimations.add(viewStyle.getSlideAnimation(re));
			}
			
			ThreadUtil.sleep(100);
		});
	}
	
	public void reset() {
		synchronized (renderedEntries) {
			renderedEntries.clear();
		}
		synchronized (slideAnimations) {
			slideAnimations.clear();
		}
	}

	@Override
	public void modelChanged(CommentViewModel model) {
		this.model = model;
		updateChanges();
	}

}
