package szoftlab4;

import java.util.HashMap;
import java.util.List;

/**
 * Egy tornyot megvalósító osztály.
 * 
 * @author Nusser Ádám
 */
public class Tower {
	private TowerGem gem;
	private Vector position;
	private double cooldown;	

	static boolean critical = false;
	static final double range = 10;//temp értékek

	static final double fireRate = 1;
	static final int cost = 100;
	static HashMap<EnemyType, Double> damage = new HashMap<EnemyType, Double>();

	/**
	 * Létrehoz egy tornyot a megadott pozícióval.
	 *
	 * @param position A létrejövő torony kívánt helye.
	 */
	public Tower(Vector position) {
		
		this.position = position;
		gem = null;
		cooldown = Game.FPS / fireRate;
		damage.put(EnemyType.human, 20.0);//temp értékek
		damage.put(EnemyType.dwarf, 20.0);
		damage.put(EnemyType.elf, 20.0);
		damage.put(EnemyType.hobbit, 20.0);
		
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
	 * A torony egy, a kapott listából kiválasztott ellenségre kilő egy lövedéket.
	 * Működés: ha a cooldown nem 0 akkor csökkenti, majd ha van gemje akkor távolságot átállítja.
	 * 			Majd létrehoz egy listát, melyben a hatósugaron belüli ellenségek vannak, majd ezek közül kiválasztja a 
	 * 			célhoz legközelebbit. Majd létrehoz egy projectilet az így kiválasztott ellenségre.
	 *
	 * @param enemies Az ellenségek listája, amelyek közül kiválasztja a megtámadandót.
	 * @return A lövedék, amit a torony kilőtt az egyik ellenségre.
	 */
	public Projectile attack(List<Enemy> enemies, Game game) {
		
		if(cooldown >= 0){
			cooldown--;
			return null;
		}
		
		double tempRange = range;
		
		if(gem != null){
			tempRange *= gem.getRangeMultiplier();
		}
		
		tempRange *= gem.getRangeMultiplier();
		
		List<Enemy> TargetsInRange = new java.util.ArrayList<Enemy>();
		
		for(Enemy e: enemies){
			if(position.getDistance(e.getPosition()) < tempRange){
				TargetsInRange.add(e);
			}
		}
		if(TargetsInRange.isEmpty())
			return null;
		
		double minDistance = TargetsInRange.get(0).getDistance();
		Enemy target = TargetsInRange.get(0);
		
		for(Enemy e: TargetsInRange){
			if(e.getDistance() < minDistance){
				minDistance = e.getDistance();
				target = e;
			}
		}
		
		double tempDamage = damage.get(target.getEnemyType());
		
		if(gem != null)
			tempDamage *= gem.getDamageMultiplier(target.getEnemyType());
		
		cooldown = Game.FPS * gem.getRateMultiplier() / fireRate;
		
		Projectile pro;
		
		if (critical)
			pro = new SplitterProjectile(target, new Vector(position), tempDamage, 100, game);
		else
			pro = new Projectile(target, new Vector(position), tempDamage, 100);

		return pro;
		
	}

	/**
	 * Lekérdezi a tornyon lévő varázskövet.
	 *
	 * @return A varázskő, ami a tornyon van.
	 */
	public TowerGem getGem() {
		return gem;
	}

	/**
	 * Lekérdezi a torony pozícióját.
	 *
	 * @return A torony helyét tároló Vector.
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Lekérdezi a torony hatótávolságát.
	 *
	 * @return A torony hatótávolsága.
	 */
	public double getRange() {
		return range;
	}

	/**
	 * Felrakja a paraméterül kapott varázskövet a toronyra. tüzelési gyakoriságot is átállítja.
	 *
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(TowerGem gem) {
		this.gem = gem;
	}
}
