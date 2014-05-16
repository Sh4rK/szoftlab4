package szoftlab4;

import java.awt.*;

/**
 * Az akadályok kirajzolásáért felelős Drawable.
 */
public class GraphicObstacle extends Drawable {

	Image gemImage;
	Obstacle o;

	/**
	 * Konstruktor mely hozzárendel egy obstacle objektumot, és beállítja a háttérképeket.
	 */
	public GraphicObstacle(Obstacle o) {
		this.o = o;
		z_index = 1;
		img = Resources.ObstacleImage;
	}

	/**
	 * Kirajzolja átszámított koordinátákkal az akadályt, 
	 * majd ha van rajta varázskő, akkor azt is rárajzolja.
	 * Végül varázskőtől függően rajzol egy olyan színű 
	 * és olyan sugarú kört a torony köré, amilyen színü a varázskő,
	 * és amekkora az akadály hatótávolsága.
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int) Game.toMouseCoords(o.getPosition()).x - img.getWidth(null) / 2,
				(int) Game.toMouseCoords(o.getPosition()).y - img.getHeight(null) / 2, null);

		if (gemImage != null)
			g.drawImage(gemImage,
					(int) Game.toMouseCoords(o.getPosition()).x - gemImage.getWidth(null) / 2 + img.getWidth(null) / 2,
					(int) Game.toMouseCoords(o.getPosition()).y - gemImage.getHeight(null) / 2,
					img.getWidth(null) / 2,
					img.getHeight(null) / 2,
					null);


		int range = (int) Game.toMouseCoords(new Vector(o.getRange(), 0)).x;

		Color color = new Color(160, 160, 160, 128);

		if (o.getGem() == ObstacleGem.yellow)
			color = new Color(160, 160, 0, 128);

		if (o.getGem() == ObstacleGem.orange)
			color = new Color(128, 80, 0, 128);

		drawRangeCircle((Graphics2D) g, color, (int) Game.toMouseCoords(o.getPosition()).x, (int) Game.toMouseCoords(o.getPosition()).y, range);

	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicObstacle && ((GraphicObstacle) other).o.equals(this.o);
	}

	/**
	 * Beállítja az akadály varázskövének megfelelően a varázskő képét.
	 */
	public void setGem() {
		if (o.getGem() != null) {
			if (o.getGem() == ObstacleGem.orange)
				gemImage = Resources.OrangeGemImage;
			else if (o.getGem() == ObstacleGem.yellow)
				gemImage = Resources.YellowGemImage;
		}
	}

}
