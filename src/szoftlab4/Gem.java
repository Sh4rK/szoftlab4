package szoftlab4;

import static szoftlab4.Game.*;

/**
 * A varázskövek közös, absztrakt ősosztálya.
 */
public abstract class Gem {

	/**
	 * Megadja, hogy mennyivel szorzódik a hatótávolság ezen varázskő hatására.
	 *
	 * @return A hatótávolság együtthatója.
	 */
	public double getRangeMultiplier() {
		printEnter(this);

		printExit(this);
		return 0;
	}
}
