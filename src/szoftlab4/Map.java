package szoftlab4;


/**
 * A pályát megvalósíto / leíró osztály.
 *
 * @author Nusser Adam
 */
public class Map {

	Waypoint wp;

	/**
	 * A kapott útvonalról betölti a Map-et;
	 */
	public Map(String path) {

	}

	/**
	 * @return Lehet-e az adott helyre Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector position) {
		return true;
	}

	/**
	 * @return Lehet-e a vector helyére Tower-t építeni.
	 */
	public boolean canBuildTower(Vector position) {
		return true;
	}
}
