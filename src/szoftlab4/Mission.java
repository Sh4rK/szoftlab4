package szoftlab4;

import static szoftlab4.Game.*;

/**
 * Az ellenséget ütemezését megvalósító osztály.
 * @author Adam
 *
 */
public class Mission {

	public static Waypoint wp = new Waypoint(new Vector());
	
	/**
	 * A kapott útvonalról betölti a Mission-t.
	 * @param str
	 */
	public Mission(String str){
		printEnter(this);

		printExit(this);
	}
	/**
	 * MegKérdezi, hogy legyen-e következő ellenség.
	 * @return Visszatér a listában tárolt következő ellenséggel,vagy null értékkel.
	 */
	public Enemy getNextEnemy() {
		printEnter(this);
		
		if(printYesNoQuestion("Legyen következő ellenség?")){
			printExit(this);
			return new Enemy(EnemyType.fooType, wp);
		}
		else{
			printExit(this);
			return null;
		}	
	}

}
