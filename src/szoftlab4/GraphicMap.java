package szoftlab4;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GraphicMap extends Drawable {
	
	Map m;
	Image mountains;
	
	public GraphicMap(Map m){
		this.m = m;
		
		try {
			img = ImageIO.read(new File("icons\\background.png"));
			mountains = ImageIO.read(new File("icons\\saurontower.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, null);
		
		int i=0;
		Waypoint way = m.getWaypointByID(i++);
		while(way != null){
			for(Waypoint w: way.listNextWaypoints()){
			//hurkát kéne rajzolni
			}
			way = m.getWaypointByID(i++);
		}
		g.drawImage(mountains,0,0,null);
	}

}
