package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Az ellenségek típusát leíró osztály.
 *
 * @author Szabó Antal
 */
public class EnemyType {

	static EnemyType fooType = new EnemyType();

	/**
	 * @return az ellenség típus kezdeti életereje
	 */
	public double getInitialHealth() {
		printEnter(this);
		printExit(this);
		return 0;
	}

	/**
	 * @return az ellenség alap sebessége
	 */
	public double getNormalSpeed() {
		printEnter(this);
		printExit(this);
		return 0;
	}
}
