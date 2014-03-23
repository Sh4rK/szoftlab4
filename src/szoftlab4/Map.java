package szoftlab4;

/**
 * A pályát megvalósító / leíró osztály.
 * @author Nusser Adam
 *
 */
public class Map {
	
	Waypoint wp;
	
	/**
	 * A kapott útvonalrl betölti a Map-et;
	 */
	public Map(String str){
		printEnter(this);
		wp = new Waypoint(new Vector());
		printExit(this);
	}
	
	/**
	 * @return Lehet-e a vector helyére Obstacle-t építeni.
	 */
	public boolean canBuildObstacle(Vector v){
		
		boolean b;
		
		printEnter(this, "vector");
		wp.getPosition();
		
		b = printYesNoQuestion("Lehet építeni akadályt?");
		
		printExit(this);
		return b;
		
	}
	/**
	 * @return Lehet-e a vector helyére Tower-t építeni.
	 */
	public boolean canBuildTower(Vector v){
		
		boolean b;
		
		printEnter(this, "vector");
		wp.getPosition();
		
		b = printYesNoQuestion("Lehet építeni tornyot?");
		
		printExit(this);
		return b;
	}
}
