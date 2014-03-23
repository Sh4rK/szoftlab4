package szoftlab4;

import static szoftlab4.Game.*;

/**
 * A toronyba rakható varázskövet megvalósító osztály.
 *
 * @author Nusser Adam
 */
public class TowerGem extends Gem {
	/**
	 * @return Visszatér a támadás szorzójával a kapott ellenség títusra.
	 */
	public double getDamageMultiplier(EnemyType type) {
		printEnter(this);

		printExit(this);
		return 0;
	}

	/**
	 * @return Visszatér a támadási távolság szorzójával.
	 */
	public double getRateMultiplier() {
		printEnter(this);

		printExit(this);
		return 0;
	}
}
