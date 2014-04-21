package szoftlab4;

import java.util.ArrayList;
import java.util.List;
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
	private static int tabs = 0;
	private static Scanner sc = new Scanner(System.in);
	private Map map;
	private Mission mission;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Tower> towers = new ArrayList<Tower>();
	
	public static final int FPS = 30;

	public Game() {
		
	}

	public static void main(String[] args) {
		boolean run = true;
		while (true) {
			Game game = new Game();
		}
	}

	/**
	 * Egy ciklusban kérdézi a felhasználót, mit akar csinálni,
	 * majd a válasznal megfelelő metódust hívja meg
	 *
	 * @return Új játékot indítunk-e
	 */
	public void run() {
		
	}

	public void addGem(Vector pos, TowerGem gem) {
		
	}

	public void addGem(Vector pos, ObstacleGem gem) {
		
	}

	/**
	 * A megadott helyre épít egy akadályt
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e akadályt építeni,
	 * illetve hogy nem ütközik-e már meglévő akadállyal.
	 *
	 * @param pos az akadály koordinátái
	 */
	public void buildObstacle(Vector pos) {
		
	}

	/**
	 * A megadott helyre épít egy tornyot
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e tornyot építeni,
	 * illetve hogy nem ütközik-e már meglévő toronnyal.
	 *
	 * @param pos a torony koordinátái
	 */
	public void buildTower(Vector pos) {
		
	}

	/**
	 * @return visszadja, hogy az adott pont ütközik-e egy akadállyal
	 */
	public boolean collidesWithObstacle(Vector pos) {
		return true;
	}

	/**
	 * @return visszaadja, hogy az adott pont ütközik-e egy toronnyal
	 */
	public boolean collidesWithTower(Vector pos) {
		return true;
	}
}
