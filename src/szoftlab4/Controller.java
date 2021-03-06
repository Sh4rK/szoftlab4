package szoftlab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller {
	private Game game;
	private View view;
	/**
	 * Ez tárolja mi az elvégzendő művelet a térképen történő eseményeknél
	 */
	private MapMouseDelegate mapMouseEvent = null;
	/**
	 * A jelenleg lenyomott gomb
	 */
	private JButton activeButton = null;

	public Controller(Game game, View view) {
		this.game = game;
		this.view = view;
	}

	/**
	 * Kitörli a kiválasztott gombot.
	 * Beállítja a gombnak a kinézetét.
	 */
	private void nullActiveButton() {
		if (activeButton != null)
			activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		view.setPlacing(null);
		activeButton = null;
		mapMouseEvent = null;
	}

	public class MenuPanelMouseEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			nullActiveButton();
		}
	}


	/**
	 * Egy interfész a térkép események kezelésére.
	 * A térképen történő egéresemény során a térkép eseménykezelője egy ilyen objektum
	 * megfelelő metódusát hívja meg.
	 * Így könnyen lecserélhető, hogy hogyan viselkedjen.
	 */
	interface MapMouseDelegate {
		public void MapClicked(MouseEvent e);

		public void MapMoved(MouseEvent e);
	}

	/**
	 * A szünet gombra történő kattintás eseménykezelője
	 */
	public class pauseMouseEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JButton b = (JButton) e.getSource();

			if (game.gameState == szoftlab4.Game.State.RUNNING) {
				game.gameState = szoftlab4.Game.State.PAUSED;

				nullActiveButton();
				b.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			} else {
				game.gameState = szoftlab4.Game.State.RUNNING;
				b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			}
		}
	}

	/**
	 * A toronyépítés gombra történő kattintás eseménykezelője
	 */
	public class BuildTowerMouseEvent extends MouseAdapter implements MapMouseDelegate {
		Vector pos;

		public void mousePressed(MouseEvent e) {
			if (activeButton == e.getSource()) {
				nullActiveButton();
				return;
			}

			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

			mapMouseEvent = this;
			activeButton = (JButton) e.getSource();
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
			game.buildTower(Game.toGameCoords(pos));
		}
	}

	/**
	 * Az akadályépítés gombra történő kattintás eseménykezelője
	 */
	public class BuildObstacleMouseEvent extends MouseAdapter implements MapMouseDelegate {
		Vector pos;

		public void mousePressed(MouseEvent e) {
			if (activeButton == e.getSource()) {
				nullActiveButton();
				return;
			}

			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

			mapMouseEvent = this;
			activeButton = (JButton) e.getSource();
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
			game.buildObstacle(Game.toGameCoords(pos));
		}
	}

	/**
	 * A varázskő gombokra történő kattintás eseménykezelője
	 */
	public class EnchantMouseEvent extends MouseAdapter implements MapMouseDelegate {
		Vector pos;

		public void mousePressed(MouseEvent e) {
			if (activeButton == e.getSource()) {
				nullActiveButton();
				return;
			}
			if (activeButton != null)
				activeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

			mapMouseEvent = this;
			activeButton = (JButton) e.getSource();
			activeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			GraphicGem g = new GraphicGem(((GemButton) activeButton).getGemType());
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
			Gem g = ((GemButton) activeButton).getGemType();
			if (g instanceof TowerGem) {
				TowerGem tg = (TowerGem) g;
				game.addGem(Game.toGameCoords(pos), tg);
			} else {
				ObstacleGem tg = (ObstacleGem) g;
				game.addGem(Game.toGameCoords(pos), tg);
			}
		}
	}

	/**
	 * A térképre történő egéresemények eseménykezelője
	 * Az eseményeket átirányítja a mapMouseEvent megfelelő metódusára
	 * Így a működését a mapMouseEvent határozza meg
	 */
	public class MapMouseEvent extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (mapMouseEvent != null)
				mapMouseEvent.MapClicked(e);
			JPanel jp = (JPanel) e.getSource();
			jp.requestFocus();
			jp.requestFocusInWindow();
		}

		public void mouseMoved(MouseEvent e) {
			if (mapMouseEvent != null)
				mapMouseEvent.MapMoved(e);
		}
	}
}
