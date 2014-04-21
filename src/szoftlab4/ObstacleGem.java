package szoftlab4;

/**
 * Egy akadályokra tehető varázskövet ír le.
 */
public class ObstacleGem extends Gem {
	public static final ObstacleGem yellow;
	public static final ObstacleGem orange;
	protected double speed;

	protected ObstacleGem(double range, double speed) {
		this.range = range;
		this.speed = speed;
	}

	static {
		yellow = new ObstacleGem(1.5, 1.1);
		orange = new ObstacleGem(1.1, 1.5);
	}

	/**
	 * Megadja, hogy egy akadály lassítási képességet mennyivel módosítja ez a varázskő.
	 *
	 * @param enemyType Az ellenségtípus, amire vonatkozóan lekérdezzük a lassítás módosulásának mértékét.
	 * @return Az enemyType típusú ellenség lassulásának módosulása.
	 */
	public double getSpeedMultiplier(EnemyType enemyType) {
		return 0;
	}
}
