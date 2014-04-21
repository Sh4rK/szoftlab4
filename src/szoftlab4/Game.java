package szoftlab4;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
		new Game().run();
	}

	/**
	 * Egy ciklusban kérdézi a felhasználót, mit akar csinálni,
	 * majd a válasznal megfelelő metódust hívja meg
	 *
	 * @return Új játékot indítunk-e
	 */
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String line = "";
			Scanner ls = null;
			
			try {
				line = br.readLine();
				ls = new Scanner(new InputStreamReader(new ByteArrayInputStream(line.getBytes("UTF-8"))));
		
				if (!ls.hasNext()) {
					continue;
				}
				
				String command = ls.next();
				
				if (command.equals("loadMap")) {
					map = new Map(ls.next());
				} else if (command.equals("loadMission")) {
					mission = new Mission(ls.next());
				} else if (command.equals("setFog")) {
					int opt = ls.nextInt();
					
					if (!((opt == 0) || (opt == 1))) {
						throw new IllegalArgumentException();  
					}
					
					Fog.setFog(opt == 1);
					
				} else if (command.equals("setCritical")) {
					int opt = ls.nextInt();
					
					if (!((opt == 0) || (opt == 1))) {
						throw new IllegalArgumentException();  
					}
					
					Tower.critical = (opt == 1);
					
				} else if (command.equals("setWaypoint")) {
					// ???? throw new WTFException();
				} else if (command.equals("step")) {
					int num = ls.nextInt();
					
					if (num < 0) {
						throw new IllegalArgumentException();  
					}
					
					for (int i = 0; i < num; ++i) {
						step();
					}
					
				} else if (command.equals("buildTower")) {
					double x = ls.nextDouble();
					double y = ls.nextDouble();
					
					buildTower(new Vector(x, y));
					
				} else if (command.equals("buildObstacle")) {
					double x = ls.nextDouble();
					double y = ls.nextDouble();
					
					buildObstacle(new Vector(x, y));
					
				} else if (command.equals("enchant")) {
					double x = ls.nextDouble();
					double y = ls.nextDouble();
					
					Vector point = new Vector(x, y);
					
					// ???
					
				} else if (command.equals("listEnemies")) {
					
				} else if (command.equals("listTowers")) {
					
				} else if (command.equals("listObstacles")) {
					
				} else if (command.equals("listProjectiles")) {
					
				} else {
					System.out.println("Ismeretlen parancs!");
				}
				
			} catch (IOException e) {
				System.out.println("Bemeneti hiba!");
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				System.out.println("Hibás parancsformátum!");
			} catch (IllegalArgumentException e) {
				System.out.println("Érvénytelen paraméter!");
			} finally {
				ls.close();
			}
		}
	}
	
	/**
	 * A játék logikáját egy lépéssel előrébb viszi.
	 * (Az ellenségek itt haladnak, a tornyok itt lőnek, a lövedékek ott repülnek, stb.)
	 */
	private void step() {
		// MAGIC!
	}
	
	/**
	 * run részmetódusa
	 */
	private void moveProjectiles(){
		for(Projectile p : projectiles){
			if(p.step())
				projectiles.remove(p);
		}
	}
	/**
	 * run részmetódusa
	 */
	private void towersFire(){
		for(Tower t : towers){
			t.attack(enemies, this);
		}
	}
	/**
	 * run részmetódusa
	 */
	private void slowEnemies(){
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
	
	/**
	 * @return Az akadály, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Obstacle getCollidingObstacle(Vector pos) {
		for (Obstacle o : obstacles){
			if (o.doesCollide(pos))
				return o;
		}
			
		return null;
	}

	/**
	 * @return A torony, amellyel az adott pont ütközik. Ha egyikkel sem, akkor null.
	 */
	public Tower getCollidingTower(Vector pos) {
		for (Tower t : towers){
			if (t.doesCollide(pos))
				return t;
		}
			
		return null;
	}
}
