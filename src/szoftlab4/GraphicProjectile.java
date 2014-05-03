package szoftlab4;

import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;


public class GraphicProjectile extends Drawable {
	
	protected Projectile p;
	
	public GraphicProjectile(Projectile p){
		this.p = p;
		z_index = 4;
		try {
			if(p instanceof SplitterProjectile)
				img = ImageIO.read(new File("icons\\splitter_projectile.png"));
			else
				img = ImageIO.read(new File("icons\\projectile.png"));
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)toMouseCoords(p.getPosition()).x, (int)toMouseCoords(p.getPosition()).y, null);

	}

}
