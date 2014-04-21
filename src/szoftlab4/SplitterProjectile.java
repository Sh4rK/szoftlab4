package szoftlab4;

public class SplitterProjectile extends Projectile {

	public SplitterProjectile(Enemy target, Vector start, double damage,
			double speed) {
		super(target, start, damage, speed);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Mozgatja a lövedéket a célja felé. Ha elérte, megsebzi.
	 *
	 * @return true, ha a lövedék elérte a célját, egyébként false
	 */
	public boolean step(Game game) {
		if(target == null)
			return true;
		if(position.equals(target.getPosition(), 0.1)){
			Enemy sp = target.split(damage);
			game.addEnemy(sp);
			return true;
		}
		position.MoveDistanceToVector(speed, target.getPosition());
		return false;
	}
}
