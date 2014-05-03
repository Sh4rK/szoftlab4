package szoftlab4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public class Controller {
	private Game game;
	private MapClickDelegate mapClick = null;
	private JButton activeButton = null;
	
	public Controller(Game game){
		this.game = game;
	}
	
	interface MapClickDelegate {
		public void MapClicked(MouseEvent e);
	}
	
	public class BuildTowerMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			game.buildTower(game.toGameCoords(pos));
		}
	}
	
	public class BuildObstacleMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			game.buildObstacle(game.toGameCoords(pos));
		}
	}
	
	public class EnchantMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			Gem g = ((GemButton)activeButton).getGemType();
			if (g instanceof TowerGem){
				TowerGem tg = (TowerGem)g;
				game.addGem(game.toGameCoords(pos), tg);
			}
			else {
				ObstacleGem tg = (ObstacleGem)g;
				game.addGem(game.toGameCoords(pos), tg);
			}
				
		}
	}
	
	public class MapMouseEvent extends MouseAdapter {
		public void mouseClicked(MouseEvent e){
			if (mapClick != null)
				mapClick.MapClicked(e);
			
			activeButton = null;
			mapClick = null;
		}
	}
}
