package szoftlab4;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

/**
 * A pályát megvalósító / leíró osztály.
 *
 * @author Nusser Ádám
 * @author Szabó Antal
 */
public class Map {

    private HashMap<String, Waypoint> waypoints;

    /**
     * A kapott útvonalról betölti a Map-et;
     */
    public Map(String path) throws Exception {
        File xmlFile = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        Document d = db.parse(xmlFile);
        d.getDocumentElement().normalize();
        NodeList maps = d.getElementsByTagName("map");

        Element map = (Element) maps.item(0);

        waypoints = new HashMap<String, Waypoint>();

        NodeList wps = map.getElementsByTagName("waypoint");
        for (int i = 0; i < wps.getLength(); ++i) {
            Element wp = (Element) wps.item(i);
            String id = ((Element) wp.getElementsByTagName("id").item(0)).getTextContent();
            Element coords = (Element) wp.getElementsByTagName("coords").item(0);
            double x = Double.parseDouble(((Element) coords.getElementsByTagName("x").item(0)).getTextContent());
            double y = Double.parseDouble(((Element) coords.getElementsByTagName("y").item(0)).getTextContent());
            waypoints.put(id, new Waypoint(new Vector(x, y)));
        }

        NodeList rts = map.getElementsByTagName("route");
        for (int i = 0; i < rts.getLength(); ++i) {
            Element rt = (Element) rts.item(i);
            String a = ((Element) rt.getElementsByTagName("a").item(0)).getTextContent();
            String b = ((Element) rt.getElementsByTagName("b").item(0)).getTextContent();
            double chance = Double.parseDouble(((Element) rt.getElementsByTagName("chance").item(0)).getTextContent());
            waypoints.get(a).setNextWaypoint(waypoints.get(b), chance);
        }

        for (Waypoint wp : waypoints.values()) {
            wp.setDistance();
        }
    }

    /**
     * @return Az adott ID-jű Waypoint.
     */
    public Waypoint getWaypointById(String id) {
        return waypoints.get(id);
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
