package szoftlab4;

/**
 * Az utakat kijel�l� pontokat megval�s�t� oszt�ly.
 * @author Nusser Adam
 *
 */
public class Waypoint {
	
	Vector pos;
	
	/**
	 * A kapott helyre l�trehoz egy Waypointot.
	 * @param pos
	 */
	public Waypoint(Vector pos){
		printEnter(this);
		this.pos = pos;
		printExit(this);
	}
	/**
	 * @return Visszat�r a c�lt�l val� t�vols�g�val.
	 */
	public double getDistance(){
		printEnter(this);

		printExit(this);
		return 0;
	}
	/**
	 * 
	 * @return Visszat�r a k�vetkez� c�lponttal.
	 */
	public Waypoint getNextWaypoint(){
		printEnter(this);

		printExit(this);
		
		return null;
	}
	/**
	 * 
	 * @return Visszat�r a p�ly�n felvett poz�ci�j�val.
	 */
	public Vector getPosition(){
		printEnter(this);

		printExit(this);
		
		return pos;
	}
}
