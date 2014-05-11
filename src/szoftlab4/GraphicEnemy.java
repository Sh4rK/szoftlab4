package szoftlab4;

import java.awt.Graphics;

/**
 * Az ellenségek kirajzolásáért felelős Drawable.
 */
public class GraphicEnemy extends Drawable {
	
	protected Enemy e;
	
	public GraphicEnemy(Enemy e){
		this.e = e;
		z_index = 3;

		if(e.getEnemyType() == EnemyType.human)
			img = Resources.HumanImage;
		else if(e.getEnemyType() == EnemyType.dwarf)
			img = Resources.DwarfImage;
		else if(e.getEnemyType() == EnemyType.elf)
			img = Resources.ElfImage;
		else if(e.getEnemyType() == EnemyType.hobbit)
			img = Resources.HobbitImage;
	}
	/**
	 * Kirajzolja az általa reprezentált ellenséget.
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)Game.toMouseCoords(e.getPosition()).x - img.getWidth(null) / 2, (int)Game.toMouseCoords(e.getPosition()).y - img.getHeight(null) / 2, null);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof GraphicEnemy && ((GraphicEnemy) other).e.equals(this.e);
	}

}
