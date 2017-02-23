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
	
	public static BufferedImage render(String str, Font font, Color color, int padding, boolean antialias) {
		Object antialiasHint = antialias ?
				RenderingHints.VALUE_ANTIALIAS_ON :
				RenderingHints.VALUE_ANTIALIAS_OFF;
		FontRenderContext frc = new FontRenderContext(null, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT , 
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		
		Rectangle2D rect = font.getStringBounds(str, frc);
		BufferedImage image = createTransparentImage(
				Math.min((int)rect.getWidth() + padding * 2, getDisplayWidth()),
				(int)rect.getHeight());
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasHint);
		g2d.setColor(color);
		g2d.setFont(font);
		g2d.drawString(str, (float)padding, (float)(- rect.getY()));
		
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
