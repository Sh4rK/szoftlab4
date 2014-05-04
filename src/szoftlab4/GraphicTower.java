package szoftlab4;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;


public class GraphicTower extends Drawable {
	
	protected Tower t;
	Image gemImage;
	
	public GraphicTower(Tower t){
		this.t = t;
		z_index = 2;
		try {
			img = ImageIO.read(new File("icons/tower.png"));
			
			if(t.getGem() != null){
				if(t.getGem() == TowerGem.red)
					gemImage = ImageIO.read(new File("icons/red_gem.png"));
				else if(t.getGem() == TowerGem.green)
					gemImage = ImageIO.read(new File("icons/green_gem.png"));
				else if(t.getGem() == TowerGem.blue)
					gemImage = ImageIO.read(new File("icons/blue_gem.png"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)Game.toMouseCoords(t.getPosition()).x - img.getWidth(null) / 2, (int)Game.toMouseCoords(t.getPosition()).y - img.getHeight(null) / 2, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)Game.toMouseCoords(t.getPosition()).x, (int)Game.toMouseCoords(t.getPosition()).y, null);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicEnemy && ((GraphicTower) other).t.equals(this.t);
	}
}
