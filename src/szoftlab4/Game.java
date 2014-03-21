package szoftlab4;

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
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private List<Tower> towers = new ArrayList<Tower>();

	public Game() {
		printEnter(this);

		enemies.add(new Enemy());
		projectiles.add(new Projectile());
		towers.add(new Tower());
		obstacles.add(new Obstacle());
		
		printExit(this);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
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

	public static void printMessage(String msg) {
		System.out.print("-");
		printIndent();
		System.out.println("  " + msg);
	}

	public static String printQuestion(String msg) {
		System.out.print("?");
		printIndent();
		System.out.printf("  %s ", msg);
		return sc.next();
	}

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

	public static int printIntQuestion(String msg, int min, int max) {
		while (true) {
			int answer = printIntQuestion(String.format("%s (%d-%d):", msg, min, max));
			if (min <= answer && answer <= max)
				return answer;
		}
	}

	public static double printDoubleQuestion(String msg) {
		while (true) {
			printIndent();
			System.out.printf("%s ", msg);
			if (sc.hasNextDouble()) {
				return sc.nextDouble();
			}
			sc.next();
		}
	}
	
	String[] menuStrings = {"1. Torony építés",
							"2. Akadály építés",
							"3. Következő ellenség lekérése",
							"4. Ellenség mozgatás",
							"5. Torony tüzelés",
							"6. Lövedék mozgatása",
							"7. Varázskő felrakása",
							"8. Kilépés"};
	
	private void printMenu() {
		for (String s : menuStrings)
			printMessage(s);
	}

	public void run() {
		printEnter(this);
		boolean exit = false;
		while (!exit) {
			printMenu();
			int sel = printIntQuestion("Mit akarsz csinálni?", 1, 8);
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
					if (printQuestion("Toronyra vagy akadályra? T/A").equalsIgnoreCase("T"))
						addGem(new Vector(), new TowerGem());
					else
						addGem(new Vector(), new ObstacleGem());
					break;
				case 8:
					String ex = printQuestion("Nyerés, vesztés, feladás vagy kilépés a programból? N/V/F/K");
					if (ex.equalsIgnoreCase("K"))
						exit = true;
					else
						new Game();
					break;
			}
		}

		printExit(this);
	}

	public void addGem(Vector pos, TowerGem gem) {
		printEnter(this, "position", "gem");
		
		if(!printYesNoQuestion("Érvényes helyet adtunk meg?")){ //Változás a dokumentációban
			printExit(this);
			return;
		}
		
		towers.get(0).getPosition();
		towers.get(0).setGem(gem);

		printExit(this);
	}

	public void addGem(Vector pos, ObstacleGem gem) {
		printEnter(this, "position", "gem");

		if(!printYesNoQuestion("Érvényes helyet adtunk meg?")){ //Változás a dokumentációban
			printExit(this);
			return;
		}
		
		obstacles.get(0).getPosition();
		obstacles.get(0).setGem(gem);
		
		printExit(this);
	}

	public void buildObstacle(Vector pos) {
		printEnter(this, "position");

		boolean build = map.canBuildObstacle(pos);
		if (!build){
			printExit(this);
			return;
		}
		
		build &= collidesWithObstacle(pos);
		
		if (build)
			new Obstacle();
		
		printExit(this);
	}

	public void buildTower(Vector pos) {
		printEnter(this, "position");
		
		boolean build = map.canBuildTower(pos);
		if (!build){
			printExit(this);
			return;
		}
		build &= collidesWithTower(pos);
		
		if (build)
			new Tower();
		
		printExit(this);
	}

	public boolean collidesWithObstacle(Vector pos) {
		printEnter(this, "position");
		
		boolean rtn = printYesNoQuestion("Ütközik másik akadállyal?");
		obstacles.get(0).getPosition();
		
		printExit(this);
		return rtn;
	}

	public boolean collidesWithTower(Vector pos) {
		printEnter(this, "position");

		boolean rtn = printYesNoQuestion("Ütközik másik toronnyal?");
		towers.get(0).getPosition();
		
		printExit(this);
		return rtn;
	}
}
