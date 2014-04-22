package szoftlab4;

/**
 * Az ellenségek típusát leíró osztály.
 *
 * @author Szabó Antal
 * @author Tallér Bátor
 */
public class EnemyType {

	public final static EnemyType elf;
	public final static EnemyType dwarf;
	public final static EnemyType hobbit;
	public final static EnemyType human;
	public final int magic;
	private double initialHealth;
	private double normalSpeed;

	protected EnemyType(double health, double speed, int magic) {
		initialHealth = health;
		normalSpeed = speed;
		this.magic = magic;
	}
	
	/**
	 * Statikus konstruktor, létrehozza az ellenségtípusokat.
	 * */
	static {
		elf = new EnemyType(110, 15, 300);
		dwarf = new EnemyType(140, 7, 250);
		hobbit = new EnemyType(75, 11, 100);
		human = new EnemyType(100, 10, 200);
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
