package szoftlab4;

import static szoftlab4.Game.*;


/**
 * Egy ellenség megvalósítása.
 */
public class Enemy {

	/**
	 * Mozgatja az ellenséget a célja felé.
	 *
	 * @return meghalt-e az ellenség
	 */
	public boolean move() {
		printEnter(this);

		Waypoint waypoint = new Waypoint();
		waypoint.getPosition();
		if (printYesNoQuestion("Elérte az ellenség a jelenlegi célját?")) {
			waypoint.getNextWaypoint();
		}

		boolean ret = printYesNoQuestion("Meghalt az ellenség?");

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

		Waypoint waypoint = new Waypoint();
		waypoint.getPosition();
		waypoint.getDistance();
		double ret = 0;

		printExit(this);
		return ret;
	}

	/**
	 * @return az ellenség típusa
	 */
	public EnemyType getEnemyType() {
		printEnter(this);

		EnemyType ret = new EnemyType();

		printExit(this);
		return ret;
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
