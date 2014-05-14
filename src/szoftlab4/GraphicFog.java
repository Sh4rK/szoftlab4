package szoftlab4;

import java.awt.*;

/**
 * A köd kirajzolásáért felelős Drawable.
 */
public class GraphicFog extends Drawable {

	public GraphicFog() {
		z_index = 5;
	}

	/**
	 * Kirajzolja a ködöt, mint egy teljes képernyős szürke átfedés.
	 */
	public void draw(Graphics g) {
		if (Fog.isSet()) {
			g.setColor(new Color(255, 255, 255, 128));
			g.fillRect(0, 0, 800, 600);
		}
	}
}
