package szoftlab4;

/**
 * Egy ellenség megvalósítása.
 *
 * @author Szabó Antal
 * @author Tallér Bátor
 */
public class Enemy {
	private EnemyType type;
	private double health;
	private Vector position;
	private Waypoint targetWaypoint;
	private double slowingFactor;
	private static int num = 0;
	private int ID;
	
	/**
	 * Létrehoz egy új ellenséget.
	 *
	 * @param type  az ellenség típusa
	 * @param start a kezdő waypoint
	 */
	public Enemy(EnemyType type, Waypoint start) {
		this.type = type;
		position = start.getPosition();
		targetWaypoint = start.getNextWaypoint();
		health = type.getHealth();
		ID = num++;
	}
	
	public int getID(){
		return ID;
	}
	
	/* Változás: nincs Cloneable interfész, helyette copy konstruktor */
	public Enemy(Enemy en){
		type = en.type;
		health = en.health;
		position = en.position;
		targetWaypoint = en.targetWaypoint;
		slowingFactor = en.slowingFactor;
	}

	/**
	 * Mozgatja az ellenséget a célja felé.
	 *
	 * @return meghalt-e az ellenség
	 */
	public boolean move() {
		Vector wPos = targetWaypoint.getPosition();
		double speed = type.getSpeed() * slowingFactor;
		
		position.MoveDistanceToVector(speed / Game.FPS, wPos);
		
		double epsilon = 2;
		if (position.getDistance(wPos) <= epsilon)
			targetWaypoint = targetWaypoint.getNextWaypoint();
		
		return health <= 0;
	}

	/**
	 * Sebzi az ellenséget.
	 *
	 * @param amount a sebzés mértéke
	 */
	public void damage(double amount) {
		health -= amount;
	}

	/**
	 * @return az ellenség végső céljától való távolsága
	 */
	public double getDistance() {
		return targetWaypoint.getDistance() + position.getDistance(targetWaypoint.getPosition());
	}

	/**
	 * @return az ellenség típusa
	 */
	public EnemyType getEnemyType() {
		return type;
	}

	/**
	 * @return az ellenség pozíciója
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * @return az ellenség életereje
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * Beállítja az ellenség sebességét egy szorzó segítségével.
	 *
	 * @param slowingFactor sebesség szorzó
	 */
	public void setSlowingFactor(double slowingFactor) {
		this.slowingFactor = slowingFactor;
	}
	
	public Enemy split(double dmg){
		this.damage(dmg);
		Enemy rtn = new Enemy(this);
		
		return rtn;
	}
}
