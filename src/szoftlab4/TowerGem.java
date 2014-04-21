package szoftlab4;

/**
 * A toronyba rakható varázskövet megvalósító osztály.
 *
 * @author Nusser Adam
 */
public class TowerGem extends Gem {
	public static final TowerGem red; // damage
	public static final TowerGem green; // range
	public static final TowerGem blue; // rate
	protected double damage;
	protected double rate;

	protected TowerGem(double range, double damage, double rate) {
		this.range = range;
		this.damage = damage;
		this.rate = rate;
	}

	static {
		red = new TowerGem(1.1, 1.5, 1.1);
		blue = new TowerGem(1.5, 1.1, 1.1);
		green = new TowerGem(1.1, 1.1, 1.5);
	}

	/**
	 * @return Visszatér a sebzés szorzójával a kapott ellenség típusra.
	 */
	public double getDamageMultiplier(EnemyType type) {
		return damage;
	}

	/**
	 * @return Visszatér a tüzelési sebesség szorzójával.
	 */
	public double getRateMultiplier() {
		return rate;
	}
}
