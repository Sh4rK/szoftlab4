package szoftlab4;

import java.util.HashMap;
import java.util.List;

/**
 * Egy tornyot megvalósító osztály.
 *
 * @author Nusser Ádám
 */
public class Tower {
	static final double range = 13;
	static final double fireRate = 1;
	static final int cost = 750;
	static final int projectileSpeed = 25;
	public static double radius = 2;
	static HashMap<EnemyType, Double> damage = new HashMap<EnemyType, Double>();
	private TowerGem gem;
	private Vector position;
	private double cooldown;
	public static boolean comeatmebro = false;

	/**
	 * Létrehoz egy tornyot a megadott pozícióval.
	 *
	 * @param position A létrejövő torony kívánt helye.
	 */
	public Tower(Vector position) {

		this.position = position;
		gem = null;
		cooldown = Game.FPS / fireRate;
		damage.put(EnemyType.human, 40.0);
		damage.put(EnemyType.dwarf, 30.0);
		damage.put(EnemyType.elf, 42.0);
		damage.put(EnemyType.hobbit, 55.0);

	}

	/**
	 * @return a megadott pozíció ütközik-e az építménnyel
	 */
	public boolean doesCollide(Vector pos) {
		return pos.getDistance(position) <= 2;

	}

	/**
	 * A torony egy, a kapott listából kiválasztott ellenségre kilő egy lövedéket.
	 * Működés: ha a cooldown nem 0 akkor csökkenti, majd ha van gemje akkor távolságot átállítja.
	 * Majd létrehoz egy listát, melyben a hatósugaron belüli ellenségek vannak, majd ezek közül kiválasztja a
	 * célhoz legközelebbit. Majd létrehoz egy projectilet az így kiválasztott ellenségre.
	 *
	 * @param enemies Az ellenségek listája, amelyek közül kiválasztja a megtámadandót.
	 * @return A lövedék, amit a torony kilőtt az egyik ellenségre.
	 */
	public Projectile attack(List<Enemy> enemies, Game game) {

		if (cooldown >= 0) {
			cooldown--;
			return null;
		}

		List<Enemy> TargetsInRange = new java.util.ArrayList<Enemy>();

		for (Enemy e : enemies) {
			if (position.getDistance(e.getPosition()) < getRange()) {
				TargetsInRange.add(e);
			}
		}
		if (TargetsInRange.isEmpty())
			return null;

		double minDistance = TargetsInRange.get(0).getDistance();
		Enemy target = TargetsInRange.get(0);

		for (Enemy e : TargetsInRange) {
			if (e.getDistance() < minDistance) {
				minDistance = e.getDistance();
				target = e;
			}
		}

		double tempDamage = damage.get(target.getEnemyType());
		cooldown = Game.FPS / fireRate;

		if (gem != null) {
			tempDamage *= gem.getDamageMultiplier(target.getEnemyType());
			cooldown /= gem.getRateMultiplier();
		}
		if (comeatmebro) {
			cooldown = (Game.FPS / fireRate) / 12.0;
		}

		Projectile pro;

		if (Math.random() < 0.06)
			pro = new SplitterProjectile(target, new Vector(position), tempDamage, projectileSpeed, game);
		else
			pro = new Projectile(target, new Vector(position), tempDamage, projectileSpeed);

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
	 * Felrakja a paraméterül kapott varázskövet a toronyra. tüzelési gyakoriságot is átállítja.
	 *
	 * @param gem A felrakandó varázskő.
	 */
	public void setGem(TowerGem gem) {
		this.gem = gem;
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
		if (comeatmebro)
			return 40;

		return range * Fog.getRangeMultiplier() * ((gem == null) ? 1 : gem.getRangeMultiplier());
	}
}
