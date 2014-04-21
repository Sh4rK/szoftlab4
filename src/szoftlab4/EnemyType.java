package szoftlab4;

/**
 * Az ellenségek típusát leíró osztály.
 *
 * @author Szabó Antal, Tallér Bátor
 */
public class EnemyType {

	private double initialHealth;
	private double normalSpeed;
	public final int cost;
	
	public final static EnemyType elf;
	public final static EnemyType dwarf;
	public final static EnemyType hobbit;
	public final static EnemyType human;
	
	protected EnemyType(double health, double speed, int cost){
		initialHealth = health;
		normalSpeed = speed;
		this.cost = cost;
	}
	
	static {
		elf = new EnemyType(110, 15, 500);
		dwarf = new EnemyType(140, 7, 400);
		hobbit = new EnemyType(75, 11, 250);
		human = new EnemyType(100, 10, 400);
	}

	/**
	 * @return az ellenség típus kezdeti életereje
	 */
	public double getHealth() {
		return initialHealth;
	}

	/**
	 * @return az ellenség alap sebessége
	 */
	public double getSpeed() {
		return normalSpeed;
	}
}
