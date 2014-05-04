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
		
		try {
			img = ImageIO.read(new File("icons/background.png"));
			mountains = ImageIO.read(new File("icons/saurontower.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g2.drawImage(img, 0, 0, null);
		for (Waypoint wp : m.getWaypoints()) {
			g2.setTransform(new AffineTransform());
			Vector pos = Game.toMouseCoords(wp.getPosition());
			Vector size = Game.toMouseCoords(new Vector(4, 4)); // TODO
			g2.setColor(new Color(0x552200));
			g2.fillOval((int)pos.x-(int)(size.x/2), (int)pos.y-(int)(size.y/2), (int)size.x, (int)size.y);
			for (Waypoint w: wp.listNextWaypoints()) {
				g2.setTransform(new AffineTransform());
				g2.setColor(new Color(0x552200));
				Vector wpos = Game.toMouseCoords(w.getPosition());
				g2.rotate(Math.atan2(wpos.y - pos.y, wpos.x - pos.x), pos.x, pos.y);
				g2.fillRect((int)pos.x, (int)pos.y-(int)(size.y/2), (int)(pos.getDistance(wpos)), (int)size.y);
			}
		}
		g2.drawImage(mountains,0,0,null);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicMap && ((GraphicMap) other).m.equals(this.m);
	}

}
