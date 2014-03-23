package szoftlab4;

/**
 * Az utakat kijelölő pontokat megvalósító osztály.
 * @author Nusser Adam
 *
 */
public class Waypoint {
	
	Vector pos;
	
	/**
	 * A kapott helyre létrehoz egy Waypointot.
	 * @param pos
	 */
	public Waypoint(Vector pos){
		printEnter(this);
		this.pos = pos;
		printExit(this);
	}
	/**
	 * @return Visszatér a céltól való távolságával.
	 */
	public double getDistance(){
		printEnter(this);

		printExit(this);
		return 0;
	}
	/**
	 * 
	 * @return Visszatér a következő célponttal.
	 */
	public Waypoint getNextWaypoint(){
		printEnter(this);

		printExit(this);
		
		return null;
	}
	/**
	 * 
	 * @return Visszatér a pályán felvett pozíciójával.
	 */
	public Vector getPosition(){
		printEnter(this);

		printExit(this);
		
		return pos;
	}
}
