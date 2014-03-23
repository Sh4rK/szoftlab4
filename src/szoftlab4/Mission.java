package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Az ellenséget ütemezését megvalósító osztály.
 *
 * @author Adam
 */
public class Mission {

	public static Waypoint wp = new Waypoint(new Vector());

	/**
	 * A kapott útvonalról betölti a Mission-t.
	 */
	public Mission(String path) {
		printEnter(this, "path");

		printExit(this);
	}

	/**
	 * Lekéri a következő ellenséget..
	 *
	 * @return Visszatér a listában tárolt következő ellenséggel, vagy null értékkel.
	 */
	public Enemy getNextEnemy() {
		printEnter(this);

		Enemy ret = null;
		if (printYesNoQuestion("Legyen következő ellenség?")) {
			ret = new Enemy(EnemyType.fooType, wp);
		}

		printExit(this);
		return ret;
	}

}
