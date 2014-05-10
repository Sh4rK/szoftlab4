package szoftlab4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	private int magic = 15000;
	/** Antialiasing bekapcsolása */
	public static boolean AA = true;
	/** FPS számláló megjelenítésének bekapcsolása */
	private boolean countFPS = true;
	/** Ez tárolja a tényleges FPS-t */
	public double realFPS;
	/* debuggoláshoz */
	private double speed = 1.0;
	
	private View view;
	/**
	 * A játék lehetséges állapotai 
	 */
	enum State { RUNNING, PAUSED, WIN, LOSE };
	State gameState = State.RUNNING;

	/**
	 * A játék konstruktora, betölt egy pályát és egy missziót
	 * 
	 *  @param mapName a map fájl neve
	 *  @param missionName a mission fájl neve
	 */
	public Game(String mapName, String missionName) {
		try {
			map = new Map("maps/" + mapName + ".map");
			mission = new Mission("missions/" + mapName + "_" + missionName + ".mission", map);
			view = new View(this, map);
			if (countFPS){
				initFPScounter();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public View getView(){
		return view;
	}
	
	/**
	 * Inicializája a vizuális FPS számlálót
	 * Az FPS számláló a képernyő jobb felső sarkában látható 
	 */
	private void initFPScounter(){
		view.addDrawable(new Drawable(){
			Game game;
			public Drawable init(Game g){
				z_index = 10;
				game = g;
				return this;
			}
			public void draw(Graphics g) {
				g.setColor(Color.white);
				g.setFont(new Font("Consolas", Font.BOLD, 36));
				g.drawString(""+(int)(game.realFPS), 750, 30);
			}
		}.init(this));
		view.magicChange(magic);
	}
	
	/**
	 * A játék főciklusa, a játék állapotától függően lépteti a játékot és kirajzolja azt. 
	 */
	public void run(){
		int i = 1;
		double stime = System.nanoTime();
		while(gameState == State.RUNNING || gameState == State.PAUSED){
			if (gameState != State.PAUSED)
				step();
			view.drawAll();
			try {
				Thread.sleep((int)(1000/(FPS * speed)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// FPS számlálás, 15 darab minta átlagát veszi
			if (++i % 15 == 0) {
				double etime = System.nanoTime();
				realFPS = (1000000000.0/((etime - stime)/15));
				stime = System.nanoTime();
				i = 0;
			}
			
			if (gameState != State.PAUSED && !mission.hasEnemy() && enemies.isEmpty()){
				gameState = State.WIN;
				
			}
		}
		
		if (gameState == State.LOSE){
			view.gameLost();
		}
		else if (gameState == State.WIN){
			view.gameWon();
		}
	}

	/**
	 * A játék logikáját egy lépéssel előrébb viszi.
	 * (Az ellenségek itt haladnak, a tornyok itt lőnek, a lövedékek ott repülnek, stb.)
	 */
	private void step() {
		slowEnemies();
		moveEnemies();

		Enemy enemy = mission.getNextEnemy();

		if (enemy != null) {
			addEnemy(enemy);
		}

		moveProjectiles();
		towersFire();
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
				if (!p.getTarget().isAlive() && enemies.contains(p.target)){
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
		view.magicChange(magic);
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
					gameState = State.LOSE;
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
					e.setSlowingFactor(1);
					for (Obstacle o : obstacles) {
						if (e.getPosition().equals(o.getPosition(), o.getRange()))
							e.setSlowingFactor(o.getSlowingFactor(e) * e.getSlowingFactor());
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
			magic -= gem.cost;
			view.magicChange(magic);
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
			magic -= gem.cost;
			view.magicChange(magic);
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
	 * @return Épített-e oda akadályt
	 */
	public boolean buildObstacle(Vector pos) {
		if (map.canBuildObstacle(pos) && !collidesWithObstacle(pos) && magic >= Obstacle.cost) {
			Obstacle o = new Obstacle(pos);
			synchronized(obstacles){
				obstacles.add(o);
			}
			view.obstacleAdded(o);
			magic -= Obstacle.cost;
			view.magicChange(magic);
			
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
		if (map.canBuildTower(pos) && !collidesWithTower(pos) && magic >= Tower.cost) {
			Tower t = new Tower(pos);
			synchronized(towers){
				towers.add(t);
			}
			view.towerAdded(t);
			magic -= Tower.cost;
			view.magicChange(magic);
			
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
	
	/**
	 * Megmutatja, hogy 1 játékbeli méter hány pixel a képernyőn 
	 */
	static private int pix = 10;
	/**
	 * @param v egy Vector, ami fizikai koordinátákat tartalmaz
	 * @return v Vector áttranszformálva játékbeli koordinákba 
	 * */
	static public Vector toGameCoords(Vector v){
		return new Vector(v.x/pix, v.y/pix);
	}
	
	/**
	 * @param v egy Vector, ami játékbeli koordinátákat tartalmaz
	 * @return v Vector áttranszformálva fizikai koordinátákba
	 * */
	static public Vector toMouseCoords(Vector v){
		return new Vector(v.x*pix, v.y*pix);
	}
}
