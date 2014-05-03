package szoftlab4;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

public abstract class Drawable implements Comparable<Drawable>{
	protected int z_index;
	protected Image img;
	
	public abstract void draw(Graphics g);
	
	public int compareTo(Drawable arg0) {
		if(this.z_index < arg0.z_index)
			return 1;
		else
			return 0;
	}
	public int getZIndex(){
		return z_index;
	}
}
