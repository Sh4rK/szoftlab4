package szoftlab4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public abstract class Drawable implements Comparable<Drawable>{
	protected int z_index;
	protected Image img;
	
	public abstract void draw(Graphics g);

	public int compareTo(Drawable other) {
		return other.z_index - this.z_index;
	}
	
	public int getZIndex(){
		return z_index;
	}
	
	public static void drawRangeCircle(Graphics2D g, Color color, int x, int y, int radius)
	{
		if (Game.AA){
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
				
		g.setStroke( new BasicStroke(4) );
		
		g.setColor(color);
		
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
		
		g.setColor(new Color(0, 0, 0, 128));
		
		g.setStroke(new BasicStroke(2));
		radius += 2;
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
		radius -= 4;
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
		
		if (Game.AA){
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		}
		
	}
}
