package szoftlab4;

public class SplitterProjectile extends Projectile{

	private Game game;
	
	public SplitterProjectile(Enemy target, Vector start, double damage, double speed, Game game) {
		super(target, start, damage, speed);
		
		this.game = game;
	}
	
	/**
	 * Mozgatja a l�ved�ket a c�lja fel�. Ha el�rte, megsebzi.
	 *
	 * @return true, ha a l�ved�k el�rte a c�lj�t, egy�bk�nt false
	 */
	public boolean step(){
		if(target == null)
			return true;
		
		position.MoveDistanceToVector(speed / Game.FPS, target.getPosition());
		
		if(position.equals(target.getPosition(), 0.1)){
			game.addEnemy(target.split(damage));
			return true;
		}
		return false;
	}
}
