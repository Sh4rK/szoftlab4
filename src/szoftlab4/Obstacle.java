package szoftlab4;

import java.util.HashMap;

/**
 * Az akadályokat megvalósító osztály.
 *
 * @author Tallér Bátor
 * @author Török Attila
 */
public class Obstacle {
	public static final int cost = 500;
	private static HashMap<EnemyType, Double> slowingFactor;
	public static final double range = 2;

	static {
		slowingFactor = new HashMap<EnemyType, Double>();

		slowingFactor.put(EnemyType.dwarf, 0.5);
		slowingFactor.put(EnemyType.elf, 0.7);
		slowingFactor.put(EnemyType.human, 0.6);
		slowingFactor.put(EnemyType.hobbit, 0.75);
	}

	private ObstacleGem gem;
	private Vector position;

	/**
	 * Létrehoz egy akadályt a megadott pozícióval.
	 *
	 * @param position A létrejövő akadály kívánt helye.
	 */
	public Obstacle(Vector position) {
		this.position = position;
	}

	/**
	 * @return a megadott pozíció ütközik-e az építménnyel
	 */
	public boolean doesCollide(Vector pos) {
		return pos.getDistance(position) <= getRange();

	}

	public boolean doesCollideWithCircle(Vector pos, double radius) {
		return pos.getDistance(position) <= getRange() + radius;

	}

	/**
	 * Visszaadja az akadályon lévő varázskövet.
	 *
	 * @return Az akadályon lévő varázskő.
	 */
	public ObstacleGem getGem() {
		return gem;
	}

	/**
	 * Felrakja a paraméterül kapott varázskövet az akadályra.
	 *
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(ObstacleGem gem) {
		this.gem = gem;
	}

	/**
	 * Visszaadja az akadály helyét.
	 *
	 * @return Az akadály helyét tartalmazó Vector.
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Megadja, hogy egy adott ellenséget mennyire lassít le ez az akadály.
	 *
	 * @param enemy Az ellenség, aminek a lassulását lekérdezzük.
	 * @return Az ellenség lassulása.
	 */
	public double getSlowingFactor(Enemy enemy) {
		double slow = slowingFactor.get(enemy.getEnemyType());
		if (gem != null)
			slow *= gem.getSpeedMultiplier(enemy.getEnemyType());

		return slow;
	}

	public double getRange() {
		double rtn = range;//* Fog.getRangeMultiplier();
		if (gem != null)
			rtn *= gem.getRangeMultiplier();

		return rtn;
	}
}
