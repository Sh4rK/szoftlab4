package szoftlab4;

public class SplitterProjectile extends Projectile {

	public SplitterProjectile(Enemy target, Vector start, double damage,
			double speed) {
		super(target, start, damage, speed);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Mozgatja a l�ved�ket a c�lja fel�. Ha el�rte, megsebzi.
	 *
	 * @return true, ha a l�ved�k el�rte a c�lj�t, egy�bk�nt false
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
