package szoftlab4;

/**
 * Az utakat kijelölő pontokat megvalósító osztály.
 *
 * @author Nusser Ádám
 */
public class Waypoint {

	private Vector position;
	private double distance;
	private java.util.List<Pair<Waypoint,Double>> nextWaypoints;
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
	public void setNextWaypoint(Waypoint wp, double r){
		nextWaypoints.add(new Pair<Waypoint,Double>(wp, r));
	}

	/**
	 * @return Visszatér a következő célponttal.
	 */
	public Waypoint getNextWaypoint() {
		double total = 0;
		for(Pair<Waypoint, Double> w : nextWaypoints){
			total += w.b;
		}
		double r = Math.random() % total; 
		total = 0;
		for(Pair<Waypoint, Double> w : nextWaypoints){
			if(r <= total + w.b){
				return w.a;
			}else{
				total += w.b;
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
