package szoftlab4;

import java.util.List;

import static szoftlab4.Game.*;

/**
 * Egy tornyot megvalósító osztály.
 */
public class Tower {
	private TowerGem gem;
	private Vector position;
	private double cooldown;	
	double fireRate;
	
	static final double range = 100;//temp értékek
	static final int cost = 100;
	static java.util.HashMap<EnemyType, Double> damage;

	/**
	 * Létrehoz egy tornyot a megadott pozícióval.
	 *
	 * @param position A létrejövő torony kívánt helye.
	 */
	public Tower(Vector position) {
		
		this.position = position;
		gem = null;
		fireRate = 100; // temp érték
		cooldown = fireRate;
		damage.put(EnemyType.Human, 20);//temp értékek
		damage.put(EnemyType.Dwarf, 20);
		damage.put(EnemyType.Elf, 20);
		damage.put(EnemyType.Hobbit, 20);
		
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
	public Projectile attack(List<Enemy> enemies) {
		
		if(cooldown != 0){
			cooldown--;
			return null;
		}
		
		double tempRange = range;
		
		if(gem != null){
			tempRange *= gem.getRangeMultiplier();
		}
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
		
		cooldown = fireRate;
		Projectile pro = new Projectile(target, new Vector(position), tempDamage, 100);

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
		if(gem != null)
			fireRate /= this.gem.getRateMultiplier();
		this.gem = gem;
		fireRate *= this.gem.getRateMultiplier();
	}
}
