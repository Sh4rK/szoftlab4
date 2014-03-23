package szoftlab4;

import static szoftlab4.Game.*;

/**
 * A pályát megvalósíto / leíró osztály.
 * @author Nusser Adam
 *
 */
public class Map {
	
	Waypoint wp;
	
	/**
	 * A kapott útvonalról betölti a Map-et;
	 */
	public Map(String path){
		printEnter(this, "path");

		wp = new Waypoint(new Vector());

		printExit(this);
	}
	
	/**
	 * @return Lehet-e az adott helyre Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector position){
		printEnter(this, "position");

		wp.getPosition();
		boolean b = printYesNoQuestion("Lehet építeni akadályt?");
		
		printExit(this);
		return b;
	}

	/**
	 * @return Lehet-e a vector helyére Tower-t építeni.
	 */
	public boolean canBuildTower(Vector position){
		printEnter(this, "position");

		wp.getPosition();
		boolean b = printYesNoQuestion("Lehet építeni tornyot?");
		
		printExit(this);
		return b;
	}
}
