package szoftlab4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


public class GraphicTower extends Drawable {
	
	protected Tower t;
	Image gemImage;
	
	public GraphicTower(Tower t){
		this.t = t;
		z_index = 2;
		img = Resources.TowerImage;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)Game.toMouseCoords(t.getPosition()).x - img.getWidth(null) / 2,
				(int)Game.toMouseCoords(t.getPosition()).y - img.getHeight(null) / 2, null);
		
		if(gemImage != null)
			g.drawImage(gemImage,
					(int)Game.toMouseCoords(t.getPosition()).x - gemImage.getWidth(null)  / 2  + img.getWidth(null) / 2,
					(int)Game.toMouseCoords(t.getPosition()).y - gemImage.getHeight(null) / 2,
					(int)img.getWidth(null) / 2,
					(int)img.getHeight(null) / 2,
					null);
		
		int range = (int)Game.toMouseCoords(new Vector(t.getRange(), 0)).x;
		
		Color color = new Color(160, 160, 160, 128);
		
		if (t.getGem() == TowerGem.red)
			color = new Color(255, 0, 0, 128);
		
		if (t.getGem() == TowerGem.green)
			color = new Color(0, 255, 0, 128);
		
		if (t.getGem() == TowerGem.blue)
			color = new Color(0, 0, 255, 128);
		
		drawRangeCircle((Graphics2D)g, color, (int)Game.toMouseCoords(t.getPosition()).x, (int)Game.toMouseCoords(t.getPosition()).y, range);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicTower && ((GraphicTower) other).t.equals(this.t);
	}
	public void setGem(){
		if(t.getGem() != null){
			if(t.getGem() == TowerGem.red)
				gemImage = Resources.RedGemImage;
			else if(t.getGem() == TowerGem.green)
				gemImage = Resources.GreenGemImage;
			else if(t.getGem() == TowerGem.blue)
				gemImage = Resources.BlueGemImage;
		}
	}
}
