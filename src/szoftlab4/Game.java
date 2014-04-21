package szoftlab4;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály fogja össze a többi osztályt.
 * A felhasználótól érkező parancsokat, eseményeket kezeli.
 * Tárolja a játék különböző elemeit:
 * Ellenségeket, tornyokat, akadályokat, lövedékeket.
 *
 * @author Tallér Bátor
 * @author Szabó Antal
 */
public class Game {

	/**
	 * Ez számolja milyen mélyen vagyunk a metódushívásokban
	 * A printEnter és a printExit metódusok ez alapján indentálnak
	 */
	private Map map;
	private Mission mission;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Tower> towers = new ArrayList<Tower>();
	
	private int magic = 1500;
	
	public static final int FPS = 30;

	public Game() {
		map = new Map("");
		mission = new Mission("");
	}

	public static void main(String[] args) {
		System.out.println("Hello!");
		/*while (true) {
			Game game = new Game();
			game.run();
		}*/
	}

	/**
	 * Egy ciklusban kérdézi a felhasználót, mit akar csinálni,
	 * majd a válasznal megfelelő metódust hívja meg
	 *
	 * @return Új játékot indítunk-e
	 */
	public void run() {
		
	}
	/**
	 * run részmetódusa
	 */
	public void moveProjectiles(){
		for(Projectile p : projectiles){
			if(p.step())
				projectiles.remove(p);
		}
	}
	/**
	 * run részmetódusa
	 */
	public void towersFire(){
		for(Tower t : towers){
			t.attack(enemies, this);
		}
	}
	/**
	 * run részmetódusa
	 */
	public void slowEnemies(){
		for(Enemy e : enemies){
			for(Obstacle o : obstacles){
				if(e.getPosition().equals(o.getPosition(), 5))
					e.setSlowingFactor(o.getSlowingFactor(e));
				else
					e.setSlowingFactor(1);
			}
		}
	}
	
	public int getMagic(){
		return magic;
	}

	public void addGem(Vector pos, TowerGem gem) {
		
	}

	public void addGem(Vector pos, ObstacleGem gem) {
		
	}
	
	public void addEnemy(Enemy en){
		enemies.add(en);
	}

	/**
	 * A megadott helyre épít egy akadályt
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e akadályt építeni,
	 * illetve hogy nem ütközik-e már meglévő akadállyal.
	 *
	 * @param pos az akadály koordinátái
	 * VÁLTOZÁS:
	 * @return Épített-e oda akadályt
	 */
	public boolean buildObstacle(Vector pos) {
		if (map.canBuildObstacle(pos) && !collidesWithObstacle(pos)){
			obstacles.add(new Obstacle(pos));
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
	 * VÁLTOZÁS:
	 * @return Épített-e oda tornyot
	 */
	public boolean buildTower(Vector pos) {
		if (map.canBuildTower(pos) && !collidesWithTower(pos)){
			towers.add(new Tower(pos));
			return true;
		}
		
		return false;
	}

	/**
	 * @return visszadja, hogy az adott pont ütközik-e egy akadállyal
	 */
	public boolean collidesWithObstacle(Vector pos) {
		for (Obstacle o : obstacles){
			if (o.doesCollide(pos))
				return true;
		}
			
		return false;
	}

	/**
	 * @return visszaadja, hogy az adott pont ütközik-e egy toronnyal
	 */
	public boolean collidesWithTower(Vector pos) {
		for (Tower t : towers){
			if (t.doesCollide(pos))
				return true;
		}
			
		return false;
	}
	
}
