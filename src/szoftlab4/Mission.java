package szoftlab4;

import static szoftlab4.Game.*;

public class Mission {

	/**
	 * @return Visszatér a listában tárolt következő ellenséggel.
	 */
	public Enemy getNextEnemy() {
		printEnter(this);

		Enemy ret = new Enemy(EnemyType.fooType, new Waypoint());

		printExit(this);
		return ret;
	}

}
