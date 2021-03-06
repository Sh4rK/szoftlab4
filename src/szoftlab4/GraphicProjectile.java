package szoftlab4;

import java.awt.*;

/**
 * A lövedékek kirajzolásáért felelős Drawable.
 */
public class GraphicProjectile extends Drawable {

	protected Projectile p;

	/**
	 * Konstruktor mely hozzárendel egy projectile objektumot, és beállítja a kirajzolandó képet.
	 */
	public GraphicProjectile(Projectile p) {
		this.p = p;
		z_index = 4;

		if (p instanceof SplitterProjectile)
			img = Resources.SplitterProjectileImage;
		else
			img = Resources.ProjectileImage;
	}


	/**
	 * Kirajzolja a lövedéket az áttranszformált koordinátákra.
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int) Game.toMouseCoords(p.getPosition()).x - img.getWidth(null) / 2, (int) Game.toMouseCoords(p.getPosition()).y - img.getHeight(null) / 2, null);

	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicProjectile && ((GraphicProjectile) other).p.equals(this.p);
	}

}
