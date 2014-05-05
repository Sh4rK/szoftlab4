package szoftlab4;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GraphicMap extends Drawable {
	
	Map m;
	Image mountains;
	
	public GraphicMap(Map m){
		this.m = m;
		z_index = 0;

		img = Resources.BackgroundImage;
		mountains = Resources.MountainsImage;
	}
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform originalTransform = g2.getTransform();
		
		/* Turn on AA */
		if (Game.AA){
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
		
		g2.drawImage(img, 0, 0, null);
		
		g2.setColor(new Color(0x552200));
		
		for (Waypoint wp : m.getWaypoints()) {
			g2.setTransform(originalTransform);
			
			Vector pos = Game.toMouseCoords(wp.getPosition());
			Vector size = Game.toMouseCoords(new Vector(4, 4)); // TODO
			
			g2.fillOval((int)pos.x-(int)(size.x/2), (int)pos.y-(int)(size.y/2), (int)size.x, (int)size.y);
			
			for (Waypoint w: wp.listNextWaypoints()) {
				g2.setTransform(originalTransform);
				
				Vector wpos = Game.toMouseCoords(w.getPosition());
				g2.rotate(Math.atan2(wpos.y - pos.y, wpos.x - pos.x), pos.x, pos.y);
				g2.fillRect((int)pos.x, (int)pos.y-(int)(size.y/2), (int)(pos.getDistance(wpos)), (int)size.y);
			}
		}
		g2.drawImage(mountains,0,0,null);
		
		/* Turn off AA */
		if (Game.AA){
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		}
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicMap && ((GraphicMap) other).m.equals(this.m);
	}

}
