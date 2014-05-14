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
	private int ID;
	private boolean visited;
	private List<Pair<Waypoint, Double>> nextWaypoints;

	/**
	 * A kapott helyre létrehoz egy Waypointot.
	 */
	public Waypoint(Vector pos, int ID) {
		this.position = pos;
		this.distance = -1;
		this.nextWaypoints = new ArrayList<Pair<Waypoint, Double>>();
		this.ID = ID;
		this.visited = false;
	}

	/**
	 * @return Visszatér a céltól való távolságával.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Beállítja a céltól való távolságot.
	 */
	public double setDistance() {
		if (distance >= 0)
			return distance;

		if (nextWaypoints.isEmpty()) {
			distance = 0;
			return distance;
		}
		double minDis = 100000;
		double temp;
		visited = true;
		for (Pair<Waypoint, Double> l : nextWaypoints) {
			if (!l.a.visited) {
				temp = l.a.setDistance() + position.getDistance(l.a.position);
				if (temp < minDis)
					minDis = temp;
			}
		}
		visited = false;
		distance = minDis;
		return distance;
	}

	/**
	 * @return Listában visszaadja az ezzel a WP-al összekötött WP-okat.
	 */
	public List<Waypoint> listNextWaypoints() {
		List<Waypoint> list = new ArrayList<Waypoint>();
		for (Pair<Waypoint, Double> l : nextWaypoints) {
			list.add(l.a);
		}
		return list;
	}

	/**
	 * @param wp Beteszi a listába ezt a Waypointot
	 * @param r  Ezzel a valószínűséggel
	 */
	public void setNextWaypoint(Waypoint wp, double r) {
		nextWaypoints.add(new Pair<Waypoint, Double>(wp, r));
	}

	/**
	 * @return Visszatér a következő célponttal.
	 */
	public Waypoint getNextWaypoint() {
		double total = 0;
		for (Pair<Waypoint, Double> w : nextWaypoints) {
			total += w.b;
		}
		double r = Math.random() % total;
		total = 0;
		for (Pair<Waypoint, Double> w : nextWaypoints) {
			if (r <= total + w.b) {
				return w.a;
			} else {
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

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
}
