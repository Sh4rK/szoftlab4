package szoftlab4;

import static szoftlab4.Game.*;

import java.util.List;

/**
 * Egy tornyot megvalósító osztály.
 */
public class Tower {
	TowerGem gem = new TowerGem();

	/**
	 * Létrehoz egy tornyot a megadott pozícióval.
	 *
	 * @param position A létrejövő torony kívánt helye.
	 */
	public Tower(Vector position) {
		printEnter(this, "position");

		printExit(this);
	}
	
	/**
	 * A torony egy, a kapott listából kiválasztott ellenségre kilő egy lövedéket.
	 * 
	 * @param enemies Az ellenségek listája, amelyek közül kiválasztja a megtámadandót.
	 * @return A lövedék, amit a torony kilőtt az egyik ellenségre.
	 */
	public Projectile attack(List<Enemy> enemies) {
		printEnter(this, "enemies");
		
		Projectile ret = null;
		if (printYesNoQuestion("Van a tornyon varázskő?")) {
			gem.getRangeMultiplier();
			gem.getDamageMultiplier(enemies.get(0).getEnemyType());
		}
		
		if (printYesNoQuestion("Van a torony hatósugarán belül ellenség?"))
		{
			enemies.get(0).getDistance();
			if (printYesNoQuestion("Ez az ellenség van a legközelebb a célhoz?")) {
				ret = new Projectile(enemies.get(0), new Vector(), 0);
			}
		}
		
		printExit(this);
		return ret;
	}
	
	/**
	 * Lekérdezi a tornyon lévő varázskövet.
	 * 
	 * @return A varázskő, ami a tornyon van.
	 */
	public TowerGem getGem() {
		printEnter(this);
		
		TowerGem ret = new TowerGem();
		
		printExit(this);
		return ret;
	}

	/**
	 * Lekérdezi a torony pozícióját.
	 * 
	 * @return A torony helyét tároló Vector.
	 */
	public Vector getPosition() {
		printEnter(this);
		
		Vector ret = new Vector();
		
		printExit(this);
		return ret;
	}

	/**
	 * Lekérdezi a torony hatótávolságát.
	 * 
	 * @return A torony hatótávolsága.
	 */
	public double getRange() {
		printEnter(this);
		
		printExit(this);
		return 0;
	}
	
	/**
	 * Felrakja a paraméterül kapott varázskövet a toronyra.
	 * 
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(TowerGem gem) {
		printEnter(this, "gem");

		printExit(this);
	}
}
