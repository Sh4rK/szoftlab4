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
			img = ImageIO.read(new File("icons/obstacle.png"));

			if(o.getGem() != null){
				if(o.getGem() == ObstacleGem.orange)
					gemImage = ImageIO.read(new File("icons/orange_gem.png"));
				else if(o.getGem() == ObstacleGem.yellow)
					gemImage = ImageIO.read(new File("icons/yellow_gem.png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)Game.toMouseCoords(o.getPosition()).x - img.getWidth(null) / 2, (int)Game.toMouseCoords(o.getPosition()).y - img.getHeight(null) / 2, null);
		if(gemImage != null)
			g.drawImage(gemImage, (int)Game.toMouseCoords(o.getPosition()).x, (int)Game.toMouseCoords(o.getPosition()).y, null);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicObstacle && ((GraphicObstacle) other).o.equals(this.o);
	}

}
