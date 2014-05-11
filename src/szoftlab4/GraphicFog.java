package szoftlab4;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Köd kirajzolásáért felelős osztály.
 * @author Adam
 *
 */
public class GraphicFog extends Drawable {
	
	public GraphicFog(){
		z_index = 5;
	}
	/**
	 * Az egész játéktérre ráhúz egy félig áttetsző szürke réteget.
	 */
	public void draw(Graphics g) {
		if (Fog.isSet()){
			g.setColor(new Color(255, 255, 255, 128));
			g.fillRect(0, 0, 800, 600);
		}
	}
}
