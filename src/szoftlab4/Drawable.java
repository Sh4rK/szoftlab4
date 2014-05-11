package szoftlab4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

/**
 * A játéktérre kirajzolható objektumok közös absztrakt ősosztálya.
 */
public abstract class Drawable implements Comparable<Drawable>{
	/** Megadja a kirajzolási sorrendet*/
	protected int z_index;
	/** A kirajzolt kép*/
	protected Image img;
	
	/**
	 * A kirajzolást végző absztrakt metódus, amelyet a leszármazottaknak felül kell definiálniuk.
	 * 
	 * @param g A Graphics példány, amire a leszámazottnak rajzolnia kell.
	 */
	public abstract void draw(Graphics g);

	/**
	 * Két Drawable példányt hasonlít össze a z-indexük alapján.
	 */
	public int compareTo(Drawable other) {
		return other.z_index - this.z_index;
	}
	
	/**
	 * Lekérdezi az objektum z-indexét.
	 * 
	 * @return A Drawable z-indexe.
	 */
	public int getZIndex(){
		return z_index;
	}
	
	/**
	 * Statikus segédmetódus az épületek hatósugarát jelző kör kirajzolásához.
	 * 
	 * @param g A Graphics példány, amire rajzolunk.
	 * @param color A rajzolandó kör színe.
	 * @param x A kör középpontjának vízszintes koordinátája. 
	 * @param y A kör középpontjának függőleges koordinátája.
	 * @param radius A kör sugara.
	 */
	public static void drawRangeCircle(Graphics2D g, Color color, int x, int y, int radius)
	{
		if (Game.AA){
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
				
		g.setStroke( new BasicStroke(4) );

		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 80);
		g.setColor(color);
		
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
		
		g.setColor(new Color(0, 0, 0, 70));
		
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
