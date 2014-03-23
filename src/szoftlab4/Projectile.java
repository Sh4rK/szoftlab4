package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Egy lövedék viselkedését megvalósító osztály.
 *
 * @author Szabó Antal
 */
public class Projectile {

	private Enemy target;

	/**
	 * Létrehot egy lövedéket.
	 *
	 * @param target a lövedék cél ellensége
	 * @param start  a lövedék kezdeti pozíciója
	 * @param damage a lövedék által kifejtett sebzés
	 */
	public Projectile(Enemy target, Vector start, double damage) {
		printEnter(this, "target", "start", "damage");

		this.target = target;

		printExit(this);
	}

	/**
	 * Mozgatja a lövedéket a célja felé. Ha elérte, megsebzi.
	 *
	 * @return true, ha a lövedék elérte a célját, egyébként false
	 */
	public boolean step() {
		printEnter(this);

		target.getPosition();
		boolean ret = false;
		if (printYesNoQuestion("Elerte a lovedek az ellenseget?")) {
			target.damage(0);
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
