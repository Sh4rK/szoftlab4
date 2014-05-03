package szoftlab4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	
	class BuildTowerClickEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			game.buildTower(pos);
		}
	}
	
	class BuildObstacleClickEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			game.buildObstacle(pos);
		}
	}
	
	class EnchantClickEvent extends MouseAdapter implements MapClickDelegate {
		public void mouseClicked(MouseEvent e){
			mapClick = this;
			activeButton = (JButton)e.getSource();
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			Gem g = ((GemButton)activeButton).getGemType();
			if (g instanceof TowerGem){
				TowerGem tg = (TowerGem)g;
				game.addGem(pos, tg);
			}
			else {
				ObstacleGem tg = (ObstacleGem)g;
				game.addGem(pos, tg);
			}
				
		}
	}
	
	class MapClickEvent extends MouseAdapter {
		public void mouseClicked(MouseEvent e){
			if (mapClick != null)
				mapClick.MapClicked(e);
			
			activeButton = null;
			mapClick = null;
		}
	}
}
