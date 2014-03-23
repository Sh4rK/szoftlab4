package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Az akadályokat megvalósító osztály.
 */
public class Obstacle {
	ObstacleGem gem = new ObstacleGem();

	/**
	 * Létrehoz egy akadályt a megadott pozícióval.
	 *
	 * @param position A létrejövő akadály kívánt helye.
	 */
	public Obstacle(Vector position) {
		printEnter(this, "position");

		printExit(this);
	}

	/**
	 * Visszaadja az akadályon lévő varázskövet.
	 *
	 * @return Az akadályon lévő varázskő.
	 */
	public ObstacleGem getGem() {
		printEnter(this);
		printExit(this);
		return gem;
	}

	/**
	 * Visszaadja az akadály helyét.
	 *
	 * @return Az akadály helyét tartalmazó Vector.
	 */
	public Vector getPosition() {
		printEnter(this);

		Vector ret = new Vector();

		printExit(this);
		return ret;
	}

	/**
	 * Megadja, hogy egy adott ellenséget mennyire lassít le ez az akadály.
	 *
	 * @param enemy Az ellenség, aminek a lassulását lekérdezzük.
	 * @return Az ellenség lassulása.
	 */
	public double getSlowingFactor(Enemy enemy) {
		printEnter(this, "enemy");

		printExit(this);
		return 0;
	}

	/**
	 * Felrakja a paraméterül kapott varázskövet az akadályra.
	 *
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(ObstacleGem gem) {
		printEnter(this, "gem");

		printExit(this);
	}

	public double getRange() {
		Game.printEnter(this);
		
		boolean g = Game.printYesNoQuestion("Van varázskő az akadályon?");
		
		if (g)
			gem.getRangeMultiplier();
		
		Game.printExit(this);
		return 1;
	}
}
