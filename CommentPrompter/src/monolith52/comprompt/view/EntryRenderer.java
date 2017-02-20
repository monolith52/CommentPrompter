package monolith52.comprompt.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class EntryRenderer {
	
	public static int getDisplayWidth() {
		return (int)GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
	}
	
	public static BufferedImage render(String str, Font font, Color color, int padding) {
		BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D dg = dummy.createGraphics();
		dg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontRenderContext frc = dg.getFontMetrics(font).getFontRenderContext();
		
		Rectangle2D rect = font.getStringBounds(str, frc);
		BufferedImage image = createTransparentImage(
				Math.min((int)rect.getWidth() + padding * 2, getDisplayWidth()),
				(int)rect.getHeight() + padding * 2);
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.setFont(font);
		g2d.drawString(str, (float)padding, (float)(padding - rect.getY()));
		
		dg.dispose();
		g2d.dispose();
		return image;
	}
	
	private static BufferedImage createTransparentImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				image.setRGB(x, y, 0);
			}
		}
		return image;
	}
}
