package szoftlab4;

public class SplitterProjectile extends Projectile{

	private Game game;
	
	public SplitterProjectile(Enemy target, Vector start, double damage, double speed, Game game) {
		super(target, start, damage, speed);
		
		this.game = game;
	}
	
	/**
	 * Mozgatja a lövedéket a célja felé. Ha elérte, megsebzi.
	 *
	 * @return true, ha a lövedék elérte a célját, egyébként false
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
