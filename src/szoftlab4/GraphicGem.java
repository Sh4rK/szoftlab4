package szoftlab4;

import java.awt.*;


public class GraphicGem extends Drawable {
	public Vector pos;

	public GraphicGem(Gem g) {
		z_index = 2;

		if (g == TowerGem.red)
			img = Resources.RedGemImage;
		else if (g == TowerGem.green)
			img = Resources.GreenGemImage;
		else if (g == TowerGem.blue)
			img = Resources.BlueGemImage;
		else if (g == ObstacleGem.orange)
			img = Resources.OrangeGemImage;
		else if (g == ObstacleGem.yellow)
			img = Resources.YellowGemImage;

		pos = new Vector(-100, -100);
	}

	@Override
	public void draw(Graphics g) {
		Vector mpos = Game.toMouseCoords(pos);
		g.drawImage(img, (int) mpos.x - img.getWidth(null) / 4,
				(int) mpos.y - img.getHeight(null) / 4,
				img.getWidth(null) / 2,
				img.getHeight(null) / 2,
				null);
	}

	public Vector getPosition() {
		return pos;
	}
}
