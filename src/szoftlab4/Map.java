package szoftlab4;

public class Map {
	
	/**
	 * @return Lehet-e a vector hely�re Obstacle-t �p�teni.
	 */
	public boolean canBuildObstacle(Vector v){
		
		boolean b;
		
		printEnter(this, "vector");
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
		b = printYesNoQuestion("Lehet �p�teni akad�lyt?");
		
		printExit(this);
		return b;
	}
}
