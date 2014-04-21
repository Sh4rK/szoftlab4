package szoftlab4;

/**
 * A varázskövek közös, absztrakt ősosztálya.
 */
public abstract class Gem {

	protected double range;
	/**
	 * Megadja, hogy mennyivel szorzódik a hatótávolság ezen varázskő hatására.
	 *
	 * @return A hatótávolság együtthatója.
	 */
	public double getRangeMultiplier() {
		return range;
	}
}
