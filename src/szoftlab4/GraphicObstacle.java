package szoftlab4;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;


public class GraphicObstacle extends Drawable {
	
	Image gemImage;
	Obstacle o;
	
	public GraphicObstacle(Obstacle o){
		this.o = o;
		
		img = ImageIO.read(new File(doc\images\ch11\icons\obstacle.png));
		if(o.getGem() != null){
			if(o.getGem() == ObstacleGem.orange)
				gemImage = ImageIO.read(new File(doc\images\ch11\icons\orange_gem.png));
			else if(o.getGem() == ObstacleGem.yellow)
				gemImage = ImageIO.read(new File(doc\images\ch11\icons\yellow_gem.png));
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)o.getPosition().x, (int)o.getPosition().y, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)o.getPosition().x, (int)o.getPosition().y, null);
	}

}
