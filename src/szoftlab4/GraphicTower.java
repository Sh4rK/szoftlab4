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
		img = ImageIO.read(new File(doc\images\ch11\icons\tower.png));
		
		if(t.getGem() != null){
			if(t.getGem() == TowerGem.red)
				gemImage = ImageIO.read(new File(doc\images\ch11\icons\red_gem.png));
			else if(t.getGem() == TowerGem.green)
				gemImage = ImageIO.read(new File(doc\images\ch11\icons\green_gem.png));
			else if(t.getGem() == TowerGem.blue)
				gemImage = ImageIO.read(new File(doc\images\ch11\icons\blue_gem.png));
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)t.getPosition().x, (int)t.getPosition().y, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)t.getPosition().x, (int)t.getPosition().y, null);
	}

}
