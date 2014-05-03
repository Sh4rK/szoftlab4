package szoftlab4;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;

public class GraphicEnemy extends Drawable {
	
	protected Enemy e;
	
	public GraphicEnemy(Enemy e){
		this.e = e;
		
		if(e.getEnemyType() == EnemyType.human)
			img = ImageIO.read(new File("icons\human.png"));
		else if(e.getEnemyType() == EnemyType.dwarf)
			img = ImageIO.read(new File("icons\dwarf.png"));
		else if(e.getEnemyType() == EnemyType.elf)
			img = ImageIO.read(new File("icons\elf.png"));
		else if(e.getEnemyType() == EnemyType.hobbit)
			img = ImageIO.read(new File("icons\hobbit.png"));
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)e.getPosition().x, (int)e.getPosition().y, null);
	}

}
