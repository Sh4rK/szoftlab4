package szoftlab4;

import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;

public class GraphicEnemy extends Drawable {
	
	protected Enemy e;
	
	public GraphicEnemy(Enemy e){
		this.e = e;
		z_index = 3;
		try{
			if(e.getEnemyType() == EnemyType.human)
				img = ImageIO.read(new File("icons/human.png"));
			else if(e.getEnemyType() == EnemyType.dwarf)
				img = ImageIO.read(new File("icons/dwarf.png"));
			else if(e.getEnemyType() == EnemyType.elf)
				img = ImageIO.read(new File("icons/elf.png"));
			else if(e.getEnemyType() == EnemyType.hobbit)
				img = ImageIO.read(new File("icons/hobbit.png"));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)Game.toMouseCoords(e.getPosition()).x - img.getWidth(null) / 2, (int)Game.toMouseCoords(e.getPosition()).y - img.getHeight(null) / 2, null);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicEnemy && ((GraphicEnemy) other).e.equals(this.e);
	}

}
