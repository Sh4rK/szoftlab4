package szoftlab4;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Drawable implements Comparable<Drawable>{
	protected int z_index;
	protected Image img;
	
	public abstract void draw(Graphics g);

	public int compareTo(Drawable other) {
		return other.z_index - this.z_index;
	}
	
	public int getZIndex(){
		return z_index;
	}
}
