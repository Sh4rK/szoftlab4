package szoftlab4;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Ez az osztály fogja össze a többi osztályt.
 * A felhasználótól érkező parancsokat, eseményeket kezeli.
 * Tárolja a játék különböző elemeit:
 * Ellenségeket, tornyokat, akadályokat, lövedékeket.
 *
 * @author Tallér Bátor
 * @author Szabó Antal
 * @author Török Attila
 */
public class Game {
	public static final int FPS = 60;
	private Map map = null;
	private Mission mission = null;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Tower> towers = new ArrayList<Tower>();
	private int magic = 1500;
	View view;

	
	public Game(String mapName, String missionName) {
		try {
			map = new Map("maps/" + mapName + ".map");
			mission = new Mission("missions/" + mapName + "_" + missionName + ".mission", map);
			view = new View(this, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A program fő belépési pontja.
	 *
	 * @param args A parancssori paraméterek.
	 */
	public static void main(String[] args) {
		Window window = new Window();
		window.setResizable(false);
		window.setVisible(true);
		synchronized(window){
			try {
				window.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		window.runGame();
	}
	
	public void run(){
		while(true){
			//step();
			view.drawAll();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A játék logikáját egy lépéssel előrébb viszi.
	 * (Az ellenségek itt haladnak, a tornyok itt lőnek, a lövedékek ott repülnek, stb.)
	 */
	private boolean step() {
		slowEnemies();
		boolean rtn = moveEnemies();

		Enemy enemy = mission.getNextEnemy();

		if (enemy != null) {
			addEnemy(enemy);
		}

		moveProjectiles();
		towersFire();
		
		return rtn;
	}

	/**
	 * A lövedékek mozgatása. Ha az ellenség meghalt akkor kitörli
	 */
	private void moveProjectiles() {
		Iterator<Projectile> i = projectiles.iterator();
		while (i.hasNext()) {
		   Projectile p = i.next();
		   if (p.step()){
				i.remove();
				view.projectileExploded(p);
				if (!p.getTarget().isAlive()){
					removeEnemy(p.getTarget());
				}
		   }
		}
	}
	/**
	 * Kitöröl egy ellenséget. 
	 */
	private void removeEnemy(Enemy en){
		enemies.remove(en);
		view.enemyDied(en);
	}

	/**
	 * A tornyok támadását intéző metódus.
	 */
	private void towersFire() {
		for (Tower t : towers) {
			Projectile p = t.attack(enemies, this);
			if (p != null){
				projectiles.add(p);
				view.projectileAdded(p);
			}
		}
	}

	/**
	 * Az ellenségek mozgását intéző metódus.
	 */
	private boolean moveEnemies() {
		for (Enemy e : enemies) {
			if (e.move()){
				//ellenség nyer
				return true;
			}
		}
		return false;
	}

	/**
	 * Az ellenségek lassítását beállító metódus.
	 */
	private void slowEnemies() {
		for (Enemy e : enemies) {
			for (Obstacle o : obstacles) {
				if (e.getPosition().equals(o.getPosition(), 5))
					e.setSlowingFactor(o.getSlowingFactor(e));
				else
					e.setSlowingFactor(1);
			}
		}
	}

	/**
	 * @return A játékos rendelkezésére álló varázserő.
	 */
	public int getMagic() {
		return magic;
	}

	/**
	 * Egy torony megerősítése egy varázskővel.
	 *
	 * @param pos A pozíció, ahol az erősítendő épület található.
	 * @param gem A varázskő, amellyel az épület erősítendő.
	 */
	public void addGem(Vector pos, TowerGem gem) {
		Tower t = getCollidingTower(pos);

		if (t != null) {
			t.setGem(gem);
		}
	}

	/**
	 * Egy akadály megerősítése egy varázskővel.
	 *
	 * @param pos A pozíció, ahol az erősítendő épület található.
	 * @param gem A varázskő, amellyel az épület erősítendő.
	 */
	public void addGem(Vector pos, ObstacleGem gem) {
		Obstacle o = getCollidingObstacle(pos);

		if (o != null) {
			o.setGem(gem);
		}
	}

	/**
	 * Egy ellenség hozzáadása a játéktérhez.
	 *
	 * @param en A hozzáadandó ellenség.
	 */
	public void addEnemy(Enemy en) {
		enemies.add(en);
		view.enemyAdded(en);
	}

	/**
	 * A megadott helyre épít egy akadályt
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e akadályt építeni,
	 * illetve hogy nem ütközik-e már meglévő akadállyal.
	 *
	 * @param pos az akadály koordinátái
	 *            VÁLTOZÁS:
	 * @return Épített-e oda akadályt
	 */
	public boolean buildObstacle(Vector pos) {
		if (map.canBuildObstacle(pos) && !collidesWithObstacle(pos)) {
			Obstacle o = new Obstacle(pos);
			obstacles.add(o);
			view.obstacleAdded(o);
			
			return true;
		}

		return false;
	}

	/**
	 * A megadott helyre épít egy tornyot
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e tornyot építeni,
	 * illetve hogy nem ütközik-e már meglévő toronnyal.
	 *
	 * @param pos a torony koordinátái
	 *            VÁLTOZÁS:
	 * @return Épített-e oda tornyot
	 */
	public boolean buildTower(Vector pos) {
		if (map.canBuildTower(pos) && !collidesWithTower(pos)) {
			Tower t = new Tower(pos);
			towers.add(t);
			view.towerAdded(t);
			
			return true;
		}

		return false;
	}

	/**
	 * @return visszadja, hogy az adott pont ütközik-e egy akadállyal
	 */
	public boolean collidesWithObstacle(Vector pos) {
		for (Obstacle o : obstacles) {
			if (o.doesCollide(pos))
				return true;
		}

		return false;
	}

	/**
	 * @return visszaadja, hogy az adott pont ütközik-e egy toronnyal
	 */
	public boolean collidesWithTower(Vector pos) {
		for (Tower t : towers) {
			if (t.doesCollide(pos))
				return true;
		}

		return false;
	}

	/**
	 * @return Az akadály, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Obstacle getCollidingObstacle(Vector pos) {
		for (Obstacle o : obstacles) {
			if (o.doesCollide(pos))
				return o;
		}

		return null;
	}

	/**
	 * @return A torony, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Tower getCollidingTower(Vector pos) {
		for (Tower t : towers) {
			if (t.doesCollide(pos))
				return t;
		}

		return null;
	}

	/**
	 * Az ellenségek kilistázása.
	 */
	void listEnemies() {
		for (Enemy e : enemies) {
			System.out.println(String.format(Locale.ENGLISH, "%d %.1f (%.1f;%.1f)", e.getID(), e.getHealth(), e.getPosition().x, e.getPosition().y));
		}
	}

	/**
	 * A tornyok kilistázása.
	 */
	void listTowers() {
		for (Tower t : towers) {
			System.out.print(String.format(Locale.ENGLISH, "(%.1f;%.1f) ", t.getPosition().x, t.getPosition().y));

			TowerGem tg = t.getGem();

			if (tg == TowerGem.red) {
				System.out.println("red");
			} else if (tg == TowerGem.green) {
				System.out.println("green");
			} else if (tg == TowerGem.blue) {
				System.out.println("blue");
			} else {
				System.out.println("-");
			}
		}
	}

	/**
	 * Az akadályok kilistázása.
	 */
	void listObstacles() {
		for (Obstacle o : obstacles) {
			System.out.print(String.format(Locale.ENGLISH, "(%.1f;%.1f) ", o.getPosition().x, o.getPosition().y));

			ObstacleGem og = o.getGem();

			if (og == ObstacleGem.yellow) {
				System.out.println("yellow");
			} else if (og == ObstacleGem.orange) {
				System.out.println("orange");
			} else {
				System.out.println("-");
			}
		}
	}

	/**
	 * A lövedékek kilistázása.
	 */
	void listProjectiles() {
		for (Projectile p : projectiles) {
			System.out.println(String.format(Locale.ENGLISH, "(%.1f;%.1f) %d %b", p.getPosition().x, p.getPosition().y, p.target.getID(), p instanceof SplitterProjectile));
		}
	}
	
	/**
	 * Megkeres egy ellenséget az azonosítója alapján.
	 * 
	 * @param enemyID A keresett ellenség azonosítója.
	 * @return A keresett ellenség, vagy null, ha nincs találat.
	 */
	private Enemy getEnemyByID(int enemyID) {
		for (Enemy e : enemies) {
			if (e.getID() == enemyID) {
				return e;
			}
		}
		
		return null;
	}
	
	static private int pix = 10;
	static public Vector toGameCoords(Vector v){
		return new Vector(v.x/pix, v.y/pix);
	}
	
	static public Vector toMouseCoords(Vector v){
		return new Vector(v.x*pix, v.y*pix);
	}
}
