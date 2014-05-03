package szoftlab4;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GraphicObstacle extends Drawable {
	
	Image gemImage;
	Obstacle o;
	
	public GraphicObstacle(Obstacle o){
		this.o = o;
		z_index = 1;
		
		try {
			img = ImageIO.read(new File("icons\\obstacle.png"));

			if(o.getGem() != null){
				if(o.getGem() == ObstacleGem.orange)
					gemImage = ImageIO.read(new File("icons\\orange_gem.png"));
				else if(o.getGem() == ObstacleGem.yellow)
					gemImage = ImageIO.read(new File("icons\\yellow_gem.png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)toMouseCoords(o.getPosition()).x, (int)toMouseCoords(o.getPosition()).y, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)toMouseCoords(o.getPosition()).x, (int)toMouseCoords(o.getPosition()).y, null);
	}

}
