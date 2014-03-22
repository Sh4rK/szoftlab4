package szoftlab4;

import static szoftlab4.Game.*;

public class Obstacle {
	
	public Obstacle(Vector position) {
		printEnter(this, "position");
		
		printExit(this);
	}
	
	public ObstacleGem getGem() {
		printEnter(this);
		
		ObstacleGem ret = new ObstacleGem();
		
		printExit(this);
		return ret;
	}
	
	public Vector getPosition() {
		printEnter(this);

		Vector ret = new Vector();
		
		printExit(this);
		return ret;
	}
	
	public double getSlowingFactor(Enemy enemy) {
		printEnter(this, "enemy");

		printExit(this);
		return 0;
	}
	
	public void setGem(ObstacleGem gem) {
		printEnter(this, "gem");

		printExit(this);
	}
}
