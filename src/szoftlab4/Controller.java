package szoftlab4;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class Controller {
	private Game game;
	private View view;
	private MapClickDelegate mapClick = null;
	private JButton activeButton = null;
	
	public Controller(Game game, View view){
		this.game = game;
		this.view = view;
	}
	
	private void nullActiveButton(){
		if(activeButton != null)
			activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		view.setPlacing(null);
		activeButton = null;
		mapClick = null;
	}
	
	interface MapClickDelegate {
		public void MapClicked(MouseEvent e);
		public void MapMoved(MouseEvent e);
	}
	
	public class pauseMouseEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e){
			if (game.gameState == szoftlab4.Game.State.RUNNING)
				game.gameState = szoftlab4.Game.State.PAUSED;
			else
				game.gameState = szoftlab4.Game.State.RUNNING;
		}
	}

	public class BuildTowerMouseEvent extends MouseAdapter implements MapClickDelegate {
		Vector pos;
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			Tower t = new Tower(new Vector(-100, -100));
			pos = t.getPosition();
			view.setPlacing(new GraphicTower(t));
		}

		public void MapMoved(MouseEvent e) {
			Vector mpos = Game.toGameCoords(new Vector(e.getX(), e.getY()));
			pos.x = mpos.x;
			pos.y = mpos.y;
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			if (game.buildTower(Game.toGameCoords(pos)))
				nullActiveButton();
		}
	}
	
	public class BuildObstacleMouseEvent extends MouseAdapter implements MapClickDelegate {
		Vector pos;
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			Obstacle o = new Obstacle(new Vector(-100, -100));
			pos = o.getPosition();
			view.setPlacing(new GraphicObstacle(o));
		}

		public void MapMoved(MouseEvent e) {
			Vector mpos = Game.toGameCoords(new Vector(e.getX(), e.getY()));
			pos.x = mpos.x;
			pos.y = mpos.y;
		}

		public void MapClicked(MouseEvent e) {
			Vector pos = new Vector(e.getX(), e.getY());
			if (game.buildObstacle(Game.toGameCoords(pos)))
				nullActiveButton();
		}
	}
	
	public class EnchantMouseEvent extends MouseAdapter implements MapClickDelegate {
		Vector pos;
		public void mousePressed(MouseEvent e){
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			mapClick = this;
			activeButton = (JButton)e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			GraphicGem g = new GraphicGem(((GemButton)activeButton).getGemType());
			pos = g.getPosition();
			view.setPlacing(g);
		}

		public void MapMoved(MouseEvent e) {
			Vector mpos = Game.toGameCoords(new Vector(e.getX(), e.getY()));
			pos.x = mpos.x;
			pos.y = mpos.y;
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

		public void mouseMoved(MouseEvent e) {
			if (mapClick != null)
				mapClick.MapMoved(e);
		}
	}
}
