package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Az utakat kijelölő pontokat megvalósító osztály.
 *
 * @author Nusser Ádám
 */
public class Waypoint {

	Vector position;
	double distance;
	java.util.List<Pair<Waypoint,Double>> nextWaypoints;
	/**
	 * A kapott helyre létrehoz egy Waypointot.
	 */
	public Waypoint(Vector pos) {
		
		this.position = pos;
		
	}

	/**
	 * @return Visszatér a céltól való távolságával.
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * 
	 * @param d Beállítja a céltól való távolságot.
	 */
	public void setDistance(double d){
		distance = d;
	}

	/**
	 * @return Visszatér a következő célponttal.
	 */
	public Waypoint getNextWaypoint() {
		double total = 0;
		for(Pair<Waypoint, Double> w : nextWaypoints){
			total += w.getV();
		}
		double r = Math.random() % total; 
		total = 0;
		for(Pair<Waypoint, Double> w : nextWaypoints){
			if(r <= total + w.getV()){
				return w.getK();
			}else{
				total += w.getV();
			}
		}
		return null;
	}

	/**
	 * @return Visszatér a pályán felvett pozíciójával.
	 */
	public Vector getPosition() {
		return position;
	}
}
