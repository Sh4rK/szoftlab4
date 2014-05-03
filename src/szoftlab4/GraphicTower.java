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
		try {
			img = ImageIO.read(new File("icons\tower.png"));
			
			if(t.getGem() != null){
				if(t.getGem() == TowerGem.red)
					gemImage = ImageIO.read(new File("icons\\red_gem.png"));
				else if(t.getGem() == TowerGem.green)
					gemImage = ImageIO.read(new File("icons\\green_gem.png"));
				else if(t.getGem() == TowerGem.blue)
					gemImage = ImageIO.read(new File("icons\\blue_gem.png"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)t.getPosition().x, (int)t.getPosition().y, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)t.getPosition().x, (int)t.getPosition().y, null);
	}

}
