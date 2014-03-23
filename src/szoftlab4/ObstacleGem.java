package szoftlab4;

import static szoftlab4.Game.*;

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
		printEnter(this, "enemyType");

		printExit(this);
		return 0;
	}
}
