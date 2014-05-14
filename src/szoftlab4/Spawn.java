package szoftlab4;

/**
 * Egy konkrét ellenség típusát és játékba lépésének idejét tárolja.
 *
 * @author Szabó Antal
 */
public class Spawn {
	public Enemy enemy;
	public double timeToSpawn;

	public Spawn(Enemy enemy, double tts) {
		this.enemy = enemy;
		this.timeToSpawn = tts;
	}
}
