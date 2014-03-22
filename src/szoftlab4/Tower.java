package szoftlab4;

import static szoftlab4.Game.*;

import java.util.List;

public class Tower {

	public Projectile attack(List<Enemy> enemies) {
		printEnter(this, "enemies");
		
		Projectile ret = new Projectile();
		
		printExit(this);
		return ret;
	}
	
	public TowerGem getGem() {
		printEnter(this);
		
		TowerGem ret = new TowerGem();
		
		printExit(this);
		return ret;
	}

	public Vector getPosition() {
		printEnter(this);
		
		Vector ret = new Vector();
		
		printExit(this);
		return ret;
	}

	public double getRange() {
		printEnter(this);
		
		printExit(this);
		return 0;
	}
	
	public void setGem(TowerGem gem) {
		printEnter(this, "gem");

		printExit(this);
	}
}
