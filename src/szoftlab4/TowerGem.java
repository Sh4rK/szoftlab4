package szoftlab4;

/**
 * A toronyba rakható varázskövet megvalósító osztály.
 *
 * @author Nusser Adam
 */
public class TowerGem extends Gem {
	/**
	 * @return Visszatér a sebzés szorzójával a kapott ellenség típusra.
	 */
	public double getDamageMultiplier(EnemyType type) {
		return 0;
	}

	/**
	 * @return Visszatér a tüzelési sebesség szorzójával.
	 */
	public double getRateMultiplier() {
		return 0;
	}
}
