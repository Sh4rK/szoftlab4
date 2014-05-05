package szoftlab4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;


public class GraphicGem extends Drawable {
	public Vector pos;

	public GraphicGem(Gem g) {
		z_index = 2;
		try {
			if (g == TowerGem.red)
				img = ImageIO.read(new File("icons/red_gem.png"));
			else if (g == TowerGem.green)
				img = ImageIO.read(new File("icons/green_gem.png"));
			else if (g == TowerGem.blue)
				img = ImageIO.read(new File("icons/blue_gem.png"));
			else if (g == ObstacleGem.orange)
				img = ImageIO.read(new File("icons/orange_gem.png"));
			else if (g == ObstacleGem.yellow)
				img = ImageIO.read(new File("icons/yellow_gem.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
