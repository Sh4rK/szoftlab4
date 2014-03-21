package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Egy lövedék viselkedését megvalósító osztály.
 */
public class Projectile {

	/**
	 * Mozgatja a lövedéket a célja felé. Ha elérte, megsebzi.
	 *
	 * @return true, ha a lövedék elérte a célját, egyébként false
	 */
	public boolean step() {
		printEnter(this);

		Enemy enemy = new Enemy();
		enemy.getPosition();
		boolean ret = false;
		if (printYesNoQuestion("Elérte a lövedék az ellenséget?")) {
			enemy.damage(0);
			ret = true;
		}

		printExit(this);
		return ret;
	}

	/**
	 * @return a lövedék pozíciója
	 */
	public Vector getPosition() {
		printEnter(this);

		Vector ret = new Vector();

		printExit(this);
		return ret;
	}

}
