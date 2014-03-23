package szoftlab4;

/**
 * Az ellenséget ütemezését megvalósító osztály.
 * @author Adam
 *
 */
public class Mission {
	
	/**
	 * A kapott útvonalról betölti a Mission-t.
	 * @param str
	 */
	public Mission(String str){
		printEnter(this);

		printExit(this);
	}
	/**
	 * MegKérdezi, hogy legyen-e következõ ellenség.
	 * @return Visszatér a listában tárolt következõ ellenséggel,vagy null értékkel.
	 */
	public Enemy getNextEnemy() {
		printEnter(this);
		
		if(printYesNoQuestion("Legyen következõ ellenség?")){
			printExit(this);
			return new Enemy();
		}
		else{
			printExit(this);
			return null;
		}	
	}

}
