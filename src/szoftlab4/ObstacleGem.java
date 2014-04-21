package szoftlab4;

/**
 * Egy akadályokra tehető varázskövet ír le.
 */
public class ObstacleGem extends Gem {

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
