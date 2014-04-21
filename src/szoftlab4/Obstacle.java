package szoftlab4;

import java.util.HashMap;

/**
 * Az akadályokat megvalósító osztály.
 * 
 * @author Tallér Bátor
 * @author Török Attila
 */
public class Obstacle {
	public static final int cost = 700;
	private ObstacleGem gem;
	private static HashMap<EnemyType, Double> slowingFactor;
	private Vector position;
	private static double range;
	
	
	static {
		slowingFactor = new HashMap<EnemyType, Double>();
		
		slowingFactor.put(EnemyType.dwarf, 0.7);
		slowingFactor.put(EnemyType.elf, 0.85);
		slowingFactor.put(EnemyType.human, 0.7);
		slowingFactor.put(EnemyType.hobbit, 0.5);
	}
	
	/**
	 * Új metódus! 
	 * @return a megadott pozíció ütközik-e az építménnyel
	 **/
	public boolean doesCollide(Vector pos){
		if (pos.getDistance(position) <= 2)
			return true;
		
		return false;
	}
	
	/**
	 * Létrehoz egy akadályt a megadott pozícióval.
	 *
	 * @param position A létrejövő akadály kívánt helye.
	 */
	public Obstacle(Vector position) {
		this.position = position;
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

	/**
	 * Felrakja a paraméterül kapott varázskövet az akadályra.
	 *
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(ObstacleGem gem) {
		this.gem = gem;
	}

	public double getRange() {
		double rtn = range;
		if (gem != null)
			rtn *= gem.getRangeMultiplier();
		
		return rtn;
	}
}
