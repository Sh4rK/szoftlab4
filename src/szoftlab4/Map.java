package szoftlab4;

import static szoftlab4.Game.*;

public class Map {

	/**
	 * @return Lehet-e a vector helyére Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector v) {

		boolean b;

		printEnter(this, "vector");
		Waypoint wp = new Waypoint();
		wp.getPosition();

		b = printYesNoQuestion("Lehet építeni akadályt?");

		printExit(this);
		return b;

	}

	/**
	 * @return Lehet-e a vector helyére Tower-t építeni.
	 */
	public boolean canBuildTower(Vector v) {

		boolean b;

		printEnter(this, "vector");
		Waypoint wp = new Waypoint();
		wp.getPosition();

		b = printYesNoQuestion("Lehet építeni akadályt?");

		printExit(this);
		return b;
	}
}
