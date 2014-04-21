 package szoftlab4;

import java.util.ArrayList;
import java.util.List;

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
		this.distance = -1;
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
	public double setDistance(){
		if(distance >= 0)
			return distance;
		
		if(nextWaypoints.isEmpty()){
			distance =0;
			return distance;
		}
		double minDis = 1000;
		double temp;
		
		for(Pair<Waypoint, Double> l : nextWaypoints){
			temp = l.a.setDistance() + position.getDistance(l.a.position);
			if(temp  < minDis)
				minDis = temp;
		}
		distance = minDis;
		return distance;
	}
	/**
	 * 
	 * @return Listában visszaadja az ezzel a WP-al összekötött WP-okat.
	 */
	public List<Waypoint> listNextWaypoints(){
		List<Waypoint> list = new ArrayList<Waypoint>();
		for(Pair<Waypoint, Double> l: nextWaypoints){
			list.add(l.a);
		}
		return list;
	}

	/**
	 * 
	 * @param wp Beteszi a listába ezt a Waypointot
	 * @param r Ezzel a valószínűséggel
	 */
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
