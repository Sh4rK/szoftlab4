package szoftlab4;

/**
 * A p�ly�t megval�s�to / le�r� oszt�ly.
 * @author Nusser Adam
 *
 */
public class Map {
	
	Waypoint wp;
	
	/**
	 * A kapott �tvonalr�l bet�lti a Map-et;
	 */
	public Map(String str){
		printEnter(this);
		wp = new Waypoint(new Vector());
		printExit(this);
	}
	
	/**
	 * @return Lehet-e a vector hely�re Obstacle-t �p�teni.
	 */
	public boolean canBuildObstacle(Vector v){
		
		boolean b;
		
		printEnter(this, "vector");
		wp.getPosition();
		
		b = printYesNoQuestion("Lehet �p�teni akad�lyt?");
		
		printExit(this);
		return b;
		
	}
	/**
	 * @return Lehet-e a vector hely�re Tower-t �p�teni.
	 */
	public boolean canBuildTower(Vector v){
		
		boolean b;
		
		printEnter(this, "vector");
		wp.getPosition();
		
		b = printYesNoQuestion("Lehet �p�teni akad�lyt?");
		
		printExit(this);
		return b;
	}
}
