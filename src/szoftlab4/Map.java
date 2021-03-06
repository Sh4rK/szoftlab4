package szoftlab4;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

/**
 * A pályát megvalósító / leíró osztály.
 *
 * @author Nusser Ádám
 * @author Szabó Antal
 */
public class Map {

	public static double roadRadius = 2;

	private HashMap<Integer, Waypoint> waypoints;

	/**
	 * A kapott útvonalról betölti a Map-et
	 */
	public Map(String path) throws Exception {
		File xmlFile = new File(path);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.parse(xmlFile);
		d.getDocumentElement().normalize();

		Element map = (Element) d.getElementsByTagName("map").item(0);

		waypoints = new HashMap<Integer, Waypoint>();

		NodeList wps = map.getElementsByTagName("waypoint");
		for (int i = 0; i < wps.getLength(); ++i) {
			Element wp = (Element) wps.item(i);
			int id = Integer.parseInt(wp.getElementsByTagName("id").item(0).getTextContent());
			Element coords = (Element) wp.getElementsByTagName("coords").item(0);
			double x = Double.parseDouble(coords.getElementsByTagName("x").item(0).getTextContent());
			double y = Double.parseDouble(coords.getElementsByTagName("y").item(0).getTextContent());
			waypoints.put(id, new Waypoint(new Vector(x, y)));
		}

		NodeList rts = map.getElementsByTagName("route");
		for (int i = 0; i < rts.getLength(); ++i) {
			Element rt = (Element) rts.item(i);
			int a = Integer.parseInt(rt.getElementsByTagName("a").item(0).getTextContent());
			int b = Integer.parseInt(rt.getElementsByTagName("b").item(0).getTextContent());
			double chance = Double.parseDouble(rt.getElementsByTagName("chance").item(0).getTextContent());
			waypoints.get(a).setNextWaypoint(waypoints.get(b), chance);
		}

		for (Waypoint wp : waypoints.values()) {
			wp.setDistance();
		}
	}

	/**
	 * Segédmetódus, amely megadja egy pontnak egy két végpontjával adott szakasztól való távolságát.
	 *
	 * @param s1 A szakasz egyik pontja.
	 * @param s2 A szakasz másik pontja.
	 * @param p  A pont, aminek a távolsága érdekes.
	 * @return A pontnak a szakasztól mért távolsága.
	 */
	private double segmentPointDistance(Vector s1, Vector s2, Vector p) {
		double px = s2.x - s1.x;
		double py = s2.y - s1.y;

		double lenSquared = px * px + py * py;

		double u = ((p.x - s1.x) * px + (p.y - s1.y) * py) / lenSquared;
		u = Math.max(Math.min(u, 1), 0);

		double dx = s1.x + u * px - p.x;
		double dy = s1.y + u * py - p.y;

		return Math.sqrt(dx * dx + dy * dy);
	}

	private boolean isInRoadRange(Vector pos, double range) {
		for (Waypoint wpfrom : waypoints.values()) {
			for (Waypoint wpto : wpfrom.listNextWaypoints()) {
				if (segmentPointDistance(wpfrom.getPosition(), wpto.getPosition(), pos) < range) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return Lehet-e az adott helyre Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector position) {
		return isInRoadRange(position, roadRadius);
	}

	/**
	 * @return Lehet-e a vector helyére Tower-t építeni.
	 */
	public boolean canBuildTower(Vector position) {
		return !isInRoadRange(position, roadRadius + Tower.radius);
	}

	/**
	 * Megkeres egy waypointt az azonosítója alapján.
	 *
	 * @param waypointID A keresett waypoint azonosítója.
	 * @return A keresett waypoint, vagy null, ha nincs találat.
	 */
	public Waypoint getWaypointByID(int waypointID) {
		return waypoints.get(waypointID);
	}

	public Collection<Waypoint> getWaypoints() {
		return waypoints.values();
	}
}
