package szoftlab4;

import static szoftlab4.Game.*;

public class Map {

	/**
	 * @return Lehet-e az adott helyre Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector position) {
		printEnter(this, "position");

		Waypoint wp = new Waypoint();
		wp.getPosition();

		boolean b = printYesNoQuestion("Lehet építeni akadályt?");

		printExit(this);
		return b;

	}

	/**
	 * @return Lehet-e az adott helyre Tower-t építeni.
	 */
	public boolean canBuildTower(Vector position) {
		printEnter(this, "position");

		Waypoint wp = new Waypoint();
		wp.getPosition();

		boolean b = printYesNoQuestion("Lehet építeni tornyot?");

		printExit(this);
		return b;
	}
}
