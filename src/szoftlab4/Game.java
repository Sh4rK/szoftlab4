package szoftlab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	/**
	 * Ez számolja milyen mélyen vagyunk a metódushívásokban
	 * A printEnter és a printExit metódusok ez alapján indentálnak
	 */
	private static int tabs = 0;
	private static Scanner sc = new Scanner(System.in);
	private Map map = new Map();
	private Mission mission = new Mission();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Tower> towers = new ArrayList<Tower>();

	public Game() {
		enemies.add(new Enemy());
		projectiles.add(new Projectile());
		towers.add(new Tower());
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	/**
	 * Minden metódus hívás legelején ezt a metódust hívjuk meg.
	 * A következőt írja ki a kimenetre: ->[:Osztály].metódus(param):
	 *
	 * @param sender az objektum, amiből meg lett hívva a metódus
	 * @param params ha hívó metódusnak voltak paraméterei, akkor string formátumban meg kell adni a neveiket
	 */
	public static void printEnter(Object sender, String... params) {
		++tabs;
		String methodEnter = "";

		for (int i = 0; i < tabs; ++i)
			methodEnter += "\t";

		Exception e = new Exception();
		e.fillInStackTrace();
		methodEnter += "->[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
		String methodName = e.getStackTrace()[1].getMethodName();
		methodEnter += "." + methodName + "(";
		if (params.length != 0) {
			int i;
			for (i = 0; i < params.length - 1; ++i)
				methodEnter += params[i] + ", ";
			methodEnter += params[i];
		}
		methodEnter += "):";
		System.out.println(methodEnter);
	}

	/**
	 * Minden metódus hívás legvégén ezt a metódust hívjuk meg.
	 * A következőt írja ki a kimenetre: <-[:Osztály].metódus()
	 *
	 * @param sender az objektum, amiből meg lett hívva a metódus
	 */
	public static void printExit(Object sender) {
		String methodExit = "";

		for (int i = 0; i < tabs; ++i)
			methodExit += "\t";
		Exception e = new Exception();
		e.fillInStackTrace();
		methodExit += "<-[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
		String methodName = e.getStackTrace()[1].getMethodName();
		methodExit += "." + methodName + "(" + ")";

		System.out.println(methodExit);
		--tabs;
	}

	public static void printMessage(String msg) {
		String tab = "";

		for (int i = 0; i < tabs; ++i)
			tab += "\t";
		System.out.println(tab + "  " + msg);
	}

	public static String printQuestion(String msg) {
		printMessage(msg);
		String rtn = sc.next();
		return rtn;
	}

	private void printMenu() {
		printMessage("1. Torony építés");
		printMessage("2. Akadály építés");
		printMessage("3. Következő ellenség lekérése");
		printMessage("4. Ellenség mozgatás");
		printMessage("5. Torony tüzelés");
		printMessage("6. Lövedék mozgatása");
		printMessage("7. Varázskő felrakása");
		printMessage("8. Kilépés");
	}

	private int menu() throws IOException {
		while (true) {
			String r = printQuestion("Kiírjam a menüt? I/N");

			if (r.equals("i") || r.equals("I"))
				printMenu();

			if (sc.hasNextInt()) {
				int sel = sc.nextInt();
				if (sel >= 1 && sel <= 8)
					return sel;
			}
			printMessage("Ilyen menüpont nincs!");
		}
	}

	public void run() {
		printEnter(this);
		boolean exit = false;
		while (!exit) {
			try {
				int sel = menu();
				switch (sel) {
					case 1:
						buildTower(new Vector());
						break;
					case 2:
						buildObstacle(new Vector());
						break;
					case 3:
						mission.getNextEnemy();
						break;
					case 4:
						enemies.get(0).move();
						break;
					case 5:
						towers.get(0).attack(enemies);
						break;
					case 6:
						projectiles.get(0).step();
						break;
					case 7:
						addGem(new Vector(), new ObstacleGem());
						break;
					case 8:
						exit = true;
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		printExit(this);
	}

	public void addGem(Vector v, TowerGem gem) {
		printEnter(this, "vector", "gem");

		printExit(this);
	}

	public void addGem(Vector v, ObstacleGem gem) {
		printEnter(this, "vector", "gem");

		printExit(this);
	}

	public void buildObstacle(Vector v) {
		printEnter(this, "vector");

		printExit(this);
	}

	public void buildTower(Vector v) {
		printEnter(this, "vector");

		printExit(this);
	}

	public boolean collidesWithObstacle(Vector v) {
		printEnter(this, "vector");

		printExit(this);
		return true;
	}

	public boolean collidesWithTower(Vector v) {
		printEnter(this, "vector");

		printExit(this);
		return true;
	}


}
