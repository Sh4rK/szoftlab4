package szoftlab4;

import static szoftlab4.Game.*;


/**
 * Egy ellenség megvalósítása.
 *
 * @author Szabó Antal
 */
public class Enemy {

	private EnemyType type;
	private Waypoint waypoint;

	/**
	 * Létrehoz egy új ellenséget.
	 *
	 * @param type  az ellenség típusa
	 * @param start a kezdő waypoint
	 */
	public Enemy(EnemyType type, Waypoint start) {
		printEnter(this, "type", "start");

		this.type = type;
		this.waypoint = start;
		type.getInitialHealth();

		printExit(this);
	}

	/**
	 * Mozgatja az ellenséget a célja felé.
	 *
	 * @return meghalt-e az ellenség
	 */
	public boolean move() {
		printEnter(this);

		boolean ret = printYesNoQuestion("Meghalt az ellenség?");
		if (!ret) {
			type.getNormalSpeed();

			waypoint.getPosition();
			if (printYesNoQuestion("Elérte az ellenség a jelenlegi célját?")) {
				waypoint = waypoint.getNextWaypoint();
			}
		}

		printExit(this);
		return ret;
	}

	/**
	 * Sebzi az ellenséget.
	 *
	 * @param amount a sebzés mértéke
	 */
	public void damage(double amount) {
		printEnter(this, "amount");
		printExit(this);
	}

	/**
	 * @return az ellenség végső céljától való távolsága
	 */
	public double getDistance() {
		printEnter(this);

		waypoint.getPosition();
		waypoint.getDistance();

		printExit(this);
		return 0;
	}

	/**
	 * @return az ellenség típusa
	 */
	public EnemyType getEnemyType() {
		printEnter(this);
		printExit(this);
		return type;
	}

	/**
	 * @return az ellenség pozíciója
	 */
	public Vector getPosition() {
		printEnter(this);

		Vector ret = new Vector();

		printExit(this);
		return ret;
	}

	/**
	 * Beállítja az ellenség sebességét egy szorzó segítségével.
	 *
	 * @param slowingFactor sebesség szorzó
	 */
	public void setSlowingFactor(double slowingFactor) {
		printEnter(this, "slowingFactor");
		printExit(this);
	}

}
