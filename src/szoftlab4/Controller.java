package szoftlab4;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class Controller {
	private Game game;
	private MapClickDelegate mapClick = null;
	private JButton activeButton = null;
	
	public Controller(Game game){
		this.game = game;
	}
	
	private void nullActiveButton(){
		if(activeButton != null)
			activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		activeButton = null;
		mapClick = null;
	}
	
	interface MapClickDelegate {
		public void MapClicked(MouseEvent e);
	}
	
	public class BuildTowerMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			if (game.buildTower(Game.toGameCoords(pos)))
				nullActiveButton();
		}
	}
	
	public class BuildObstacleMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			if (game.buildObstacle(Game.toGameCoords(pos)))
				nullActiveButton();
		}
	}
	
	public class EnchantMouseEvent extends MouseAdapter implements MapClickDelegate {
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			Gem g = ((GemButton)activeButton).getGemType();
			if (g instanceof TowerGem){
				TowerGem tg = (TowerGem)g;
				game.addGem(Game.toGameCoords(pos), tg);
			}
			else {
				ObstacleGem tg = (ObstacleGem)g;
				game.addGem(Game.toGameCoords(pos), tg);
			}
			
			nullActiveButton();
				
		}
	}
	
	public class MapMouseEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e){
			if (mapClick != null)
				mapClick.MapClicked(e);
		}
	}
}
