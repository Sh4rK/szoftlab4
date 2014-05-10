package szoftlab4;

/**
 * A varázskövek közös, absztrakt ősosztálya.
 */
public abstract class Gem {

	protected double range;
	protected int cost = 400;

	/**
	 * Megadja, hogy mennyivel szorzódik a hatótávolság ezen varázskő hatására.
	 *
	 * @return A hatótávolság együtthatója.
	 */
	public double getRangeMultiplier() {
		return range;
	}
	
	public int getCost(){
		return cost;
	}
}
