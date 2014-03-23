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

	public Game() {
		printEnter(this);

		printMessage("1. palya");
		printMessage("2. palya");
		printIntQuestion("Melyik palyat toltsem be?", 1, 2);
		map = new Map("map.map");

		printMessage("1. kuldetes");
		printMessage("2. kuldetes");
		printIntQuestion("Melyik kuldetest toltsem be?", 1, 2);
		mission = new Mission("mission.mission");

		enemies.add(new Enemy(EnemyType.fooType, Mission.wp));
		projectiles.add(new Projectile(enemies.get(0), new Vector(), 0));
		towers.add(new Tower(new Vector()));
		obstacles.add(new Obstacle(new Vector()));
		printExit(this);
	}

	public static void main(String[] args) {
		boolean run = true;
		while (run) {
			Game game = new Game();
			run = game.run();
		}
	}

	public static void printIndent() {
		for (int i = 0; i < tabs; ++i)
			System.out.print("    ");
	}

	/**
	 * Minden metódus hívás legelején ezt a metódust hívjuk meg.
	 * A következőt írja ki a kimenetre: ->[:Osztály].metódus(param):
	 *
	 * @param sender az objektum, amiből meg lett hívva a metódus
	 * @param params ha hívó metódusnak voltak paraméterei, akkor string formátumban meg kell adni a neveiket
	 */
	public static void printEnter(Object sender, String... params) {
		System.out.print(">");
		++tabs;
		printIndent();

		Exception e = new Exception();
		e.fillInStackTrace();

		String methodEnter = "->[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
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
		System.out.print("<");
		printIndent();

		Exception e = new Exception();
		e.fillInStackTrace();

		String methodExit = "<-[:" + sender.getClass().getSimpleName() + "]";//->[:Game].buildTower(pos):
		String methodName = e.getStackTrace()[1].getMethodName();
		methodExit += "." + methodName + "(" + ")";

		System.out.println(methodExit);
		--tabs;
	}

	/**
	 * Kiír egy üzenetet a megfelelő jelzéssel, indentálással
	 */
	public static void printMessage(String msg) {
		System.out.print("-");
		printIndent();
		System.out.println("  " + msg);
	}

	/**
	 * Kiír egy kérdést a megfelelő formátumban
	 *
	 * @return stringként a megadott válasz
	 */
	public static String printQuestion(String msg) {
		System.out.print("?");
		printIndent();
		System.out.printf("  %s ", msg);
		return sc.next();
	}

	/**
	 * Kiír egy igen/nem kérdést a megfelelő formátumban
	 *
	 * @return igen/nem
	 */
	public static boolean printYesNoQuestion(String msg) {
		while (true) {
			System.out.print("?");
			printIndent();
			System.out.printf("  %s (I/N): ", msg);
			String answer = sc.next();
			if (answer.equalsIgnoreCase("i"))
				return true;
			if (answer.equalsIgnoreCase("n"))
				return false;
		}
	}

	/**
	 * Kiír egy kérdést, amire a válasz egész szám lehet
	 */
	public static int printIntQuestion(String msg) {
		while (true) {
			System.out.print("?");
			printIndent();
			System.out.printf("  %s ", msg);
			if (sc.hasNextInt()) {
				return sc.nextInt();
			}
			sc.next();
		}
	}

	/**
	 * Kiír egy kérdést, amire a válasz egész szám lehet
	 *
	 * @param min lehetséges válasz alsó korlátja
	 * @param max lehetséges válasz felső korlátja
	 */
	public static int printIntQuestion(String msg, int min, int max) {
		while (true) {
			int answer = printIntQuestion(String.format("%s (%d-%d):", msg, min, max));
			if (min <= answer && answer <= max)
				return answer;
		}
	}

	String[] menuStrings = {"1. Torony epites",
							"2. Akadaly epites",
							"3. Kovetkezo ellenseg lekerese",
							"4. Ellenseg mozgatas",
							"5. Torony tuzeles",
							"6. Lovedek mozgatasa",
							"7. Varazsko felrakasa",
							"8. Ellensegek lassitasa",
							"9. Kilepes"};

	private void printMenu() {
		for (String s : menuStrings)
			printMessage(s);
	}

	/**
	 * Egy ciklusban kérdézi a felhasználót, mit akar csinálni,
	 * majd a válasznal megfelelő metódust hívja meg
	 *
	 * @return Új játékot indítunk-e
	 */
	public boolean run() { //változás a dokumentációban: visszatérési érték
		printEnter(this);
		boolean exit = false;
		while (!exit) {
			printMenu();
			int sel = printIntQuestion("Mit akarsz csinalni?", 1, 9);
			printMessage(menuStrings[sel - 1]);
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
					if (printQuestion("Toronyra vagy akadalyra? T/A").equalsIgnoreCase("T"))
						addGem(new Vector(), new TowerGem());
					else
						addGem(new Vector(), new ObstacleGem());
					break;
				case 8: //változás a dokumentációban: új menüpont
					enemies.get(0).setSlowingFactor(1);
					obstacles.get(0).getPosition();
					obstacles.get(0).getRange();
					obstacles.get(0).getSlowingFactor(enemies.get(0));
					enemies.get(0).getPosition();
					if (printYesNoQuestion("Van ellenseg az akadaly hatokoreben?"))
						enemies.get(0).setSlowingFactor(1);
					break;
				case 9:
					String ex = printQuestion("Nyeres, vesztes, feladas vagy kilepes a programbol? N/V/F/K");
					if (ex.equalsIgnoreCase("K")) {
						printExit(this);
						return false;
					} else if (ex.equalsIgnoreCase("N"))
						printMessage("Gratulalok, nyertel");
					else if (ex.equalsIgnoreCase("V"))
						printMessage("Sajnos vesztettel");
					else if (ex.equalsIgnoreCase("F"))
						printMessage("Feladtad a jatekot");
					else
						break;

					printExit(this);
					return true;
			}
		}

		printExit(this);
		return true;
	}

	public void addGem(Vector pos, TowerGem gem) {
		printEnter(this, "position", "gem");

		if (!printYesNoQuestion("Ervenyes helyet adtunk meg?")) { //Változás a dokumentációban
			printExit(this);
			return;
		}

		towers.get(0).getPosition();
		towers.get(0).setGem(gem);

		printExit(this);
	}

	public void addGem(Vector pos, ObstacleGem gem) {
		printEnter(this, "position", "gem");

		if (!printYesNoQuestion("Ervenyes helyet adtunk meg?")) { //Változás a dokumentációban
			printExit(this);
			return;
		}

		obstacles.get(0).getPosition();
		obstacles.get(0).setGem(gem);

		printExit(this);
	}

	/**
	 * A megadott helyre épít egy akadályt
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e akadályt építeni,
	 * illetve hogy nem ütközik-e már meglévő akadállyal.
	 *
	 * @param pos az akadály koordinátái
	 */
	public void buildObstacle(Vector pos) {
		printEnter(this, "position");

		if (!map.canBuildObstacle(pos)) {
			printExit(this);
			return;
		}

		if (!collidesWithObstacle(pos))
			new Obstacle(pos);

		printExit(this);
	}

	/**
	 * A megadott helyre épít egy tornyot
	 * Ellenőrzi, hogy a megadott helyre a pályán lehet-e tornyot építeni,
	 * illetve hogy nem ütközik-e már meglévő toronnyal.
	 *
	 * @param pos a torony koordinátái
	 */
	public void buildTower(Vector pos) {
		printEnter(this, "position");

		if (!map.canBuildTower(pos)) {
			printExit(this);
			return;
		}

		if (!collidesWithTower(pos))
			new Tower(new Vector());

		printExit(this);
	}

	/**
	 * @return visszadja, hogy az adott pont ütközik-e egy akadállyal
	 */
	public boolean collidesWithObstacle(Vector pos) {
		printEnter(this, "position");

		obstacles.get(0).getPosition();
		boolean rtn = printYesNoQuestion("Utkozik masik akadallyal?");

		printExit(this);
		return rtn;
	}

	/**
	 * @return visszaadja, hogy az adott pont ütközik-e egy toronnyal
	 */
	public boolean collidesWithTower(Vector pos) {
		printEnter(this, "position");

		towers.get(0).getPosition();
		boolean rtn = printYesNoQuestion("Utkozik masik toronnyal?");

		printExit(this);
		return rtn;
	}
}
