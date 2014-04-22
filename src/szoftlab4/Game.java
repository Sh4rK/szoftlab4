package szoftlab4;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	public static final int FPS = 30;
	private Map map = null;
	private Mission mission = null;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Tower> towers = new ArrayList<Tower>();
	private int magic = 1500;


	/**
	 * A program fő belépési pontja.
	 *
	 * @param args A parancssori paraméterek.
	 */
	public static void main(String[] args) {
		
		//if (args.length > 0){
		try {
			//System.out.println("MAIN: ");
			Enemy.resetID();
			new Game().run(null);
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		/*}
		else
			new Game().run(null);*/
	}

	/**
	 * A felhasználó (vagy a tesztelő segédprogram) parancsait olvassa be, és hajtja végre.
	 * @throws FileNotFoundException 
	 */
	public void run(String f) {
		BufferedReader br = null; 
		br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String line = "";
			Scanner ls = null;

			try {
				line = br.readLine();
				//System.out.println(line);
				ls = new Scanner(new InputStreamReader(new ByteArrayInputStream(line.getBytes("UTF-8"))));
				
				if (!ls.hasNext()) {
					continue;
				}

				String command = ls.next();
				if (command.equalsIgnoreCase("exit")){
					return;
				}
				else if (command.equalsIgnoreCase("loadMap")) {
					map = new Map(ls.next());
				} else if (command.equalsIgnoreCase("loadMission")) {
					mission = new Mission(ls.next(), map);
				} else if (command.equalsIgnoreCase("setFog")) {
					int opt = ls.nextInt();

					if (!((opt == 0) || (opt == 1))) {
						throw new IllegalArgumentException();
					}

					Fog.setFog(opt == 1);

				} else if (command.equalsIgnoreCase("setCritical")) {
					int opt = ls.nextInt();

					if (!((opt == 0) || (opt == 1))) {
						throw new IllegalArgumentException();
					}

					Tower.critical = (opt == 1);

				} else if (command.equalsIgnoreCase("setWaypoint")) {
					int enemyID = ls.nextInt();
					int waypointID = ls.nextInt();
					
					Enemy e = getEnemyByID(enemyID);
					
					if (e != null) {
						e.setNextWaypoint(map.getWaypointById(waypointID));
					} else {
						throw new IllegalArgumentException();
					}
				} else if (command.equalsIgnoreCase("step")) {
					int num = ls.nextInt();

					if (num < 0) {
						throw new IllegalArgumentException();
					}
					//System.out.println("num = " + num);
					
					for (int i = 0; i < num; ++i) {
						if (step())
							return;
					}

				} else if (command.equalsIgnoreCase("buildTower")) {
					double x = ls.nextDouble();
					double y = ls.nextDouble();

					buildTower(new Vector(x, y));

				} else if (command.equalsIgnoreCase("buildObstacle")) {
					double x = ls.nextDouble();
					double y = ls.nextDouble();

					buildObstacle(new Vector(x, y));

				} else if (command.equalsIgnoreCase("enchant")) {
					String type = ls.next();

					double x = ls.nextDouble();
					double y = ls.nextDouble();

					Vector point = new Vector(x, y);

					if (type.equalsIgnoreCase("red")) {
						addGem(point, TowerGem.red);
					} else if (type.equalsIgnoreCase("green")) {
						addGem(point, TowerGem.green);
					} else if (type.equalsIgnoreCase("blue")) {
						addGem(point, TowerGem.blue);
					} else if (type.equalsIgnoreCase("yellow")) {
						addGem(point, ObstacleGem.yellow);
					} else if (type.equalsIgnoreCase("orange")) {
						addGem(point, ObstacleGem.orange);
					} else {
						throw new IllegalArgumentException();
					}

				} else if (command.equalsIgnoreCase("listEnemies")) {
					listEnemies();
				} else if (command.equalsIgnoreCase("listTowers")) {
					listTowers();
				} else if (command.equalsIgnoreCase("listObstacles")) {
					listObstacles();
				} else if (command.equalsIgnoreCase("listProjectiles")) {
					listProjectiles();
				} else if (command.equalsIgnoreCase("exit")) {
					return;
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
			} catch (Exception e) {
				System.out.println("Valami hiba történt! Kárpótlásul itt egy stack trace:");
				e.printStackTrace();
			} finally {
				if (ls != null)
					ls.close();
			}
		}
	}

	/**
	 * A játék logikáját egy lépéssel előrébb viszi.
	 * (Az ellenségek itt haladnak, a tornyok itt lőnek, a lövedékek ott repülnek, stb.)
	 */
	private boolean step() {
		//System.out.println("step");
		slowEnemies();
		boolean rtn = moveEnemies();

		Enemy enemy = mission.getNextEnemy();

		if (enemy != null) {
			//System.out.println("kaptam enemit");
			addEnemy(enemy);
		}

		moveProjectiles();
		towersFire();
		
		return rtn;
	}

	/**
	 * run A lövedékek mozgatása.
	 */
	private void moveProjectiles() {
		
		Iterator<Projectile> i = projectiles.iterator();
		while (i.hasNext()) {
		   Projectile p = i.next();
		   if (p.step())
				i.remove();
		}
	}

	/**
	 * A tornyok támadását intéző metódus.
	 */
	private void towersFire() {
		for (Tower t : towers) {
			Projectile p = t.attack(enemies, this);
			if (p != null)
				projectiles.add(p);
		}
	}

	/**
	 * Az ellenségek mozgását intéző metódus.
	 */
	private boolean moveEnemies() {
		for (Enemy e : enemies) {
			if (e.move()){
				System.out.println("Enemy winz");
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
	 *            VÁLTOZÁS:
	 * @return Épített-e oda tornyot
	 */
	public boolean buildTower(Vector pos) {
		if (map.canBuildTower(pos) && !collidesWithTower(pos)) {
			towers.add(new Tower(pos));
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
}
