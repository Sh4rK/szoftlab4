package szoftlab4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public void run(){
		while(true){
			step();
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
		magic += en.getEnemyType().magic;
		enemies.remove(en);
		view.enemyDied(en);
	}

	/**
	 * A tornyok támadását intéző metódus.
	 */
	private void towersFire() {
		synchronized(towers){
			for (Tower t : towers) {
				Projectile p = t.attack(enemies, this);
				if (p != null){
					projectiles.add(p);
					view.projectileAdded(p);
				}
			}
		}
	}

	/**
	 * Az ellenségek mozgását intéző metódus.
	 */
	private boolean moveEnemies() {
		synchronized(enemies){
			for (Enemy e : enemies) {
				if (e.move()){
					//ellenség nyer
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Az ellenségek lassítását beállító metódus.
	 */
	private void slowEnemies() {
		synchronized(enemies){
			synchronized(obstacles){
				for (Enemy e : enemies) {
					for (Obstacle o : obstacles) {
						if (e.getPosition().equals(o.getPosition(), 5))
							e.setSlowingFactor(o.getSlowingFactor(e));
						else
							e.setSlowingFactor(1);
					}
				}
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
			view.towerEnchanted(t);
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
			view.obstacleEnchanted(o);
		}
	}

	/**
	 * Egy ellenség hozzáadása a játéktérhez.
	 *
	 * @param en A hozzáadandó ellenség.
	 */
	public void addEnemy(Enemy en) {
		synchronized(enemies){
			enemies.add(en);
		}
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
		if (map.canBuildObstacle(pos) && !collidesWithObstacle(pos) && magic > Obstacle.cost) {
			Obstacle o = new Obstacle(pos);
			synchronized(obstacles){
				obstacles.add(o);
			}
			view.obstacleAdded(o);
			magic -= Obstacle.cost;
			
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
	 * @return Épített-e oda tornyot
	 */
	public boolean buildTower(Vector pos) {
		if (map.canBuildTower(pos) && !collidesWithTower(pos) && magic > Tower.cost) {
			Tower t = new Tower(pos);
			synchronized(towers){
				towers.add(t);
			}
			view.towerAdded(t);
			magic -= Tower.cost;
			
			return true;
		}

		return false;
	}

	/**
	 * @return visszadja, hogy az adott pont ütközik-e egy akadállyal
	 */
	public boolean collidesWithObstacle(Vector pos) {
		synchronized(obstacles){
			for (Obstacle o : obstacles) {
				if (o.doesCollide(pos))
					return true;
			}
		}

		return false;
	}

	/**
	 * @return visszaadja, hogy az adott pont ütközik-e egy toronnyal
	 */
	public boolean collidesWithTower(Vector pos) {
		synchronized(towers){
			for (Tower t : towers) {
				if (t.doesCollide(pos))
					return true;
			}
		}

		return false;
	}

	/**
	 * @return Az akadály, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Obstacle getCollidingObstacle(Vector pos) {
		synchronized(obstacles){
			for (Obstacle o : obstacles) {
				if (o.doesCollide(pos))
					return o;
			}
		}

		return null;
	}

	/**
	 * @return A torony, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Tower getCollidingTower(Vector pos) {
		synchronized(towers){
			for (Tower t : towers) {
				if (t.doesCollide(pos))
					return t;
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
